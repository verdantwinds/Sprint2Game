package org.example;

public class Player {
    private int x;
    private int y;
    private final int emoji = 0x1F9D9;
    private int moveCount;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.moveCount = 0;
    }

    public boolean move(String direction, int gridSize) {
        int prevX = x;
        int prevY = y;

        switch (direction.toLowerCase()) {
            case "up":
                if (y > 0) {
                    y--;
                    moveCount++;
                    return true;
                }
                System.out.println("You can't currently move up! Pick another direction!");
                return false;
            case "down":
                if (y < gridSize - 1) {
                    y++;
                    moveCount++;
                    return true;
                }
                System.out.println("You can't currently move down! Pick another direction!");
                return false;
            case "left":
                if (x > 0) {
                    x--;
                    moveCount++;
                    return true;
                }
                System.out.println("You can't currently move left! Pick another direction!");
                return false;
            case "right":
                if (x < gridSize - 1) {
                    x++;
                    moveCount++;
                    return true;
                }
                System.out.println("You can't currently move right! Pick another direction!");
                return false;
            default:
                System.out.println("Invalid direction! Please use 'up', 'down', 'left', or 'right'.");
                return false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getMoveCount() {
        return moveCount;
    }
}