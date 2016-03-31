Feature: Edit proposal
  In order to edit a submited proposal
  As a proponent
  I want to edit a proposal

  Scenario: Edit existing proposal
    Given there is an existing proposal with title "Really interesting project"
    When I edit the proposal title with "Not interesting project"
    Then I have edited the proposal "proposalSubmission" that "edits" the "proposal" with "title" "Not interesting project"

  Scenario: Edit existing proposal with new title null
    Given there is an existing proposal with title "Really interesting project"
    When I edit the proposal title with ""
    Then I get error 400 with message "Title cannot be blank"

  Scenario: Edit non-existent proposal
    Given there isn't any proposal
    When I edit the proposal title with "Project"
    Then I get error 500 with message "This proposal doesn't exist"