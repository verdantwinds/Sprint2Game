package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TreasureTest {
    private Treasure treasure;
    private static final int GRID_SIZE = 10;

    @BeforeEach
    void setUp() {
        // Typically placed in a somewhat central location
        treasure = new Treasure(GRID_SIZE, 5, 5);
    }

    @Test
    void testTreasureInitialization() {
        // Verify that total treasures is calculated correctly (20% of grid size, rounded up)
        assertEquals(2, treasure.getTotalTreasures(),
                "Total treasures should be 20% of grid size (rounded up)");

        assertEquals(0, treasure.getTreasuresCollected(),
                "Initially, no treasures should be collected");

        assertEquals(5, treasure.getX(), "X coordinate should be set correctly");
        assertEquals(5, treasure.getY(), "Y coordinate should be set correctly");
    }

    @Test
    void testIncrementTreasuresCollected() {
        treasure.incrementTreasuresCollected();
        assertEquals(1, treasure.getTreasuresCollected(),
                "Treasures collected should increment correctly");
    }

    @Test
    void testSetPosition() {
        treasure.setPosition(3, 7);
        assertEquals(3, treasure.getX(), "X position should update");
        assertEquals(7, treasure.getY(), "Y position should update");
    }

    @Test
    void testProximityHints() {
        // Test various distances
        assertEquals("This is it! The treasure!",
                treasure.getProximityHint(5, 5, GRID_SIZE),
                "On exact treasure location");

        // Very close (within 20% of grid size)
        assertEquals("The treasure is very close!",
                treasure.getProximityHint(6, 6, GRID_SIZE),
                "Close proximity hint");

        // Moderately close
        assertEquals("You're getting closer to the treasure...",
                treasure.getProximityHint(7, 7, GRID_SIZE),
                "Moderate proximity hint");

        // Not close
        assertEquals("You're not close to the treasure...",
                treasure.getProximityHint(9, 9, GRID_SIZE),
                "Far proximity hint");

        // Very far
        assertEquals("The treasure is far away.",
                treasure.getProximityHint(0, 0, GRID_SIZE),
                "Very far proximity hint");
    }

    @Test
    void testDirectionalHints() {
        // Moving closer tests
        assertEquals("Moving left got you closer!",
                treasure.getDirectionalHint(5, 6, 6, 6),
                "Moving left towards treasure");

        assertEquals("Moving right got you closer!",
                treasure.getDirectionalHint(5, 4, 4, 4),
                "Moving right towards treasure");

        assertEquals("Moving up got you closer!",
                treasure.getDirectionalHint(6, 5, 6, 6),
                "Moving up towards treasure");

        assertEquals("Moving down got you closer!",
                treasure.getDirectionalHint(4, 5, 4, 4),
                "Moving down towards treasure");

        // Moving away tests
        assertEquals("Moving left took you further away!",
                treasure.getDirectionalHint(3, 4, 4, 4),
                "Moving left away from treasure");

        assertEquals("Moving right took you further away!",
                treasure.getDirectionalHint(6, 6, 5, 6),
                "Moving right away from treasure");

        // Neutral move
        assertEquals("Your move didn't change your distance to the treasure.",
                treasure.getDirectionalHint(5, 5, 5, 5),
                "No change in distance");
    }

    @Test
    void testCalculateDistance() {
        // Use reflection to test private method if needed, or verify through public methods
        double expectedDistance = Math.sqrt(2); // Distance from (5,5) to (6,6)
        assertEquals(expectedDistance,
                Math.sqrt(Math.pow(6 - 5, 2) + Math.pow(6 - 5, 2)),
                0.001,
                "Distance calculation should be correct");
    }
}