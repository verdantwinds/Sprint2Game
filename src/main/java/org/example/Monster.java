package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Monster {
    private int x;
    private int y;
    private String name;
    private String riddle;
    private String riddleAnswer;

    public Monster(int gridSize, List<Integer[]> occupiedPositions) {
        Random random = new Random();


        do {
            x = random.nextInt(gridSize);
            y = random.nextInt(gridSize);
        } while (isPositionOccupied(occupiedPositions, x, y));

        occupiedPositions.add(new Integer[]{x, y});


        String[] monsterNames = {"Spooky", "Ghastly", "Creepy", "Haunting", "Terrifying"};
        String[] monsterTypes = {"Zombie", "Ghost", "Vampire", "Werewolf", "Demon"};
        this.name = monsterNames[random.nextInt(monsterNames.length)] + " " +
                monsterTypes[random.nextInt(monsterTypes.length)];


        String[][] riddleBank = {
                {"What has keys, but no locks; space, but no room; and you can enter, but not go in?", "keyboard"},
                {"I have cities, but no houses. I have mountains, but no trees. I have water, but no fish. What am I?", "map"},
                {"What goes up but never comes down?", "age"},
                {"I'm tall when I'm young, and short when I'm old. What am I?", "candle"}
        };


        int riddleIndex = random.nextInt(riddleBank.length);
        this.riddle = riddleBank[riddleIndex][0];
        this.riddleAnswer = riddleBank[riddleIndex][1];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public boolean isNearPlayer(int playerX, int playerY) {
        return Math.abs(playerX - x) <= 1 && Math.abs(playerY - y) <= 1 &&
                !(playerX == x && playerY == y);
    }

    public boolean isOnSamePosition(int playerX, int playerY) {
        return x == playerX && y == playerY;
    }

    public boolean challengePlayer(Scanner scanner) {
        System.out.println(name + " appears and challenges you to a riddle!");
        System.out.println("Riddle: " + riddle);
        System.out.print("Your answer: ");
        String playerAnswer = scanner.nextLine().trim().toLowerCase();

        return playerAnswer.equals(riddleAnswer);
    }

    private boolean isPositionOccupied(List<Integer[]> occupiedPositions, int x, int y) {
        for (Integer[] pos : occupiedPositions) {
            if (pos[0] == x && pos[1] == y) {
                return true;
            }
        }
        return false;
    }
}