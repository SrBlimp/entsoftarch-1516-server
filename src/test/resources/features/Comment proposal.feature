Feature: Comment proposal
  In order to comment created proposals
  As a user
  I want add new comment

  Scenario: Comment existing proposal
    Given there is an existing proposal with title "Patata"
    And there is an existing submission of the proposal titled "Patata"
    And there is an existing proposal publication of a submission of the proposal titled "Patata"
    When I comment the proposal with title "Patata" with a comment with text "I love this project!"
    Then I have created a comment that comments a proposal with text "I love this project!"