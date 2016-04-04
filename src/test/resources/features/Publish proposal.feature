Feature: Publish proposal
  In order to publish a proposal
  As a coordinator
  I want review and make public the proposal

  Scenario: Publish an un-submitted proposal
    Given I login as "coordinator" with password "password"
    And there is an existing proposal with title "Publish a draft proposal"
    And there is not a submission of the proposal titled "Publish a draft proposal"
    And the status of the proposal titled "Publish a draft proposal" is "DRAFT"
    When I publish an un-existing submission
    Then I get error 400 with message "may not be null"
    And the status of the proposal titled "Publish a draft proposal" is "DRAFT"

  Scenario: Publish a submitted proposal but wrong status "DRAFT"
    Given there is an existing proposal with title "Publish a submitted proposal but wrong status"
    And there is an existing submission of the proposal titled "Publish a submitted proposal but wrong status"
    And the status of the proposal titled "Publish a submitted proposal but wrong status" is set to "DRAFT"
    When I publish the proposal with title "Publish a submitted proposal but wrong status"
    Then I get error 500 with message "Invalid proposal status 'DRAFT', should be 'SUBMITTED'"
    And the status of the proposal titled "Publish a submitted proposal but wrong status" is "DRAFT"

  Scenario: Publish a submitted proposal
    Given there is an existing proposal with title "Publish a submitted proposal"
    And there is an existing submission of the proposal titled "Publish a submitted proposal"
    And the status of the proposal titled "Publish a submitted proposal" is "SUBMITTED"
    And there is not a publication of the submission of the proposal titled "Publish a submitted proposal"
    When I publish the proposal with title "Publish a submitted proposal"
    Then I have a proposal publication with title "Publish a submitted proposal"
    And the status of the proposal titled "Publish a submitted proposal" is "PUBLISHED"

  Scenario: Publish a submitted proposal but wrong status "PUBLISHED"
    Given there is an existing proposal with title "Publish a submitted proposal but wrong status"
    And there is an existing submission of the proposal titled "Publish a submitted proposal but wrong status"
    And the status of the proposal titled "Publish a submitted proposal but wrong status" is set to "PUBLISHED"
    When I publish the proposal with title "Publish a submitted proposal but wrong status"
    Then I get error 500 with message "Invalid proposal status 'PUBLISHED', should be 'SUBMITTED'"
    And the status of the proposal titled "Publish a submitted proposal but wrong status" is "PUBLISHED"

  Scenario: Publish a published proposal
    Given there is an existing proposal with title "Publish a published proposal"
    And there is an existing submission of the proposal titled "Publish a published proposal"
    And there is an existing publication of the proposal titled "Publish a published proposal"
    And the status of the proposal titled "Publish a published proposal" is "PUBLISHED"
    When I publish the proposal with title "Publish a published proposal"
    Then I get error 500 with message "Invalid proposal status 'PUBLISHED', should be 'SUBMITTED'"
    And the status of the proposal titled "Publish a published proposal" is "PUBLISHED"

  Scenario: Comment publish proposal
    Given there is an existing proposal with title "Comment publish proposal"
    And I login as "professor1" with password "password"
    And there is an existing submission of the proposal titled "Comment publish proposal"
    And there is not a publication of the submission of the proposal titled "Comment publish proposal"
    When I publish the proposal with title "Comment publish proposal"
    And I comment the proposal with title "Comment publish proposal" with a comment with text "This is a comment"
    Then I have created a comment that comments a proposal with text "This is a comment"
