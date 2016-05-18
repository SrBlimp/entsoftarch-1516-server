Feature: Publish proposal
  In order to publish a proposal
  As a coordinator
  I want review and make public the proposal

  Scenario: Publish an un-existing proposal
    Given I login as "coordinator1" with password "password"
    And there is not an existing proposal titled "Publish un-existing proposal"
    When I publish an un-existing proposal
    Then I get error 400 with message "may not be null"
    And no message has been sent

  Scenario: Publish an un-submitted proposal
    Given I login as "coordinator1" with password "password"
    And there is an existing proposal with title "Publish a draft proposal"
    And there is not a submission of the proposal titled "Publish a draft proposal"
    And the status of the proposal titled "Publish a draft proposal" is "DRAFT"
    When I publish an un-existing submission
    Then I get error 400 with message "may not be null"
    And the status of the proposal titled "Publish a draft proposal" is "DRAFT"
    And no message has been sent

  Scenario: Publish a submitted proposal but wrong status "DRAFT"
    Given I login as "coordinator1" with password "password"
    And there is an existing proposal with title "Publish a submitted proposal but wrong status"
    And there is an existing submission of the proposal titled "Publish a submitted proposal but wrong status"
    And the status of the proposal titled "Publish a submitted proposal but wrong status" is set to "DRAFT"
    When I publish the proposal with title "Publish a submitted proposal but wrong status"
    Then I get error 500 with message "Invalid proposal status 'DRAFT', should be 'SUBMITTED'"
    And the status of the proposal titled "Publish a submitted proposal but wrong status" is "DRAFT"
    And no message has been sent

  Scenario: Publish a submitted proposal
    Given I login as "coordinator1" with password "password"
    And there is an existing proposal with title "Publish a submitted proposal" by "professor1"
    And there is an existing submission of the proposal titled "Publish a submitted proposal"
    And the status of the proposal titled "Publish a submitted proposal" is "SUBMITTED"
    And there is not a publication of the submission of the proposal titled "Publish a submitted proposal"
    When I publish the proposal with title "Publish a submitted proposal"
    Then I have a proposal publication with title "Publish a submitted proposal"
    And the status of the proposal titled "Publish a submitted proposal" is "PUBLISHED"
    And an email has been sent to "professor1@thesismarket" with subject "Proposal Published" and containing "Your proposal with title"

  Scenario: Publish a submitted proposal but wrong status "PUBLISHED"
    Given I login as "coordinator1" with password "password"
    And there is an existing proposal with title "Publish a submitted proposal but wrong status"
    And there is an existing submission of the proposal titled "Publish a submitted proposal but wrong status"
    And the status of the proposal titled "Publish a submitted proposal but wrong status" is set to "PUBLISHED"
    When I publish the proposal with title "Publish a submitted proposal but wrong status"
    Then I get error 500 with message "Invalid proposal status 'PUBLISHED', should be 'SUBMITTED'"
    And the status of the proposal titled "Publish a submitted proposal but wrong status" is "PUBLISHED"
    And no message has been sent

  Scenario: Publish a published proposal
    Given I login as "coordinator1" with password "password"
    And there is an existing proposal with title "Publish a published proposal"
    And there is an existing submission of the proposal titled "Publish a published proposal"
    And there is an existing publication of the proposal titled "Publish a published proposal"
    And the status of the proposal titled "Publish a published proposal" is "PUBLISHED"
    When I publish the proposal with title "Publish a published proposal"
    Then I get error 500 with message "Invalid proposal status 'PUBLISHED', should be 'SUBMITTED'"
    And the status of the proposal titled "Publish a published proposal" is "PUBLISHED"
    And no message has been sent

  Scenario: Coordinator comment published proposal
    Given I login as "coordinator1" with password "password"
    And there is an existing proposal with title "Comment publish proposal" by "student1"
    And there is an existing submission of the proposal titled "Comment publish proposal"
    And there is not a publication of the submission of the proposal titled "Comment publish proposal"
    When I publish the proposal with title "Comment publish proposal"
    And an email has been sent to "student1@thesismarket" with subject "Proposal Published" and containing "Your proposal with title"
    And I comment the proposal with title "Comment publish proposal" with a comment with text "This is a comment"
    Then I have created a comment that comments a proposal with text "This is a comment"

  Scenario: Professor comment published proposal
    Given I login as "professor1" with password "password"
    And there is an existing proposal with title "Comment publish proposal" by "professor1"
    And there is an existing submission of the proposal titled "Comment publish proposal"
    And there is an existing publication of the proposal titled "Comment publish proposal"
    When I comment the proposal with title "Comment publish proposal" with a comment with text "This is a comment"
    Then I have created a comment that comments a proposal with text "This is a comment"

  Scenario: Student comment published proposal
    Given I login as "student1" with password "password"
    And there is an existing proposal with title "Comment publish proposal" by "student1"
    And there is an existing submission of the proposal titled "Comment publish proposal"
    And there is an existing publication of the proposal titled "Comment publish proposal"
    When I comment the proposal with title "Comment publish proposal" with a comment with text "This is a comment"
    Then I have created a comment that comments a proposal with text "This is a comment"

  Scenario: Professor1 publish proposal
    Given I login as "professor1" with password "password"
    And there is an existing proposal with title "Professor1 publish proposal" by "professor1"
    And there is an existing submission of the proposal titled "Professor1 publish proposal"
    And there is not a publication of the submission of the proposal titled "Professor1 publish proposal"
    When I publish the proposal with title "Professor1 publish proposal"
    Then there is not a publication of the submission of the proposal titled "Professor1 publish proposal"
    And no message has been sent

  Scenario: Student publish proposal
    Given I login as "student1" with password "password"
    And there is an existing proposal with title "Student1 publish proposal"
    And there is an existing submission of the proposal titled "Student1 publish proposal"
    And there is not a publication of the submission of the proposal titled "Student1 publish proposal"
    When I publish the proposal with title "Student1 publish proposal"
    Then there is not a publication of the submission of the proposal titled "Student1 publish proposal"
    And no message has been sent