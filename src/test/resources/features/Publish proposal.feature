Feature: Publish proposal
  In order to publish a proposal
  As a coordinator
  I want review and make public the proposal

  Scenario: Publish proposal
    Given there is an existing proposal with title "Publish existing proposal submission"
    And there is an existing submission of the proposal titled "Publish existing proposal submission"
    When I publish the proposal with title "Publish existing proposal submission"

