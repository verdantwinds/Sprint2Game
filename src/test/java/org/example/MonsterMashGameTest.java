package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MonsterMashGameTest {

    private MonsterMashGame game;

    @BeforeEach
    public void setUp() {
        // Create a game with a standard 10x10 grid for most tests
        game = new MonsterMashGame(10);
    }


    @Test
    public void testGameInitialization() {
        // Test that the game is initialized with a valid grid
        Assertions.assertNotNull(game, "Game should be initialized");
    }

    @Test
    public void testInvalidGridSizeThrowsException() {
        // Test that creating a game with too small a grid throws an exception
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new MonsterMashGame(4),
                "Grid size less than 5 should throw an exception"
        );
    }

    @Test
    public void testPlayerAndTreasurePlacement() throws Exception {
        // Use reflection to access private fields
        Field playerXField = MonsterMashGame.class.getDeclaredField("playerX");
        Field playerYField = MonsterMashGame.class.getDeclaredField("playerY");
        Field treasureXField = MonsterMashGame.class.getDeclaredField("treasureX");
        Field treasureYField = MonsterMashGame.class.getDeclaredField("treasureY");

        playerXField.setAccessible(true);
        playerYField.setAccessible(true);
        treasureXField.setAccessible(true);
        treasureYField.setAccessible(true);

        int playerX = (int) playerXField.get(game);
        int playerY = (int) playerYField.get(game);
        int treasureX = (int) treasureXField.get(game);
        int treasureY = (int) treasureYField.get(game);

        // Verify player and treasure are within grid
        Assertions.assertTrue(playerX >= 0 && playerX < 10, "Player X should be in grid");
        Assertions.assertTrue(playerY >= 0 && playerY < 10, "Player Y should be in grid");
        Assertions.assertTrue(treasureX >= 0 && treasureX < 10, "Treasure X should be in grid");
        Assertions.assertTrue(treasureY >= 0 && treasureY < 10, "Treasure Y should be in grid");

        // Verify player and treasure are not in the same location
        Assertions.assertFalse(playerX == treasureX && playerY == treasureY,
                "Player and treasure should not be in the same location");
    }

    @Test
    public void testCalculateDistance() throws Exception {
        // Use reflection to test the private calculateDistance method
        Method calculateDistanceMethod = MonsterMashGame.class.getDeclaredMethod("calculateDistance");
        calculateDistanceMethod.setAccessible(true);

        // Verify distance calculation works
        double distance = (double) calculateDistanceMethod.invoke(game);
        Assertions.assertTrue(distance >= 0, "Distance should be non-negative");
    }

    @Test
    public void testValidMoves() throws Exception {
        // Use reflection to test the private movePlayer method
        Method movePlayerMethod = MonsterMashGame.class.getDeclaredMethod("movePlayer", String.class, int[].class);
        movePlayerMethod.setAccessible(true);

        // Create a previous position array
        int[] prevPos = new int[2];

        // Invoke movePlayer method
        boolean moveResult = (boolean) movePlayerMethod.invoke(game, "up", prevPos);

        Assertions.assertTrue(moveResult, "Valid move should return true");
    }

    @Test
    public void testInvalidMove() throws Exception {
        // Use reflection to test the private movePlayer method
        Method movePlayerMethod = MonsterMashGame.class.getDeclaredMethod("movePlayer", String.class, int[].class);
        movePlayerMethod.setAccessible(true);

        // Create a previous position array
        int[] prevPos = new int[2];

        // Try an invalid move
        boolean moveResult = (boolean) movePlayerMethod.invoke(game, "diagonal", prevPos);

        Assertions.assertFalse(moveResult, "Invalid move should return false");
    }

    @Test
    public void testTreasureHint() throws Exception {
        // Use reflection to test the private getTreasureHint method
        Method getTreasureHintMethod = MonsterMashGame.class.getDeclaredMethod("getTreasureHint");
        getTreasureHintMethod.setAccessible(true);

        // Get the treasure hint
        String hint = (String) getTreasureHintMethod.invoke(game);

        Assertions.assertNotNull(hint, "Treasure hint should not be null");
        Assertions.assertFalse(hint.isEmpty(), "Treasure hint should not be empty");
    }

    @Test
    public void testDirectionalHint() throws Exception {
        // Use reflection to test the private getDirectionalHint method
        Method getDirectionalHintMethod = MonsterMashGame.class.getDeclaredMethod("getDirectionalHint", int.class, int.class);
        getDirectionalHintMethod.setAccessible(true);

        // Get the directional hint
        String hint = (String) getDirectionalHintMethod.invoke(game, 5, 5);

        Assertions.assertNotNull(hint, "Directional hint should not be null");
        Assertions.assertFalse(hint.isEmpty(), "Directional hint should not be empty");
    }
}

