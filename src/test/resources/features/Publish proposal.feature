Feature: Publish proposal
  In order to publish a proposal
  As a coordinator
  I want review and make public the proposal

  Scenario: Publish proposal
    Given there is an existing proposal with title "Publish existing proposal submission"
    And there is an existing submission of the proposal titled "Publish existing proposal submission"
    When I publish the proposal with title "Publish existing proposal submission"
    Then I have a proposal publication with title "Publish existing proposal submission"

  Scenario: Publish a proposal published 
    Given there is an existing proposal with title "Publish a proposal published" 
    And there is an existing submission of the proposal titled "Publish a proposal published"
    And there is an existing publication of the proposal titled "Publish a proposal published" 
    When I publish the proposal with title "Publish a proposal published" 
    Then I get error 500 with message "The proposal is already published"

  Scenario: Assign to student a publish proposal
    Given there is an existing proposal with title "Publish a proposal published"
    And there is an existing submission of the proposal titled "Publish a proposal published"
    And there is an existing publication of the proposal titled "Publish a proposal published"
    When I publish the proposal with title "Publish a proposal published"
    Then I assign the proposal with title "Publish a proposal published" to student with name "Pepito"

  Scenario: Assign to director a publish proposal
    Given there is an existing proposal with title "Publish a proposal published"
    And there is an existing submission of the proposal titled "Publish a proposal published"
    And there is an existing publication of the proposal titled "Publish a proposal published"
    When I publish the proposal with title "Publish a proposal published"
    Then I assign the proposal with title "Publish a proposal published" to director with name "Director Pepito"

  Scenario: Assign to codirector a publish proposal
    Given there is an existing proposal with title "Publish a proposal published"
    And there is an existing submission of the proposal titled "Publish a proposal published"
    And there is an existing publication of the proposal titled "Publish a proposal published"
    When I publish the proposal with title "Publish a proposal published"
    Then I assign the proposal with title "Publish a proposal published" to codirector with name "CoDirector Pepito"

  Scenario: Withdraw publish a proposal but wrong status "DRAFT"
    Given there is an existing proposal with title "Draft a proposal published"
    And there is an existing submission of the proposal titled "Draft a proposal published"
    And the status of the proposal titled "Draft a proposal published" is set to "DRAFT"
    When I withdraw the submission of the proposal titled "Draft a proposal published"
    Then I get error 500 with message "Invalid proposal status 'DRAFT', should be 'SUBMITTED'"
    And the status of the proposal titled "Draft a proposal published" is "DRAFT"


  Scenario: Comment publish proposal
    Given there is an existing proposal with title "Publish a proposal published"
    And there is an existing submission of the proposal titled "Publish a proposal published"
    And there is an existing publication of the proposal titled "Publish a proposal published"
    When I comment the proposal with title "Publish a proposal published" with a comment with text "Comment publish proposal"
    Then I have created a comment that comments a proposal with text "Comment publish proposal"