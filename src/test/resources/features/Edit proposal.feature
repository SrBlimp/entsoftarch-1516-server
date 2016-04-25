Feature: Edit proposal
  In order to edit a submited proposal
  As a proponent
  I want to edit a proposal

  Scenario: Edit existing proposal logged as professor OK
    Given there is an existing proposal with title "Very interesting project" by "professor1"
      And I login as "professor1" with password "password"
    When I edit the proposal with title "Very interesting project" with new title "Not interesting project"
    Then I have edited the proposal with title "Not interesting project"
      And check title editor user is logged

  Scenario: Edit existing proposal logged as student KO
    Given there is an existing proposal with title "Very interesting project" by "professor1"
      And I login as "student1" with password "password"
    When I edit the proposal with title "Very interesting project" with new title "Not interesting project"
    Then I get error 403 with message "Access is denied"


  Scenario: Edit existing proposal with new title null logged as professor
    Given there is an existing proposal with title "Very interesting project" by "professor1"
      And I login as "professor1" with password "password"
    When I edit the proposal with title "Very interesting project" with new title ""
    Then I get error 400 with message "Title cannot be blank"

  Scenario: Edit existing proposal with new title null logged as student
    Given I login as "student1" with password "password"
    And I create the proposal with title "Very interesting project"
    When I edit the proposal with title "Very interesting project" with new title ""
    Then I get error 400 with message "Title cannot be blank"


#  Scenario: Edit non-existent proposal
#    Given there isn't any proposal
#    When I edit the proposal with title "" with new title "Not interesting project"
#    Then I get error 500 with message ""
   