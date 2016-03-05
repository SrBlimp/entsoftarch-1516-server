Feature: Withdraw proposal
  In order to withdraw a submitted proposal not yet published
  As a proponent
  I want to create a proposal withdrawal

  Scenario: Withdraw existing proposal submission not yet published
    Given there is an existing proposal with title "Really interesting project"
      And there is an existing submission of the proposal titled "Really interesting project"
      And the status of the proposal titled "Really interesting project" is "SUBMITTED"
      And there is not a publication of the submission of the proposal titled "Really interesting project"
    When I withdraw the submission of the proposal titled "Really interesting project"
    Then I have created a withdrawal of the submission of the proposal titled "Really interesting project"
      And the status of the proposal titled "Really interesting project" is "DRAFT"

  Scenario: Withdraw un-submitted proposal
    Given there is an existing proposal with title "Really interesting project"
      And the status of the proposal titled "Really interesting project" is "DRAFT"
    When I withdraw an un-existing submission
    Then I get error 500 with message "Trying to withdraw un-existing submission"
      And the status of the proposal titled "Really interesting project" is "DRAFT"

  Scenario: Withdraw submitted proposal but wrong status "DRAFT"
    Given there is an existing proposal with title "Really interesting project"
      And there is an existing submission of the proposal titled "Really interesting project"
      And the status of the proposal titled "Really interesting project" is set to "DRAFT"
    When I withdraw the submission of the proposal titled "Really interesting project"
    Then I get error 500 with message "Invalid proposal status 'DRAFT', should be 'SUBMITTED'"
      And the status of the proposal titled "Really interesting project" is "DRAFT"

  Scenario: Withdraw proposal submission already published
    Given there is an existing proposal with title "Really interesting project"
      And there is an existing submission of the proposal titled "Really interesting project"
      And there is an existing publication of the proposal titled "Really interesting project"
      And the status of the proposal titled "Really interesting project" is "PUBLISHED"
    When I withdraw the submission of the proposal titled "Really interesting project"
    Then I get error 500 with message "Invalid proposal status 'PUBLISHED', should be 'SUBMITTED'"
      And the status of the proposal titled "Really interesting project" is "PUBLISHED"

  Scenario: Withdraw proposal submission with a publication but wrong status "SUBMITTED"
    Given there is an existing proposal with title "Really interesting project"
      And there is an existing submission of the proposal titled "Really interesting project"
      And there is an existing publication of the proposal titled "Really interesting project"
      And the status of the proposal titled "Really interesting project" is set to "SUBMITTED"
    When I withdraw the submission of the proposal titled "Really interesting project"
    Then I get error 500 with message "To withdraw a proposal submission it should be unpublished"