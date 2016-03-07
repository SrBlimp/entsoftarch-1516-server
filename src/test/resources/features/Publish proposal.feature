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
