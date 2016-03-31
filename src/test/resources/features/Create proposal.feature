

Feature: Create proposal
  In order to create a proposal
  I want to check the correct creation

  Background:
    Given I login as "professor1" with password "password"

  Scenario: Create proposal
    When I create the proposal with title "Create new proposal"
    Then new proposal with title "Create new proposal"

  Scenario: Create proposal and already created with title is "Proposal"
    Given there is an existing proposal with title "Proposal"
    When I create the proposal with title "Proposal"
    Then I get error 500 with message "Trying to create a proposal already created."

  Scenario: Create proposal and check title is not blank
    When I create the proposal with title ""
    Then I get error 400 with message "Title cannot be blank"