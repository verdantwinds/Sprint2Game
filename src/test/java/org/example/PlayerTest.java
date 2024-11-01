package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    private static final int GRID_SIZE = 5;

    @BeforeEach
    void setUp() {
        // Initialize a player at a default position before each test
        player = new Player(2, 2);
    }

    @Test
    void testInitialPosition() {
        // Verify that the player is initialized with the correct starting coordinates
        assertEquals(2, player.getX(), "Initial X coordinate should be set correctly");
        assertEquals(2, player.getY(), "Initial Y coordinate should be set correctly");
    }

    @Test
    void testSetPosition() {
        // Verify that setPosition method works correctly
        player.setPosition(3, 4);
        assertEquals(3, player.getX(), "X coordinate should be updated");
        assertEquals(4, player.getY(), "Y coordinate should be updated");
    }

    @Test
    void testMoveUp() {
        // Test moving up when possible
        assertTrue(player.move("up", GRID_SIZE), "Move up should return true");
        assertEquals(1, player.getY(), "Y coordinate should decrement when moving up");
    }

    @Test
    void testMoveDown() {
        // Test moving down when possible
        assertTrue(player.move("down", GRID_SIZE), "Move down should return true");
        assertEquals(3, player.getY(), "Y coordinate should increment when moving down");
    }

    @Test
    void testMoveLeft() {
        // Test moving left when possible
        assertTrue(player.move("left", GRID_SIZE), "Move left should return true");
        assertEquals(1, player.getX(), "X coordinate should decrement when moving left");
    }

    @Test
    void testMoveRight() {
        // Test moving right when possible
        assertTrue(player.move("right", GRID_SIZE), "Move right should return true");
        assertEquals(3, player.getX(), "X coordinate should increment when moving right");
    }

    @Test
    void testMoveUpBoundary() {
        // Move to the top boundary of the grid
        player = new Player(2, 0);
        assertFalse(player.move("up", GRID_SIZE), "Move up at top boundary should return false");
    }

    @Test
    void testMoveDownBoundary() {
        // Move to the bottom boundary of the grid
        player = new Player(2, GRID_SIZE - 1);
        assertFalse(player.move("down", GRID_SIZE), "Move down at bottom boundary should return false");
    }

    @Test
    void testMoveLeftBoundary() {
        // Move to the left boundary of the grid
        player = new Player(0, 2);
        assertFalse(player.move("left", GRID_SIZE), "Move left at left boundary should return false");
    }

    @Test
    void testMoveRightBoundary() {
        // Move to the right boundary of the grid
        player = new Player(GRID_SIZE - 1, 2);
        assertFalse(player.move("right", GRID_SIZE), "Move right at right boundary should return false");
    }

    @Test
    void testInvalidMove() {
        // Test an invalid move direction
        assertFalse(player.move("diagonal", GRID_SIZE), "Invalid move should return false");
    }

    @Test
    void testCaseInsensitiveMove() {
        // Test that move directions are case-insensitive
        assertTrue(player.move("UP", GRID_SIZE), "Uppercase 'UP' should be valid");
        assertEquals(1, player.getY(), "Y coordinate should decrement when moving up");
    }
}