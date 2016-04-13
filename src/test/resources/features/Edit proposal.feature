Feature: Edit proposal
  In order to edit a submited proposal
  As a proponent
  I want to edit a proposal

  Scenario: Edit existing proposal
    Given I create the proposal with title "Very interesting project"
    When I edit the previous proposal title with "Not interesting project"
    Then I have edited the proposal with title "Not interesting project"

  Scenario: Edit existing proposal with new title null
    Given I create the proposal with title "Very interesting project"
    When I edit the previous proposal title with ""
    Then I get error 400 with message "Title cannot be blank"

  Scenario: Edit non-existent proposal
    Given there isn't any proposal
    When I edit the previous proposal title with "NewProject"
    Then new proposal with title "NewProject"