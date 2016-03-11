Feature: Withdraw proposal
  In order to withdraw a submitted proposal not yet published
  As a proponent
  I want to create a proposal withdrawal

  Scenario: Withdraw existing proposal submission not yet published
    Given I login as "professor1" with password "password"
      And there is an existing proposal with title "Really interesting project" by "professor1"
      And there is an existing submission of the proposal titled "Really interesting project"
      And the status of the proposal titled "Really interesting project" is "SUBMITTED"
      And there is not a publication of the submission of the proposal titled "Really interesting project"
    When I withdraw the submission of the proposal titled "Really interesting project"
    Then I have created a withdrawal of the submission of the proposal titled "Really interesting project"
      And the status of the proposal titled "Really interesting project" is "DRAFT"

  Scenario: Withdraw un-submitted proposal
    Given I login as "professor1" with password "password"
      And there is an existing proposal with title "Really interesting project" by "professor1"
      And the status of the proposal titled "Really interesting project" is "DRAFT"
    When I withdraw an un-existing submission
    Then I get error 400 with message "may not be null"
      And the status of the proposal titled "Really interesting project" is "DRAFT"

  Scenario: Withdraw submitted proposal but wrong status "DRAFT"
    Given I login as "professor1" with password "password"
      And there is an existing proposal with title "Really interesting project" by "professor1"
      And there is an existing submission of the proposal titled "Really interesting project"
      And the status of the proposal titled "Really interesting project" is set to "DRAFT"
    When I withdraw the submission of the proposal titled "Really interesting project"
    Then I get error 500 with message "Invalid proposal status 'DRAFT', should be 'SUBMITTED'"
      And the status of the proposal titled "Really interesting project" is "DRAFT"

  Scenario: Withdraw proposal submission already published
    Given I login as "professor1" with password "password"
      And there is an existing proposal with title "Really interesting project" by "professor1"
      And there is an existing submission of the proposal titled "Really interesting project"
      And there is an existing publication of the proposal titled "Really interesting project"
      And the status of the proposal titled "Really interesting project" is "PUBLISHED"
    When I withdraw the submission of the proposal titled "Really interesting project"
    Then I get error 500 with message "Invalid proposal status 'PUBLISHED', should be 'SUBMITTED'"
      And the status of the proposal titled "Really interesting project" is "PUBLISHED"

  Scenario: Withdraw proposal submission with a publication but wrong status "SUBMITTED"
    Given I login as "professor1" with password "password"
      And there is an existing proposal with title "Really interesting project" by "professor1"
      And there is an existing submission of the proposal titled "Really interesting project"
      And there is an existing publication of the proposal titled "Really interesting project"
      And the status of the proposal titled "Really interesting project" is set to "SUBMITTED"
    When I withdraw the submission of the proposal titled "Really interesting project"
    Then I get error 500 with message "To withdraw a proposal submission it should be unpublished"