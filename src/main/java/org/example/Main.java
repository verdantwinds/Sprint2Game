package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("MONSTER MASH");
                System.out.print("Enter the grid size (minimum 6): ");
                int size = inputScanner.nextInt();

                if (size < 6) {
                    System.out.println("Grid size must be at least 6x6. Try again.");
                    continue;
                }

                inputScanner.nextLine();

                MonsterMashGame game = new MonsterMashGame(size, 1);
                game.play();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                inputScanner.nextLine();
            }
        }

        inputScanner.close();
    }
}
