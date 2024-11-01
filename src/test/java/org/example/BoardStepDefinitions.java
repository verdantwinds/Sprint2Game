package org.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardStepDefinitions {
    private Board board;
    private Player player;
    private List<Monster> monsters;
    private ByteArrayOutputStream outContent;
    private IllegalArgumentException exception;

    @Given("I want to create a board")
    public void i_want_to_create_a_board() {
        monsters = new ArrayList<>();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @When("I create a board with size {int}")
    public void i_create_a_board_with_size(int size) {
        board = new Board(size);
    }

    @Then("the board grid size should be {int}")
    public void the_board_grid_size_should_be(int expectedSize) {
        assertEquals(expectedSize, board.getGridSize());
    }

    @When("I attempt to create a board with size {int}")
    public void i_attempt_to_create_a_board_with_size(int size) {
        try {
            board = new Board(size);
            fail("Expected an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            exception = e;
        }
    }

    @Then("I should receive an IllegalArgumentException")
    public void i_should_receive_an_illegal_argument_exception() {
        assertNotNull(exception);
        assertEquals("Grid size must be at least 6x6", exception.getMessage());
    }

    @Given("I have a board of size {int}")
    public void i_have_a_board_of_size(int size) {
        board = new Board(size);
        monsters = new ArrayList<>();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Given("I have a player at position \\({int}, {int})")
    public void i_have_a_player_at_position(int x, int y) {
        player = new Player(x, y);
    }

    @Given("I have no monsters")
    public void i_have_no_monsters() {
        monsters = new ArrayList<>();
    }

    @When("I print the grid")
    public void i_print_the_grid() {
        board.printGrid(player, monsters);
    }

    @Then("the grid should contain a player emoji")
    public void the_grid_should_contain_a_player_emoji() {
        String output = outContent.toString();
        assertTrue(output.contains("🧙"), "Player emoji should be present");
    }

    @Then("the grid should have correct border emojis")
    public void the_grid_should_have_correct_border_emojis() {
        String output = outContent.toString();
        int fogCount = output.split("🌫️").length - 1;
        assertEquals((6 + 2) * 2 + (6 * 2), fogCount - 1, "Incorrect number of fog borders");
    }

    @Then("the grid should have forest and spooky emojis")
    public void the_grid_should_have_forest_and_spooky_emojis() {
        String output = outContent.toString();

        // Verify presence of forest emojis
        assertTrue(output.contains("🌲") || output.contains("🌳") ||
                output.contains("🍂") || output.contains("🌿") ||
                output.contains("🍄"), "Forest emojis should be present");

        // Verify presence of spooky emojis
        assertTrue(output.contains("⚰️") || output.contains("🕸️") ||
                output.contains("🦇") || output.contains("💀") ||
                output.contains("🪦"), "Spooky emojis should be present");
    }

    @Then("the output should include a legend")
    public void the_output_should_include_a_legend() {
        String output = outContent.toString();
        assertTrue(output.contains("🧙 - you, the player"), "Legend should explain player emoji");
        assertTrue(output.contains("🌫️ - border"), "Legend should explain border emoji");
    }
}