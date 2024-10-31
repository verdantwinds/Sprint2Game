package org.example;

import java.util.Random;
import java.util.Scanner;

public class MonsterMashGame {

    private int gridSize;
    private int playerX;
    private int playerY;
    private int treasureX;
    private int treasureY;

    private Random random;
    private Scanner scanner;

    public MonsterMashGame(int size) {
        if (size < 5) {
            throw new IllegalArgumentException("Dungeon size must be at least 5x5");
        }

        this.gridSize = size;
        random = new Random();
        scanner = new Scanner(System.in);

        playerX = random.nextInt(gridSize);
        playerY = random.nextInt(gridSize);

        do {
            treasureX = random.nextInt(gridSize);
            treasureY = random.nextInt(gridSize);
        } while (treasureX == playerX && treasureY == playerY);
    }

    private double calculateDistance() {
        return Math.sqrt(Math.pow(playerX - treasureX, 2) + Math.pow(playerY - treasureY, 2));
    }

    private String getTreasureHint() {
        double distance = calculateDistance();

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

    private void movePlayer(String direction) {
        switch (direction.toLowerCase()) {
            case "up":
                if (playerY > 0)
                    playerY--;
                break;
            case "down":
                if (playerY < gridSize - 1)
                    playerY++;
                break;
            case "left":
                if (playerX > 0)
                    playerX--;
                break;
            case "right":
                if (playerX < gridSize - 1)
                    playerX++;
                break;
        }
    }

    public void play() {
        System.out.println("Welcome to the Monster Mash!");
        System.out.println("Move using 'up', 'down', 'left', 'right'.");
        System.out.println("Find the treasure without getting caught by the monsters!");

        while (true) {
            System.out.println(getTreasureHint());
            System.out.println("Current position: (" + playerX + ", " + playerY + ")");

            System.out.print("Enter your move: ");
            String move = scanner.nextLine();

            if (move.equalsIgnoreCase("quit")) {
                System.out.println("You've abandoned your hunt for the treasure...");
                break;
            }

            movePlayer(move);

            if (playerX == treasureX && playerY == treasureY) {
                System.out.println("Congratulations! You've found the treasure!");
                break;
            }
        }

        scanner.close();
    }


}