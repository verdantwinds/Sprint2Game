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

    // Random number generator for initial placement
    private Random random;
    // Scanner for user input
    private Scanner scanner;

    private List<Monster> monsters;
    private int initialMonsterCount;

    private int playerEmoji = 0x1F9D9;

    public MonsterMashGame(int size, int monsterCount) {
        // Validate minimum grid size and monster count
        if (size < 5) {
            throw new IllegalArgumentException("Grid size must be at least 5x5");
        }
        if (monsterCount < 0) {
            throw new IllegalArgumentException("Monster count cannot be negative");
        }

        // Set up game parameters
        this.gridSize = size;
        this.initialMonsterCount = monsterCount;
        random = new Random();
        scanner = new Scanner(System.in);
        monsters = new ArrayList<>();

        // List to track occupied positions
        List<Integer[]> occupiedPositions = new ArrayList<>();

        // Randomly place the player on the grid
        playerX = random.nextInt(gridSize);
        playerY = random.nextInt(gridSize);
        occupiedPositions.add(new Integer[]{playerX, playerY});

        // Randomly place the treasure, ensuring it's not in the same spot as the player
        do {
            treasureX = random.nextInt(gridSize);
            treasureY = random.nextInt(gridSize);
        } while (treasureX == playerX && treasureY == playerY);
        occupiedPositions.add(new Integer[]{treasureX, treasureY});

        // Spawn monsters
        for (int i = 0; i < initialMonsterCount; i++) {
            if (random.nextInt() % 2 == 0) {
                monsters.add(new RiddleMonster(gridSize, occupiedPositions));
            } else {
                monsters.add(new ChasingMonster(gridSize, occupiedPositions));
            }
        }
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
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
//                boolean monsterHere = false;
//                for (Monster monster : monsters) {
//                    if (x == monster.getX() && y == monster.getY()) {
//                        System.out.print(" M ");
//                        monsterHere = true;
//                        break;
//                    }
//                }

                //if (!monsterHere) {
                    if (x == playerX && y == playerY) {
                        System.out.print(Character.toString(playerEmoji));
                    } else {
                        System.out.print(" . ");
                    }
                //}
            }
            System.out.println();
        }
        System.out.println();
    }

    public void play() {
        // Game introduction
        System.out.println("Welcome to the Monster Mash!");
        System.out.println("Move using 'up', 'down', 'left', 'right'.");
        System.out.println("Find the treasure while avoiding monsters!");

        // Array to track previous position
        int[] prevPos = new int[2];

        // Continuous game loop until treasure is found or player quits/dies
        while (true) {
            // prints da grid
            printGrid();
            // Show proximity hint
            System.out.println(getTreasureHint());
            // Show current position (for debugging)
            System.out.println("Current position: (" + playerX + ", " + playerY + ")");

            // Check for monsters near the player
            for (Monster monster : monsters) {
                if (monster.isNearPlayer(playerX, playerY)) {
                    System.out.println(monster.getName() + " is lurking nearby... *spooky sounds*");
                }
            }

            // Get player move
            System.out.print("Enter your move: ");
            String move = scanner.nextLine();

            // Check for quit
            if (move.equalsIgnoreCase("quit")) {
                System.out.println("You've abandoned your hunt for the treasure...");
                break;
            }
            boolean monsterEncountered = false;
            for (Monster monster : monsters) {
                if (monster.isOnSamePosition(playerX, playerY)) {
                    System.out.println(monster.getName() + " blocks your path!");
                    monsterEncountered = true;

                    // Handle different monster types
                    if (monster instanceof RiddleMonster || monster instanceof ChasingMonster) {
                        if (monster.challengePlayer(scanner)) {
                            System.out.println("Congratulations! You answered correctly and the monster vanishes!");
                            monsters.remove(monster);
                            monsterEncountered = false;  // Allow the game to continue
                            break;
                        } else {
                            System.out.println("Wrong answer! " + monster.getName() + " attacks and defeats you!");
                            return;  // Game over
                        }
                    }
                }
            }

            // Move player only if direction is valid
            if (movePlayer(move, prevPos)) {
                System.out.println(getDirectionalHint(prevPos[0], prevPos[1]));

                // Only move chasing monsters if no monster was encountered
                if (!monsterEncountered) {
                    // Move all chasing monsters
                    for (Monster monster : monsters) {
                        if (monster instanceof ChasingMonster) {
                            ((ChasingMonster) monster).moveTowardsPlayer(playerX, playerY, gridSize);
                        }
                    }

                    // Check if treasure is found
                    if (playerX == treasureX && playerY == treasureY) {
                        System.out.println("Congratulations! You found the treasure!");
                        break;
                    }
                }
            }
        }

        // Close scanner
        scanner.close();
    }
}