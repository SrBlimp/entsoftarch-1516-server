Feature: Edit proposal
  In order to edit a submited proposal
  As a proponent
  I want to edit a proposal

  Scenario: Edit existing proposal
    Given there is an existing proposal with title "Really interesting project"
    When I edit the proposal title with "Not interesting project"
    Then I have edited the "proposalSubmission" that "edits" the "proposal" with "title" "Not interesting project"

  Scenario: Edit existing proposal with new title null
    Given there is an existing proposal with title "Really interesting project"
    When I edit the proposal title with ""
    Then I have an error and the title is "Really interesting project"

  Scenario: Edit non-existent proposal
    Given there is not proposal
    When I try edit this proposal
    Then I have an error