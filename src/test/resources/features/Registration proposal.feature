Feature: Register proposal
  In order to accept thesis projects
  As a coordinator
  I want to accept proposals with its participants

  Scenario: Register published proposal
    Given there is an existing proposal with title "Register my proposal"
    And the status of the proposal titled "Register my proposal" is "ASSIGNED"
    And the student of the proposal titled "Register my proposal" is not null
    And the director of the proposal titled "Register my proposal" is not null
    When I register published proposal titled "Register my proposal"