
Feature: Create proposal
  In order to create a proposal
  I want to check the correct creation

  Scenario: Create proposal
    Given I login as "student1" with password "password"
    When I create the proposal with title "Create new proposal"
    Then new proposal with title "Create new proposal"
    Then check proposal status is "DRAFT"
    Then check proposal creator is user logged
    And an email has been sent to "student1@thesismarket" with subject "Create Proposal" and containing "Create new proposal"

  Scenario: Create proposal and already created with title is "Proposal"
    Given I login as "professor1" with password "password"
    And there is an existing proposal with title "Proposal"
    When I create the proposal with title "Proposal"
    Then I get error 500 with message "Trying to create a proposal already created."

  Scenario: Create proposal and check title is not blank
    Given I login as "professor1" with password "password"
    When I create the proposal with title ""
    Then I get error 400 with message "Title cannot be blank"

  Scenario: Create proposal with coordinator user
    Given I login as "coordinator1" with password "password"
    When I create the proposal with title "Create proposal"
    Then I get error 403 with message "Access is denied"