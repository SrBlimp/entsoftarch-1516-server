Feature: Comment proposal
  In order to comment created proposals
  As a user
  I want add new comment

  Scenario: Comment existing proposal
    Given there is an existing proposal with title "Really interesting project"
    When I comment the proposal with a comment
    Then I have created a comment that comments a proposal with text "I love this project!"