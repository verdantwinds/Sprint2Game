package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MonsterMashGame {
    private final Board board;
    private final Player player;
    private final Treasure treasure;
    private final List<Monster> monsters;
    private final int monstersPerTreasure;
    private final Random random;
    private final Scanner scanner;

    public MonsterMashGame(int size, int baseMonsterCount) {
        if (baseMonsterCount < 0) {
            throw new IllegalArgumentException("Monster count cannot be negative");
        }

        this.board = new Board(size);
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.monsters = new ArrayList<>();
        this.monstersPerTreasure = baseMonsterCount;

        // Initialize player at random position
        int playerX = random.nextInt(board.getGridSize());
        int playerY = random.nextInt(board.getGridSize());
        this.player = new Player(playerX, playerY);

        // Initialize treasure at random position
        List<Integer[]> occupiedPositions = new ArrayList<>();
        occupiedPositions.add(new Integer[]{playerX, playerY});
        int treasureX = random.nextInt(board.getGridSize());
        int treasureY = random.nextInt(board.getGridSize());
        while (isPositionOccupied(treasureX, treasureY, occupiedPositions)) {
            treasureX = random.nextInt(board.getGridSize());
            treasureY = random.nextInt(board.getGridSize());
        }
        this.treasure = new Treasure(board.getGridSize(), treasureX, treasureY);

        // Spawn initial monsters
        spawnMonsters(occupiedPositions);
    }

    private void spawnNewTreasure(List<Integer[]> occupiedPositions) {
        int newX, newY;
        do {
            newX = random.nextInt(board.getGridSize());
            newY = random.nextInt(board.getGridSize());
        } while (isPositionOccupied(newX, newY, occupiedPositions));

        treasure.setPosition(newX, newY);
        occupiedPositions.add(new Integer[]{newX, newY});
    }

    private void spawnMonsters(List<Integer[]> occupiedPositions) {
        int currentMonsterCount = monstersPerTreasure + (int)(treasure.getTreasuresCollected() * 0.2);

        monsters.clear();

        for (int i = 0; i < currentMonsterCount; i++) {
            if (random.nextInt() % 2 == 0) {
                monsters.add(new RiddleMonster(board.getGridSize(), occupiedPositions));
            } else {
                monsters.add(new ChasingMonster(board.getGridSize(), occupiedPositions));
            }
        }
    }

    private boolean isPositionOccupied(int x, int y, List<Integer[]> occupiedPositions) {
        for (Integer[] pos : occupiedPositions) {
            if (pos[0] == x && pos[1] == y) {
                return true;
            }
        }
        return false;
    }

    private String getMonsterWarnings() {
        StringBuilder warnings = new StringBuilder();
        boolean riddleMonsterNear = false;
        boolean chasingMonsterNear = false;
        double closestRiddleDistance = Double.MAX_VALUE;
        double closestChasingDistance = Double.MAX_VALUE;

        for (Monster monster : monsters) {
            double distance = Math.sqrt(
                    Math.pow(player.getX() - monster.getX(), 2) +
                            Math.pow(player.getY() - monster.getY(), 2)
            );

            if (monster instanceof RiddleMonster) {
                if (distance < closestRiddleDistance) {
                    closestRiddleDistance = distance;
                    riddleMonsterNear = distance <= 2;
                }
            } else if (monster instanceof ChasingMonster) {
                if (distance < closestChasingDistance) {
                    closestChasingDistance = distance;
                    chasingMonsterNear = distance <= 2;
                }
            }
        }

        if (riddleMonsterNear) {
            if (closestRiddleDistance <= 1) {
                warnings.append("A riddle monster is extremely close! ");
            } else {
                warnings.append("You hear mysterious whispers nearby... ");
            }
        }

        if (chasingMonsterNear) {
            if (closestChasingDistance <= 1) {
                warnings.append("A chasing monster is right next to you! ");
            } else {
                warnings.append("You hear heavy footsteps approaching... ");
            }
        }

        return warnings.toString();
    }

    public void play() {
        System.out.println("Welcome to the Monster Mash!");
        System.out.println("Move using 'up', 'down', 'left', 'right'.");
        System.out.println("Find " + treasure.getTotalTreasures() + " hidden treasures while avoiding unseen monsters!");
        System.out.println("The monsters will multiply as you collect more treasures...");
        System.out.println("Listen for warnings and follow the hints to survive!");
        System.out.println("More spooky symbols may appear when danger is near...");

        int prevX, prevY;

        while (true) {
            board.printGrid(player, monsters);

            String monsterWarnings = getMonsterWarnings();
            if (!monsterWarnings.isEmpty()) {
                System.out.println("\n" + monsterWarnings);
            }

            System.out.println(treasure.getProximityHint(player.getX(), player.getY(), board.getGridSize()));
            System.out.println("Treasures collected: " + treasure.getTreasuresCollected() + "/" + treasure.getTotalTreasures());

            System.out.print("Enter your move: ");
            String move = scanner.nextLine();

            if (move.equalsIgnoreCase("quit")) {
                System.out.println("You've abandoned your hunt for the treasure...");
                break;
            }

            prevX = player.getX();
            prevY = player.getY();

            boolean monsterEncountered = false;
            for (Monster monster : monsters) {
                if (monster.isOnSamePosition(player.getX(), player.getY())) {
                    if (monster instanceof RiddleMonster) {
                        System.out.println("A mysterious figure emerges from the shadows!");
                    } else {
                        System.out.println("A terrifying monster appears before you!");
                    }
                    monsterEncountered = true;

                    if (monster.challengePlayer(scanner)) {
                        System.out.println("You've overcome the challenge! The monster vanishes into thin air!");
                        monsters.remove(monster);
                        monsterEncountered = false;
                        break;
                    } else {
                        System.out.println("The monster overwhelms you... Game Over!");
                        return;
                    }
                }
            }

            if (player.move(move, board.getGridSize())) {
                System.out.println(treasure.getDirectionalHint(player.getX(), player.getY(), prevX, prevY));

                if (!monsterEncountered) {
                    for (Monster monster : monsters) {
                        if (monster instanceof ChasingMonster) {
                            ((ChasingMonster) monster).moveTowardsPlayer(player.getX(), player.getY(), board.getGridSize());
                        }
                    }

                    if (player.getX() == treasure.getX() && player.getY() == treasure.getY()) {
                        treasure.incrementTreasuresCollected();
                        if (treasure.getTreasuresCollected() == treasure.getTotalTreasures()) {
                            System.out.println("Congratulations! You've collected all the treasures and won the game!");
                            System.out.println("It took you " + player.getMoveCount() + " moves to find " + treasure.getTotalTreasures() + " treasures!");
                            for(int i = 0; i < treasure.getTotalTreasures(); i++) {
                                System.out.print("\uD83D\uDC8E");
                            }
                            break;
                        } else {
                            System.out.println("Treasure found! " + (treasure.getTotalTreasures() - treasure.getTreasuresCollected()) + " more to go!");
                            System.out.println("But beware... more monsters have appeared in the forest!");
                            List<Integer[]> occupiedPositions = new ArrayList<>();
                            occupiedPositions.add(new Integer[]{player.getX(), player.getY()});
                            spawnNewTreasure(occupiedPositions);
                            spawnMonsters(occupiedPositions);
                        }
                    }
                }
            }
        }

        scanner.close();
    }
}