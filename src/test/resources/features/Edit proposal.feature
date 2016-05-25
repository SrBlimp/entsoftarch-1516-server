Feature: Edit proposal
  In order to edit a submited proposal
  As a proponent
  I want to edit a proposal

  Scenario: Edit own proposal allowed
    Given I login as "professor1" with password "password"
      And there is an existing proposal with title "Very interesting project" by "professor1"
    When I edit the proposal with title "Very interesting  project" with new title "Not interesting project"
    Then I have edited the proposal with title "Not interesting project"
      And check title editor user is logged
    
  Scenario: Edit professor1 proposal logged as student1 not allowed
    Given I login as "student1" with password "password"
      And there is an existing proposal with title "Very interesting project" by "professor1"
    When I edit the proposal with title "Very interesting project" with new title "Not interesting project"
    Then I get error 403 with message "Access is denied"


    #Scenarios with new title null

  Scenario: Edit existing proposal with new title null logged as professor
    Given there is an existing proposal with title "Really interesting project" by "professor1"
      And I login as "professor1" with password "password"
    When I edit the proposal with title "Very interesting project" with new title ""
    Then I get error 400 with message "Title cannot be blank"

  Scenario: Edit existing proposal with new title null logged as student
    Given there is an existing proposal with title "Really interesting project" by "professor1"
      And I login as "student1" with password "password"
    When I edit the proposal with title "Very interesting project" with new title ""
    Then I get error 400 with message "Title cannot be blank"


    #Scenarios without autentication!

  Scenario: Edit existent proposal without autentication
    Given there is an existing proposal with title "Very interesting project" by "professor1"
    When I edit the proposal with title "Very interesting project" with new title "Not interesting project"
    Then I get error 401 with message "Full authentication is required to access this resource"

  Scenario: Edit existent proposal without autentication with null text
    Given there is an existing proposal with title "Very interesting project" by "professor1"
    When I edit the proposal with title "Very interesting project" with new title ""
    Then I get error 401 with message "Full authentication is required to access this resource"

   