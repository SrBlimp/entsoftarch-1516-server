Feature: Assign student to a proposal
  In order to assign a student to a published proposal
  As a coordinator
  I want to assign a proposal to a student

  Scenario: Assign existing proposal submission to an existing user
    Given there is an existing proposal with title "Really interesting project"
      And there is an existing submission of the proposal titled "Really interesting project"
      And there is an existing publication of the proposal titled "Really interesting project"
      And the status of the proposal titled "Really interesting project" is "PUBLISHED"
      And there is an existing user with id "123"
    When I assign a existing user to the published proposal titled "Really interesting project"
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