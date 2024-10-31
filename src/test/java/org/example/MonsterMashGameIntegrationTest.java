package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MonsterMashGameIntegrationTest {
    @Test
    public void testGameFlow() {

        MonsterMashGame game = new MonsterMashGame(10);
        Assertions.assertDoesNotThrow(() -> {
            game.play();
        }, "Game should start without exceptions");
    }
}
