package org.example;

public class Treasure {
    private int x;
    private int y;
    private final int totalTreasures;
    private int treasuresCollected;

    public Treasure(int gridSize, int x, int y) {
        this.x = x;
        this.y = y;
        this.totalTreasures = (int) Math.ceil(gridSize * 0.2);
        this.treasuresCollected = 0;
    }

    public String getProximityHint(int playerX, int playerY, int gridSize) {
        double distance = calculateDistance(playerX, playerY);

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

    public String getDirectionalHint(int playerX, int playerY, int prevX, int prevY) {
        double prevDistance = calculateDistance(prevX, prevY);
        double currentDistance = calculateDistance(playerX, playerY);

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

    private double calculateDistance(int playerX, int playerY) {
        return Math.sqrt(
                Math.pow(playerX - x, 2) +
                        Math.pow(playerY - y, 2)
        );
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTotalTreasures() {
        return totalTreasures;
    }

    public int getTreasuresCollected() {
        return treasuresCollected;
    }

    public void incrementTreasuresCollected() {
        treasuresCollected++;
    }
}
