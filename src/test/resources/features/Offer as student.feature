Feature: Offer as student
  In order to a student is offered for a proposal
  As a student
  I want to offer as student to this proposal

  Scenario: Offer as student without credentials
    Given I'm not logged in
      And there is an existing proposal with title "Really interesting proposal"
      And there is an existing submission of the proposal titled "Really interesting proposal"
      And there is an existing publication of the proposal titled "Really interesting proposal"
      And the status of the proposal titled "Really interesting proposal" is "PUBLISHED"
    When I offer as student to a publication proposal with title "Really interesting proposal"
    Then I get error 401 with message "Full authentication is required to access this resource"
      And the status of the proposal titled "Really interesting proposal" is "PUBLISHED"

  Scenario: Student is offered for an existing proposal created by a teacher
    Given I login as "student1" with password "password"
      And there is an existing proposal with title "Really interesting proposal"
      And there is an existing submission of the proposal titled "Really interesting proposal"
      And there is an existing publication of the proposal titled "Really interesting proposal"
    When I offer as student to a publication proposal with title "Really interesting proposal"
    Then I have created an offer student of the publication proposal of the submission of the proposal titled "Really interesting proposal"

  Scenario: Student is offered for an existing proposal created by a teacher with a previous offer student
    Given I login as "student1" with password "password"
      And there is an existing proposal with title "Really interesting proposal"
      And there is an existing submission of the proposal titled "Really interesting proposal"
      And there is an existing publication of the proposal titled "Really interesting proposal"
    When I offer as student to a publication proposal with title "Really interesting proposal"
    When I offer as student with name "studenta" to a publication proposal with title "Really interesting proposal"
      And I offer as student with name "studentb" to a publication proposal with title "Really interesting proposal"
    Then I have two offer student more created of the publication proposal of the submission of the proposal titled "Really interesting proposal"

  Scenario: Student is offered for an unpublished proposal created by a teacher
    Given I login as "student1" with password "password"
      And there is an existing proposal with title "Really interesting proposal"
      And there is an existing submission of the proposal titled "Really interesting proposal"
      And there is not a publication of the submission of the proposal titled "Really interesting proposal"
      And the status of the proposal titled "Really interesting proposal" is "SUBMITTED"
    When I offer as student to a un-existing publication proposal
    Then I get error 400 with message "may not be null"
      And the status of the proposal titled "Really interesting proposal" is "SUBMITTED"

  Scenario: Student is offered for an assigned proposal created by a teacher
    Given I login as "student1" with password "password"
      And there is an existing proposal with title "Really interesting proposal"
      And there is an existing submission of the proposal titled "Really interesting proposal"
      And there is an existing publication of the proposal titled "Really interesting proposal"
      And the status of the proposal titled "Really interesting proposal" is set to "ASSIGNED"
    When I offer as student to a publication proposal with title "Really interesting proposal"
    Then I get error 500 with message "Invalid proposal status 'ASSIGNED', should be 'PUBLISHED'"
      And the status of the proposal titled "Really interesting proposal" is "ASSIGNED"

  Scenario: Student is offered two times for a same existing proposal created by teacher
    Given I login as "student1" with password "password"
      And there is an existing proposal with title "Really interesting proposal"
      And there is an existing submission of the proposal titled "Really interesting proposal"
      And there is an existing publication of the proposal titled "Really interesting proposal"
    When I offer as student with name "student1" to a publication proposal with title "Really interesting proposal"
      And I offer as student with name "student1" to a publication proposal with title "Really interesting proposal"
    Then I get error 500 with message "Repeated StudentOffer"

  Scenario: Offer as student for a proposal using wrong credentials
    Given I login as "student1" with password "wrong password"
      And there is an existing proposal with title "Really interesting proposal"
      And there is an existing submission of the proposal titled "Really interesting proposal"
      And there is an existing publication of the proposal titled "Really interesting proposal"
      And the status of the proposal titled "Really interesting proposal" is "PUBLISHED"
    When I offer as student to a publication proposal with title "Really interesting proposal"
    Then I get error 401 with message "Bad credentials"
      And the status of the proposal titled "Really interesting proposal" is "PUBLISHED"

  Scenario: Offer as student for a proposal by a different user
    Given I login as "professor1" with password "password"
      And there is an existing proposal with title "Really interesting proposal"
      And there is an existing submission of the proposal titled "Really interesting proposal"
      And there is an existing publication of the proposal titled "Really interesting proposal"
      And the status of the proposal titled "Really interesting proposal" is "PUBLISHED"
    When I offer as student to a publication proposal with title "Really interesting proposal"
    Then I get error 403 with message "Access is denied"
      And the status of the proposal titled "Really interesting proposal" is "PUBLISHED"
    