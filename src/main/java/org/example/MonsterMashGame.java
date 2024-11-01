package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MonsterMashGame {
    // Game grid dimensions and player/treasure coordinates
    private int gridSize;
    private int playerX;
    private int playerY;
    private int treasureX;
    private int treasureY;
    private int totalTreasures;
    private int treasuresCollected;
    private int monstersPerTreasure;

    // Random number generator for initial placement
    private Random random;
    // Scanner for user input
    private Scanner scanner;

    private List<Monster> monsters;
    private int initialMonsterCount;

    private final int playerEmoji = 0x1F9D9;

    private final String[] forestEmojis = {
            "🌲",
            "🌳",
            "🍂",
            "🌿",
            "🍄"
    };

    private final String[] spookyEmojis = {
            "⚰️",
            "🕸️",
            "🦇",
            "💀",
            "🪦"
    };


    public MonsterMashGame(int size, int baseMonsterCount) {
        if (size < 6) {
            throw new IllegalArgumentException("Grid size must be at least 6x6");
        }
        if (baseMonsterCount < 0) {
            throw new IllegalArgumentException("Monster count cannot be negative");
        }

        this.gridSize = size;
        this.totalTreasures = (int) Math.ceil(size * 0.2); // number of treasures scales with grid size
        this.treasuresCollected = 0;
        this.monstersPerTreasure = baseMonsterCount;

        random = new Random();
        scanner = new Scanner(System.in);
        monsters = new ArrayList<>();

        initializeGame();
    }

    private void initializeGame() {
        List<Integer[]> occupiedPositions = new ArrayList<>();

        // Place player
        playerX = random.nextInt(gridSize);
        playerY = random.nextInt(gridSize);
        occupiedPositions.add(new Integer[]{playerX, playerY});

        // Place first treasure
        spawnNewTreasure(occupiedPositions);

        // Spawn initial monsters
        spawnMonsters(occupiedPositions);
    }

    private void spawnNewTreasure(List<Integer[]> occupiedPositions) {
        do {
            treasureX = random.nextInt(gridSize);
            treasureY = random.nextInt(gridSize);
        } while (isPositionOccupied(treasureX, treasureY, occupiedPositions));

        occupiedPositions.add(new Integer[]{treasureX, treasureY});
    }

    private void spawnMonsters(List<Integer[]> occupiedPositions) {
        int currentMonsterCount = monstersPerTreasure + (int)(treasuresCollected * 0.2);

        // Clear existing monsters
        monsters.clear();

        // Spawn new monsters
        for (int i = 0; i < currentMonsterCount; i++) {
            if (random.nextInt() % 2 == 0) {
                monsters.add(new RiddleMonster(gridSize, occupiedPositions));
            } else {
                monsters.add(new ChasingMonster(gridSize, occupiedPositions));
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

    // Calculate Euclidean distance between player and treasure
    private double calculateDistance() {
        return Math.sqrt(
                Math.pow(playerX - treasureX, 2) +
                        Math.pow(playerY - treasureY, 2)
        );
    }

    // Calculate distance from a previous position to the treasure
    private double calculatePreviousDistance(int prevX, int prevY) {
        return Math.sqrt(
                Math.pow(prevX - treasureX, 2) +
                        Math.pow(prevY - treasureY, 2)
        );
    }

    // Generate a hint about the treasure's proximity
    private String getTreasureHint() {
        double distance = calculateDistance();

        // Provide hints based on distance from treasure
        if (distance == 0) {
            return "This is it! The treasure!";
        } else if (distance <= Math.ceil(gridSize * 0.2)) {
            return "The treasure is very close!";
        } else if (distance <= Math.ceil(gridSize * 0.4)) {
            return "You're getting closer to the treasure...";
        } else if (distance <= Math.ceil(gridSize * 0.6)) {
            return "You're not close to the treasure...";
        } else {
            return "The treasure is far away.";
        }
    }

    // Provide feedback on whether the last move brought the player closer or further from the treasure
    private String getDirectionalHint(int prevX, int prevY) {
        // Compare previous and current distances to treasure
        double prevDistance = calculatePreviousDistance(prevX, prevY);
        double currentDistance = calculateDistance();

        // Determine if move was closer or further
        if (currentDistance < prevDistance) {
            if (playerX < prevX) return "Moving left got you closer!";
            if (playerX > prevX) return "Moving right got you closer!";
            if (playerY < prevY) return "Moving up got you closer!";
            if (playerY > prevY) return "Moving down got you closer!";
        } else if (currentDistance > prevDistance) {
            if (playerX < prevX) return "Moving left took you further away!";
            if (playerX > prevX) return "Moving right took you further away!";
            if (playerY < prevY) return "Moving up took you further away!";
            if (playerY > prevY) return "Moving down took you further away!";
        }

        return "Your move didn't change your distance to the treasure.";
    }

    private boolean movePlayer(String direction, int[] prevPos) {
        // Store previous position
        prevPos[0] = playerX;
        prevPos[1] = playerY;

        // Validate input direction
        switch (direction.toLowerCase()) {
            case "up":
                if (playerY > 0) {
                    playerY--;
                    return true;
                }
                System.out.println("You can't currently move up! Pick another direction!");
                return false;
            case "down":
                if (playerY < gridSize - 1) {
                    playerY++;
                    return true;
                }
                System.out.println("You can't currently move down! Pick another direction!");
                return false;
            case "left":
                if (playerX > 0) {
                    playerX--;
                    return true;
                }
                System.out.println("You can't currently move left! Pick another direction!");
                return false;
            case "right":
                if (playerX < gridSize - 1) {
                    playerX++;
                    return true;
                }
                System.out.println("You can't currently move right! Pick another direction!");
                return false;
            default:
                System.out.println("Invalid direction! Please use 'up', 'down', 'left', or 'right'.");
                return false;
        }
    }


    private void printGrid() {
        Random emojiRandom = new Random();
        String[][] gridLayout = new String[gridSize][gridSize];

        // Fill grid with random forest and spooky emojis
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (x == playerX && y == playerY) {
                    gridLayout[y][x] = "🧙";
                } else {
                    // Increase the chance of spooky emojis near monsters to create atmosphere
                    boolean nearMonster = false;
                    for (Monster monster : monsters) {
                        double distanceToMonster = Math.sqrt(
                                Math.pow(x - monster.getX(), 2) +
                                        Math.pow(y - monster.getY(), 2)
                        );
                        if (distanceToMonster <= 2) {
                            nearMonster = true;
                            break;
                        }
                    }

                    // Higher chance of spooky emojis near monsters
                    if (nearMonster && emojiRandom.nextInt(100) < 40) {
                        gridLayout[y][x] = spookyEmojis[emojiRandom.nextInt(spookyEmojis.length)];
                    } else if (emojiRandom.nextInt(100) < 80) {
                        gridLayout[y][x] = forestEmojis[emojiRandom.nextInt(forestEmojis.length)];
                    } else {
                        gridLayout[y][x] = spookyEmojis[emojiRandom.nextInt(spookyEmojis.length)];
                    }
                }
            }
        }

        // prints da border
        System.out.println("🌫️ ".repeat(gridSize + 2));
        for (int y = 0; y < gridSize; y++) {
            System.out.print("🌫️");
            for (int x = 0; x < gridSize; x++) {
                System.out.print(gridLayout[y][x] + " ");
            }
            System.out.println("🌫️");
        }
        System.out.println("🌫️ ".repeat(gridSize + 2));

        // Updated legend
        System.out.println("🧙 - you, the player");
        System.out.println("🌫️ - border");
        System.out.println();
    }

    private String getMonsterWarnings() {
        StringBuilder warnings = new StringBuilder();
        boolean riddleMonsterNear = false;
        boolean chasingMonsterNear = false;
        double closestRiddleDistance = Double.MAX_VALUE;
        double closestChasingDistance = Double.MAX_VALUE;

        for (Monster monster : monsters) {
            double distance = Math.sqrt(
                    Math.pow(playerX - monster.getX(), 2) +
                            Math.pow(playerY - monster.getY(), 2)
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
        System.out.println("Find " + totalTreasures + " hidden treasures while avoiding unseen monsters!");
        System.out.println("The monsters will multiply as you collect more treasures...");
        System.out.println("Listen for warnings and follow the hints to survive!");
        System.out.println("More spooky symbols may appear when danger is near...");

        int[] prevPos = new int[2];

        while (true) {
            printGrid();

            // Print environment warnings
            String monsterWarnings = getMonsterWarnings();
            if (!monsterWarnings.isEmpty()) {
                System.out.println("\n" + monsterWarnings);
            }

            System.out.println(getTreasureHint());
            System.out.println("Treasures collected: " + treasuresCollected + "/" + totalTreasures);

            System.out.print("Enter your move: ");
            String move = scanner.nextLine();

            if (move.equalsIgnoreCase("quit")) {
                System.out.println("You've abandoned your hunt for the treasure...");
                break;
            }

            boolean monsterEncountered = false;
            for (Monster monster : monsters) {
                if (monster.isOnSamePosition(playerX, playerY)) {
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

            if (movePlayer(move, prevPos)) {
                System.out.println(getDirectionalHint(prevPos[0], prevPos[1]));

                if (!monsterEncountered) {
                    for (Monster monster : monsters) {
                        if (monster instanceof ChasingMonster) {
                            ((ChasingMonster) monster).moveTowardsPlayer(playerX, playerY, gridSize);
                        }
                    }

                    if (playerX == treasureX && playerY == treasureY) {
                        treasuresCollected++;
                        if (treasuresCollected == totalTreasures) {
                            System.out.println("Congratulations! You've collected all the treasures and won the game!");
                            for(int i = 0; i < totalTreasures; i++) {
                                System.out.print("\uD83D\uDC8E");
                            }
                            break;
                        } else {
                            System.out.println("Treasure found! " + (totalTreasures - treasuresCollected) + " more to go!");
                            System.out.println("But beware... more monsters have appeared in the forest!");
                            List<Integer[]> occupiedPositions = new ArrayList<>();
                            occupiedPositions.add(new Integer[]{playerX, playerY});
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