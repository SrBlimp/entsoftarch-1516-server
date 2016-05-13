Feature: List proposals
  In order to see the relevant proposals to me
  I want to list them

  Background:
    Given there is an existing proposal with title "Professor1 proposal" by "professor1"
    And there is an existing proposal with title "Student1 proposal" by "student1"

  Scenario: List proposals as professor1
    Given I login as "professor1" with password "password"
    When I list proposals
    Then I get "1" proposals
      And I get proposals all with title containing "Professor1"

  Scenario: List proposals as student1
    Given I login as "student1" with password "password"
    When I list proposals
    Then I get "1" proposals
    And I get proposals all with title containing "Student1"

  Scenario: List proposals as admin
    Given I login as "admin" with password "password"
    When I list proposals
    Then I get "2" proposals