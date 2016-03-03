Feature: Withdraw proposal
  In order to withdraw a submitted proposal not yet published
  As a proponent
  I want to create a proposal withdrawal

  Scenario: Withdraw existing proposal submission not yet published
    Given there is an existing proposal with title "Really interesting project"
    And there is an existing submission of the proposal titled "Really interesting project"
    When I withdraw the submission of the proposal titled "Really interesting project"
    Then I have created a withdrawal of the submission of the proposal titled "Really interesting project"