Feature: Monster Behavior and Creation

  Scenario: Creating a monster with valid grid size
    Given a grid size of 10
    When I create a monster
    Then the monster should be created successfully
    And the monster should have a valid name
    And the monster position should be within the grid boundaries

