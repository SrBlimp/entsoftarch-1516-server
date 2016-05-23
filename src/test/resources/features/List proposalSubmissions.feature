Feature: List proposal submissions
  In order to see the relevant proposal submissions to me
  I want to list them

  Background:
    Given there is an existing proposal with title "Professor1 proposal" by "professor1"
    And there is an existing proposal with title "Student1 proposal" by "student1"


  Scenario: List proposalSubmissions as professor1
    Given I login as "professor1" with password "password"
    When I submit the proposal with title "Professor1 proposal"
    And I list proposalSubmissions
    Then I get "1" proposalSubmissions
    And I get proposalSubmissions all with proposal title containing "Professor1"

  Scenario: List proposalSubmissions as student1
    Given I login as "student1" with password "password"
    When I submit the proposal with title "Student1 proposal"
    When I list proposalSubmissions
    Then I get "1" proposalSubmissions
    And I get proposalSubmissions all with proposal title containing "Student1"

  Scenario: List proposalSubmissions as admin
    Given I login as "professor1" with password "password"
    When I submit the proposal with title "Professor1 proposal"
    And I login as "student1" with password "password"
    And I submit the proposal with title "Student1 proposal"
    And I login as "admin" with password "password"
    And I list proposalSubmissions
    Then I get "2" proposalSubmissions