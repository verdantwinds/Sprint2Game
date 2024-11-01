package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testBoardConstructorWithValidSize() {
        board = new Board(6);
        assertEquals(6, board.getGridSize());
    }

    @Test
    void testBoardConstructorWithInvalidSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Board(5);
        }, "Grid size must be at least 6x6");
    }

    @Test
    void testPrintGridWithPlayer() {
        board = new Board(6);
        Player player = new Player(2, 3);
        List<Monster> monsters = new ArrayList<>();

        board.printGrid(player, monsters);

        // Verify basic grid output
        String output = outContent.toString();
        assertTrue(output.contains("🧙"), "Player should be represented on the grid");

    }

    @Test
    void testPrintGridOutputFormat() {
        board = new Board(6);
        Player player = new Player(2, 3);
        List<Monster> monsters = new ArrayList<>();

        board.printGrid(player, monsters);

        String output = outContent.toString();

        // Check for legend
        assertTrue(output.contains("🧙 - you, the player"));
        assertTrue(output.contains("🌫️ - border"));
    }

    @Test
    void testGridConsistency() {
        board = new Board(6);
        Player player = new Player(2, 3);
        List<Monster> monsters = new ArrayList<>();

        board.printGrid(player, monsters);

        String output = outContent.toString();

        String[] lines = output.split("\n");
        int gridLines = 0;

        for (String line : lines) {
            if (line.contains("🌫️")) {
                gridLines++;
            }
        }

        assertEquals(9, gridLines, "Grid lines should match grid size plus the border");
    }

    @Test
    void testRandomEmojisPresent() {
        board = new Board(6);
        Player player = new Player(2, 3);
        List<Monster> monsters = new ArrayList<>();

        board.printGrid(player, monsters);

        String output = outContent.toString();

        assertTrue(output.contains("🌲") || output.contains("🌳") ||
                output.contains("🍂") || output.contains("🌿") ||
                output.contains("🍄"), "Forest emojis should be present");

        assertTrue(output.contains("⚰️") || output.contains("🕸️") ||
                output.contains("🦇") || output.contains("💀") ||
                output.contains("🪦"), "Spooky emojis should be present");
    }
}