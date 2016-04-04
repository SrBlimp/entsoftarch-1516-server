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
    Then I have a registered proposal titled "Register my proposal"
    And the status of the proposal titled "Register my proposal" is "REGISTERED"

  Scenario: Register a submitted proposal but wrong status "DRAFT"
    Given there is an existing proposal with title "Register my draft proposal"
    And the status of the proposal titled "Register my draft proposal" is "DRAFT"
    When I register un-published proposal
    Then I get error 500 with message "Invalid status proposal 'DRAFT', should be 'ASSIGNED'"
    And the status of the proposal titled "Register my proposal" is "DRAFT"

  Scenario: Register un-assigned proposal
    Given there is an existing proposal with title "Register my un-assigned proposal"
    And the status of the proposal titled "Register my un-assigned proposal" is "PUBLISHED"
    When I register un-assigned proposal
    Then I get error 500 with message "Invalid status proposal 'PUBLISHED', should be 'ASSIGNED'"
    And the status of the proposal titled "Register my un-assigned proposal" is "PUBLISHED"