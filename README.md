Thesis Marketplace
==================

Class project for the Enterprise Software Architecture 2015-16 Course

## Vision

**For** students, professors and organizations,
**who** are interested in being involved in a thesis project,
**the** project ThesisMarket
**is a** bachelor and master thesis proposals marketplace
**that** allows them to propose thesis project, and also to get involved in proposals made by others, they are interested in.
**Unlike** current situation where proposals are periodically published in institutional web pages or proposed informally,
**our project** facilitates making proposals and contacting other parties interested in them.

## Goals, Capabilities, Features

### Goal
Facilitate that **professors** propose thesis projects and find interested **students**, and other *professors*

- **Stakeholder**: Professor
	- **Capability**: Propose project
		- *Feature*: Create proposal
		- *Feature*: Submit proposal
	- **Capability**: Discuss proposal
		- *Feature*: Comment proposal
	- **Capability**: Assign proposal team
		- *Feature*: Assign student
		- *Feature*: Assign co-director
	- **Capability**: Cancel proposal
		- *Feature*: Withdraw proposal
		- *Feature*: Remove proposal
	- **Capability**: Express interest in proposal
		- *Feature*: Offer as co-director

- **Stakeholder**: Student
	- **Capability**: Discuss proposal
		- *Feature*: Comment proposal
	- **Capability**: Express interest in proposal
		- *Feature*: Offer as Student

### Goal
Facilitate that **students** propose thesis projects and find interested **directors**, and other *students*

- **Stakeholder**: Student
	- **Capability**: Propose Project
		- *Feature*: Create proposal
		- *Feature*: Propose project
	- **Capability**: Discuss proposal
		- *Feature*: Comment proposal
	- **Capability**: Assign proposal team
		- *Feature*: Assign director
		- *Feature*: Assign co-student
	- **Capability**: Cancel proposal
		- *Feature*: Withdraw proposal
		- *Feature*: Remove proposal
	- **Capability**: Express interest in proposal
		- *Feature*: Offer as student

- **Stakeholder**: Professor
	- **Capability**: Discuss proposal
		- *Feature*: Comment proposal
	- **Capability**: Express interest in proposal
		- *Feature*: Offer as director

### Goal 
Facilitate that **organizations** do thesis projects with **students** and **professors**

- **Stakeholder**: Organization
	- **Capability**: **Propose project
		- *Feature*: Create proposal
		- *Feature*: Submit proposal
	- **Capability**: Assign proposal team
		- *Feature*: Assign director
		- *Feature*: Assign student
	- **Capability**: Discuss proposal
		- *Feature*: Comment proposal
	- **Capability**: Cancel proposal
		- *Feature*: Withdraw proposal
		- *Feature*: Remove proposal

- **Stakeholder**: Student
	- **Capability**: Discuss proposal
		- *Feature*: Comment proposal
	- **Capability**: Express interest in proposal
		- *Feature*: Offer as Student
	- **Capability**: Share experience with organization
		- *Feature*: Rate organization

- **Stakeholder**: Professor
	- **Capability**: Discuss proposal
		- *Feature*: Comment proposal
	- **Capability**: Express interest in proposal
		- *Feature*: Offer as director

### Goal 
Assist the **coordinator** to supervise proposed and accepted proposals

- **Stakeholder**: Coordinator
	- **Capability**: Discuss proposal
		- *Feature*: Comment proposal
	- **Capability**: Process proposal
		- *Feature*: Pubish proposal
		- *Feature*: Reject proposal
	- **Capability**: Accept thesis project proposal
		- *Feature*: Register thesis project
		- *Feature*: Reject thesis project

### Goal
Facilitate **dissemination** of thesis project **defenses**

- **Stakeholder**: Professor
	- **Capability**: Communicate defense date
		- *Feature*: Deposit Project

## Thesis Proposals Stages

![States and Transitions](http://g.gravizo.com/g?
digraph G {
   "" [shape=plaintext];
   "" -> "Draft" [label="create"];
   "Draft" -> "Proposed" [label="submit"];
   "Proposed" -> "Draft" [label="withdraw"];
   "Proposed" -> "Published" [label="publish %28coordinator%29"];
   "Proposed" -> "Draft" [label="reject %28coordinator%29"];
   "Published" -> "Published"  [label="comment"];
   "Published" -> "Published"  [label="offer as..."];
   "Published" -> "Published"  [label="assign"];
   "Published" -> "Assigned"  [label="assign %28all assigned%29"];
   "Published" -> "Draft" [label="withdraw"];
   "Assigned" -> "Registered" [label="register %28coordinator%29"];
   "Assigned" -> "Draft" [label="reject %28coordinator%29"];
   "Registered" -> "Draft" [label="withdraw"];
   "Registered" -> "Deposited" [label="deposit %28professor%29"];
   "Deposited" -> "Deposited" [label="rate organization"];
   "Draft" -> "" [label="remove"];
 }
)
