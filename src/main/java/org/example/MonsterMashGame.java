package org.example;

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

    // Constructor to initialize the game with a specified grid size
    public MonsterMashGame(int size) {
        // Validate minimum grid size
        if (size < 5) {
            throw new IllegalArgumentException("Grid size must be at least 5x5");
        }

        // Set up game parameters
        this.gridSize = size;
        random = new Random();
        scanner = new Scanner(System.in);

        // Randomly place the player on the grid
        playerX = random.nextInt(gridSize);
        playerY = random.nextInt(gridSize);

        // Randomly place the treasure, ensuring it's not in the same spot as the player
        do {
            treasureX = random.nextInt(gridSize);
            treasureY = random.nextInt(gridSize);
        } while (treasureX == playerX && treasureY == playerY);
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

    public void play() {
        // Game introduction
        System.out.println("Welcome to the Monster Mash!");
        System.out.println("Move using 'up', 'down', 'left', 'right'.");
        System.out.println("Find the treasure without getting caught by the monsters!");

        // Array to track previous position
        int[] prevPos = new int[2];

        // Continuous game loop until treasure is found or player quits
        while (true) {
            // Show proximity hint
            System.out.println(getTreasureHint());
            // Show current position (for debugging)
            System.out.println("Current position: (" + playerX + ", " + playerY + ")");

            // Get player move
            System.out.print("Enter your move: ");
            String move = scanner.nextLine();

            // Check for quit
            if (move.equalsIgnoreCase("quit")) {
                System.out.println("You've abandoned your hunt for the treasure...");
                break;
            }

            // Move player only if direction is valid
            if (movePlayer(move, prevPos)) {
                System.out.println(getDirectionalHint(prevPos[0], prevPos[1]));

                // Check if treasure is found
                if (playerX == treasureX && playerY == treasureY) {
                    System.out.println("Congratulations! You found the treasure!");
                    break;
                }
            }
        }

        // Close scanner
        scanner.close();
    }
}