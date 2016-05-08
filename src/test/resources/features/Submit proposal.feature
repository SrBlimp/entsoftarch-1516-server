Feature: Submit proposal
  In order to submit a proposal
  As a proponent
  I want to create proposal submission

  Background:
    Given there is an existing proposal with title "Really interesting project" by "professor1"

  Scenario: Submit existing proposal with login
    Given I login as "professor1" with password "password"
    When I submit the proposal with title "Really interesting project"
    Then I have created a proposal submission that submits a proposal with title "Really interesting project"
    And the status of the proposal titled "Really interesting project" is "SUBMITTED"
    And an email has been sent to "coordinator1@thesismarket" with subject "Proposal Submission" and containing "Really interesting project"


  Scenario: Submit unexisting proposal
    Given I login as "professor1" with password "password"
    When I submit an unexisting proposal
    Then I get error 400 with message "the proposal should exist"


  Scenario: Submit existing proposal which i am not the creator
    Given I login as "student1" with password "password"
    When I submit the proposal with title "Really interesting project"
    Then I get error 403 with message "Access is denied"
    And the status of the proposal titled "Really interesting project" is "DRAFT"


  Scenario: Submit existing proposal with bad login
    Given I login as "professor24" with password "password24"
    When I submit the proposal with title "Really interesting project"
    Then I get error 401 with message "Bad credentials"
    And the status of the proposal titled "Really interesting project" is "DRAFT"


  Scenario: Submit existing proposal without login
    Given I'm not logged in
    When I submit the proposal with title "Really interesting project"
    Then I get error 401 with message "Bad credentials"
    And the status of the proposal titled "Really interesting project" is "DRAFT"


  Scenario: Submit a submitted proposal
    Given I login as "professor1" with password "password"
    And the status of the proposal titled "Really interesting project" is set to "SUBMITTED"
    When I submit the proposal with title "Really interesting project"
    Then I get error 500 with message "Invalid proposal status 'SUBMITTED', should be 'DRAFT'"


  Scenario: Submit a proposal already published
    Given I login as "professor1" with password "password"
    And the status of the proposal titled "Really interesting project" is set to "PUBLISHED"
    When I submit the proposal with title "Really interesting project"
    Then I get error 500 with message "Invalid proposal status 'PUBLISHED', should be 'DRAFT'"

  Scenario: Submit a proposal already assigned
    Given I login as "professor1" with password "password"
    And the status of the proposal titled "Really interesting project" is set to "ASSIGNED"
    When I submit the proposal with title "Really interesting project"
    Then I get error 500 with message "Invalid proposal status 'ASSIGNED', should be 'DRAFT'"

  Scenario: Submit a proposal already registered
    Given I login as "professor1" with password "password"
    And the status of the proposal titled "Really interesting project" is set to "REGISTERED"
    When I submit the proposal with title "Really interesting project"
    Then I get error 500 with message "Invalid proposal status 'REGISTERED', should be 'DRAFT'"

  Scenario: Submit a proposal already deposited
    Given I login as "professor1" with password "password"
    And the status of the proposal titled "Really interesting project" is set to "DEPOSITED"
    When I submit the proposal with title "Really interesting project"
    Then I get error 500 with message "Invalid proposal status 'DEPOSITED', should be 'DRAFT'"
