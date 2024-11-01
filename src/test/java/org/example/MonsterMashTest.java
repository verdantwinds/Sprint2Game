package org.example;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterMashTest {
    @Test
    public void testMonsterInstantiation() {
        int gridSize = 5;
        List<Integer[]> occupiedPositions = new ArrayList<>();
        Monster monster = new Monster(gridSize, occupiedPositions);

        assertNotNull(monster, "Monster should be instantiated correctly.");
    }

    @Test
    public void testMonsterNameInstantiation() {
        int gridSize = 5;
        List<Integer[]> occupiedPositions = new ArrayList<>();
        Monster monster = new Monster(gridSize, occupiedPositions);

        assertNotNull(monster.getName(), "Monster's name should not be null.");
        assertFalse(monster.getName().isEmpty(), "Monster's name should not be empty.");
    }

    @Test
    public void testMonsterMovement() {
        int gridSize = 5;
        List<Integer[]> occupiedPositions = new ArrayList<>();
        Monster monster = new Monster(gridSize, occupiedPositions) ;

        monster.setX(3);
        monster.setY(4);

        assertEquals(3, monster.getX(), "Monster's X position should be updated to 3.");
        assertEquals(4, monster.getY(), "Monster's Y position should be updated to 4.");
    }

    @Test
    public void testIsNearPlayer() {
        int gridSize = 5;
        List<Integer[]> occupiedPositions = new ArrayList<>();
        Monster monster = new Monster(gridSize, occupiedPositions);

        monster.setX(2);
        monster.setY(0);

        // one or less to get true
        assertTrue(monster.isNearPlayer(1, 0), "Player at (1, 2) should be near the monster.");

        assertFalse(monster.isNearPlayer(4, 4), "Player at (4, 4) should not be near the monster.");
    }

    @Test
    public void testIsOnSamePosition() {
        int gridSize = 5;
        List<Integer[]> occupiedPositions = new ArrayList<>();
        Monster monster = new Monster(gridSize, occupiedPositions);

        monster.setX(2);
        monster.setY(2);

        assertTrue(monster.isOnSamePosition(2, 2), "Player and monster should be on the same position");

        assertFalse(monster.isOnSamePosition(1, 2), "Player and monster are not on the same position.");
    }
}
