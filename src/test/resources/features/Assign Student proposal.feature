Feature: Assign student to a proposal
  In order to assign a student to a published proposal
  As a coordinator
  I want to assign a proposal to a student

  Scenario: Assign existing proposal submission to an existing user
    Given there is an existing proposal with title "Really interesting project"
      And there is an existing submission of the proposal titled "Really interesting project"
      And there is an existing publication of the proposal titled "Really interesting project"
      And the status of the proposal titled "Really interesting project" is "PUBLISHED"
      And there is an existing student with id "uname"
      And there is an existing offer for the user "uname" and the proposal "Really interesting project"
    When I assign a existing user with id "uname" to the published proposal titled "Really interesting project"
    Then I have created a withdrawal of the submission of the proposal titled "Really interesting project"


