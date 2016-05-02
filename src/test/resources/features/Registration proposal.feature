Feature: Register proposal
  In order to accept thesis projects
  As a coordinator
  I want to accept proposals with its participants

  Scenario: Register published proposal
    Given there is an existing proposal with title "Register my proposal"
      And I login as "coordinator1" with password "password"
      And there is an existing submission of the proposal titled "Register my proposal"
      And there is an existing publication of the proposal titled "Register my proposal"
      And the status of the proposal titled "Register my proposal" is set to "ASSIGNED"
      And the student of the proposal titled "Register my proposal" is not null
      And the director of the proposal titled "Register my proposal" is set to "professor1"
    When I register published proposal titled "Register my proposal"
    Then I have a registered proposal titled "Register my proposal"
      And the status of the proposal titled "Register my proposal" is "REGISTERED"

  Scenario: Register a submitted proposal but wrong status "DRAFT"
    Given there is an existing proposal with title "Register my draft proposal"
      And I login as "coordinator1" with password "password"
      And the status of the proposal titled "Register my draft proposal" is set to "DRAFT"
    When I register un-published proposal
    Then I get error 400 with message "may not be null"
      And the status of the proposal titled "Register my draft proposal" is set to "DRAFT"

  Scenario: Register un-assigned proposal
    Given there is an existing proposal with title "Register my un-assigned proposal"
      And I login as "coordinator1" with password "password"
      And the status of the proposal titled "Register my un-assigned proposal" is set to "PUBLISHED"
    When I register un-assigned proposal
    Then I get error 400 with message "may not be null"
      And the status of the proposal titled "Register my un-assigned proposal" is set to "PUBLISHED"