Feature: Submit proposal
  In order to submit a proposal
  As a proponent
  I want to create proposal submission

  Scenario: Submit existing proposal
    Given there is an existing proposal with title "Really interesting project"
    When I submit the proposal with title "Really interesting project"
    Then I have created a proposal submission that submits a proposal with title "Really interesting project"