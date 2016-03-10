# Created by Guille at 2/03/16

Feature: Create proposal
  In order to create a proposal
  I want to check the correct creation


  Scenario: Create proposal
    When I create the proposal with title "Create new proposal"
    Then new proposal with title "Create new proposal"

  Scenario: Create proposal and already created with title is "Proposal"
    Given there is an existing proposal with title "Proposal"
    When I create the proposal with title "Proposal"
    Then I get error 500 with message "Trying to create a proposal already created."

   Scenario: Create proposal and check description is not null
     When I create the proposal with title ""
     Then I get error 500 with message "Title can't be blank"