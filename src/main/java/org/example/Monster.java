package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

abstract public class Monster {
    protected int x;
    protected int y;
    protected String name;
    protected List<Integer[]> occupiedPositions;

    /**
     * Constructor for abstract class monster that determines monster name and initial position
     * @param gridSize
     * @param occupiedPositions
     */
    public Monster(int gridSize, List<Integer[]> occupiedPositions) {
        Random random = new Random();


        do {
            x = random.nextInt(gridSize);
            y = random.nextInt(gridSize);
        } while (isPositionOccupied(occupiedPositions, x, y));

        occupiedPositions.add(new Integer[]{x, y});
        this.occupiedPositions = occupiedPositions;


        String[] monsterNames = {"Spooky", "Ghastly", "Creepy", "Haunting", "Terrifying"};
        String[] monsterTypes = {"Zombie", "Ghost", "Vampire", "Werewolf", "Demon"};
        this.name = monsterNames[random.nextInt(monsterNames.length)] + " " +
                monsterTypes[random.nextInt(monsterTypes.length)];


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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNearPlayer(int playerX, int playerY) {
        return Math.abs(playerX - x) <= 1 && Math.abs(playerY - y) <= 1 &&
                !(playerX == x && playerY == y);
    }

    public boolean isOnSamePosition(int playerX, int playerY) {
        return x == playerX && y == playerY;
    }

    public boolean challengePlayer(Scanner scanner) {
        // implement new method
        return false;
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