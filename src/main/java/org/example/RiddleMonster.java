package org.example;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RiddleMonster extends Monster {
    private String riddle;
    private String riddleAnswer;


    public RiddleMonster(int gridSize, List<Integer[]> occupiedPositions) {
        super(gridSize, occupiedPositions);
        Random random = new Random();

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

    @Override
    public boolean challengePlayer(Scanner scanner) {
        System.out.println(name + " appears and challenges you to a riddle!");
        System.out.println("Riddle: " + riddle);
        System.out.print("Your answer: ");
        String playerAnswer = scanner.nextLine().trim().toLowerCase();
        return playerAnswer.equals(riddleAnswer);
    }
}
