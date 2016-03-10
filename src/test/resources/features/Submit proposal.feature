Feature: Submit proposal
  In order to submit a proposal
  As a proponent
  I want to create proposal submission

  Scenario: Submit existing proposal
    Given there is an existing proposal with title "Really interesting project"
    When I submit the proposal with title "Really interesting project"
    Then I have created a proposal submission that submits a proposal with title "Really interesting project"


  Scenario: Submit unexisting proposal
    Given there is an existing proposal with title "Really interesting project"
    When I submit an unexisting proposal
    Then I get error 500 with message "Submits mustn't be null"

  Scenario: Submit a submitted proposal
    Given there is an existing proposal with title "Really interesting project"
    And the status of the proposal titled "Really interesting project" is set to "SUBMITTED"
    When I submit the proposal with title "Really interesting project"
    Then I get error 500 with message "Invalid proposal status 'SUBMITTED', should be 'DRAFT'"

  Scenario: Submit a proposal already published
    Given there is an existing proposal with title "Really interesting project"
    And the status of the proposal titled "Really interesting project" is set to "PUBLISHED"
    When I submit the proposal with title "Really interesting project"
    Then I get error 500 with message "Invalid proposal status 'PUBLISHED', should be 'DRAFT'"

  Scenario: Submit a proposal already assigned
    Given there is an existing proposal with title "Really interesting project"
    And the status of the proposal titled "Really interesting project" is set to "ASSIGNED"
    When I submit the proposal with title "Really interesting project"
    Then I get error 500 with message "Invalid proposal status 'ASSIGNED', should be 'DRAFT'"

  Scenario: Submit a proposal already registered
    Given there is an existing proposal with title "Really interesting project"
    And the status of the proposal titled "Really interesting project" is set to "REGISTERED"
    When I submit the proposal with title "Really interesting project"
    Then I get error 500 with message "Invalid proposal status 'REGISTERED', should be 'DRAFT'"

  Scenario: Submit a proposal already deposited
    Given there is an existing proposal with title "Really interesting project"
    And the status of the proposal titled "Really interesting project" is set to "DEPOSITED"
    When I submit the proposal with title "Really interesting project"
    Then I get error 500 with message "Invalid proposal status 'DEPOSITED', should be 'DRAFT'"