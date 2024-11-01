package org.example;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class ChasingMonster extends Monster {
    private List<String> triviaQuestions;
    private List<String> triviaAnswers;

    public ChasingMonster(int gridSize, List<Integer[]> occupiedPositions) {
        super(gridSize, occupiedPositions);
        initializeTrivia();
    }

    private void initializeTrivia() {
        triviaQuestions = new ArrayList<>();
        triviaAnswers = new ArrayList<>();

        triviaQuestions.add("What color is the sky on a clear day?");
        triviaAnswers.add("blue");

        triviaQuestions.add("How many days are in a week?");
        triviaAnswers.add("7");

        triviaQuestions.add("What planet do we live on?");
        triviaAnswers.add("earth");

        triviaQuestions.add("What is 2 + 2?");
        triviaAnswers.add("4");

        triviaQuestions.add("What color is grass?");
        triviaAnswers.add("green");
    }

    public List<String> getTriviaQuestions() {
        return triviaQuestions;
    }

    public void setTriviaQuestions(List<String> triviaQuestions) {
        this.triviaQuestions = triviaQuestions;
    }

    public List<String> getTriviaAnswers() {
        return triviaAnswers;
    }

    public void setTriviaAnswers(List<String> triviaAnswers) {
        this.triviaAnswers = triviaAnswers;
    }

    @Override
    public boolean challengePlayer(Scanner scanner) {

        Random random = new Random();
        int questionIndex = random.nextInt(triviaQuestions.size());

        System.out.println(getName() + " challenges you with a question!");
        System.out.println("Question: " + triviaQuestions.get(questionIndex));

        String playerAnswer = scanner.nextLine().trim().toLowerCase();
        return playerAnswer.equals(triviaAnswers.get(questionIndex).toLowerCase());
    }

    public void moveTowardsPlayer(int playerX, int playerY, int gridSize) {

        int currentX = this.x;
        int currentY = this.y;


        int[][] possibleMoves = {
                {currentX, currentY - 1},
                {currentX, currentY + 1},
                {currentX - 1, currentY},
                {currentX + 1, currentY}
        };

        double minDistance = Double.MAX_VALUE;
        int[] bestMove = {currentX, currentY};

        for (int[] move : possibleMoves) {
            if (move[0] >= 0 && move[0] < gridSize &&
                    move[1] >= 0 && move[1] < gridSize) {

                boolean occupied = false;
                for (Integer[] pos : occupiedPositions) {
                    if (pos[0] == move[0] && pos[1] == move[1]) {
                        occupied = true;
                        break;
                    }
                }

                if (!occupied) {

                    double distance = calculateDistance(move[0], move[1], playerX, playerY);
                    if (distance < minDistance) {
                        minDistance = distance;
                        bestMove = move;
                    }
                }
            }
        }

        occupiedPositions.removeIf(pos -> pos[0] == this.x && pos[1] == this.y);

        this.x = bestMove[0];
        this.y = bestMove[1];

        occupiedPositions.add(new Integer[]{this.x, this.y});
    }

    private double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}