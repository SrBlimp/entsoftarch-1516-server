Feature: Offer as student
  In order to a student is offered for a proposal
  As a student
  I want to offer as student to this proposal

  Scenario: Student is offered for an existing proposal created by a teacher
    Given there is an existing proposal with title "Really interesting proposal"
    And there is an existing submission of the proposal titled "Really interesting proposal"
    And there is an existing publication of the proposal titled "Really interesting proposal"
    When I offer as student to a publication proposal with title "Really interesting proposal"
    Then I have created an offer student of the publication proposal of the submission of the proposal titled "Really interesting proposal"


#  Scenario: Student is offered for an existing proposal created by a teacher
#    Given there is an existing proposal with title "Really interesting proposal"
#    And there is an existing submission of the proposal titled "Really interesting proposal"
#    And there is an existing publication of the proposal titled "Really interesting proposal"
#    When I offer as student to a publication proposal with title "Really interesting project"
#    And Student "other student" has offered for proposal with title "Really interesting proposal"
#    Then I have created an other offer student of the publication proposal of the submission of the proposal titled "Really interesting project"


  Scenario: Student is offered for an unpublished proposal created by a teacher
    Given there is an existing proposal with title "Really interesting proposal"
      And there is an existing submission of the proposal titled "Really interesting proposal"
      And there is not a publication of the submission of the proposal titled "Really interesting proposal"
      And the status of the proposal titled "Really interesting proposal" is "SUBMITTED"
    When I offer as student to a un-existing publication proposal
    Then I get error 400 with message "may not be null"
      And the status of the proposal titled "Really interesting proposal" is "SUBMITTED"

#  Scenario: Student is offered for an assigned proposal created by a teacher
#    Given there is an existing proposal with title "Really interesting proposal"
#      And there is an existing submission of the proposal titled "Really interesting proposal"
#      And there is an existing publication of the proposal titled "Really interesting proposal"
#      And the status of the proposal titled "Really interesting proposal" is set to "ASSIGNED"
#    #And proposal publication with title "<string>" has been deleted
#    When I offer as student to a publication proposal with title "Really interesting proposal"
#    Then I get error 500 with message "Invalid proposal status 'ASSIGNED', should be 'PUBLISHED'"
#      And the status of the proposal titled "Really interesting proposal" is "ASSIGNED"


  #removed?

#   Scenario: Student is offered for an existing proposal created by teacher, without enought credits