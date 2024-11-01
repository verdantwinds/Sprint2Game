package org.example;

import java.util.List;
import java.util.Random;

public class Board {
    private final int gridSize;
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

    public Board(int size) {
        if (size < 6) {
            throw new IllegalArgumentException("Grid size must be at least 6x6");
        }
        this.gridSize = size;
    }

    public void printGrid(Player player, List<Monster> monsters) {
        Random emojiRandom = new Random();
        String[][] gridLayout = new String[gridSize][gridSize];

        // Fill grid with random forest and spooky emojis
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if (x == player.getX() && y == player.getY()) {
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

    public int getGridSize() {
        return gridSize;
    }
}