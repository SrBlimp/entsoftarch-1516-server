Feature: Comment proposal
  In order to comment created proposals
  As a user
  I want add new comment

  Scenario: Comment existing proposal
    Given there is an existing proposal with title "Really interesting project"
    And there is an existing submission of the proposal titled "Really interesting project"
    And there is an existing publication of the proposal titled "Really interesting project"
    When I comment the proposal with title "Really interesting project" with a comment with text "I love this project!"
    Then I have created a comment that comments a proposal with text "I love this project!"

  Scenario: Comment a publication but wrong status "SUBMITTED"
    Given there is an existing proposal with title "Really interesting project"
    And there is an existing submission of the proposal titled "Really interesting project"
    And there is an existing publication of the proposal titled "Really interesting project"
    And the status of the proposal titled "Really interesting project" is set to "SUBMITTED"
    When I comment the proposal with title "Really interesting project" with a comment with text "I love this project!"
    Then I get error 500 with message "Invalid proposal status 'SUBMITTED', should be 'PUBLISHED'"