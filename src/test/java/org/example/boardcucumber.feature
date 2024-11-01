Feature: Board Grid Representation

  Scenario: Create a valid board
    Given I want to create a board
    When I create a board with size 6
    Then the board grid size should be 6

  Scenario: Attempt to create an invalid board
    Given I want to create a board
    When I attempt to create a board with size 5
    Then I should receive an IllegalArgumentException

  Scenario: Print board grid with a player
    Given I have a board of size 6
    And I have a player at position (2, 3)
    And I have no monsters
    When I print the grid
    Then the grid should contain a player emoji
    And the grid should have correct border emojis
    And the grid should have forest and spooky emojis

  Scenario: Verify board grid legend
    Given I have a board of size 6
    And I have a player at position (2, 3)
    And I have no monsters
    When I print the grid
    Then the output should include a legend