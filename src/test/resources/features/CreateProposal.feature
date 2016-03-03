# Created by Guille at 2/03/16

Feature: Create proposal
  In order to create a proposal
  I want to check the correct creation


  Scenario: Create proposal
    Given new proposal "Create new proposal"
    When I create the proposal with title "Create new proposal"
    Then new proposal with title "Create new proposal"