package org.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterStepDefinitions {
    private Monster monster;
    private int gridSize;
    private List<Integer[]> occupiedPositions;

    @Given("a grid size of {int}")
    public void a_grid_size_of(int size) {
        gridSize = size;
        occupiedPositions = new ArrayList<>();
    }

    @When("I create a monster")
    public void i_create_a_monster() {
        monster = new Monster(gridSize, occupiedPositions);
    }

    @Then("the monster should be created successfully")
    public void the_monster_should_be_created_successfully() {
        assertNotNull(monster, "Monster should not be null");
    }

    @Then("the monster should have a valid name")
    public void the_monster_should_have_a_valid_name() {
        assertNotNull(monster.getName(), "Monster name should not be null");
        assertTrue(monster.getName().contains(" "), "Monster name should have two parts");
    }

    @Then("the monster position should be within the grid boundaries")
    public void the_monster_position_should_be_within_the_grid_boundaries() {
        assertTrue(monster.getX() >= 0 && monster.getX() < gridSize, "X position should be within grid");
        assertTrue(monster.getY() >= 0 && monster.getY() < gridSize, "Y position should be within grid");
    }
}
