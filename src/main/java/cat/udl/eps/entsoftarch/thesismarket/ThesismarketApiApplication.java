package cat.udl.eps.entsoftarch.thesismarket;

import cat.udl.eps.entsoftarch.thesismarket.domain.*;
import cat.udl.eps.entsoftarch.thesismarket.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityDataConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
@Import(SecurityDataConfiguration.class)
public class ThesismarketApiApplication {

	@Autowired Environment env;
	@Autowired ProposalRepository proposalRepository;
	@Autowired ProposalSubmissionRepository proposalSubmissionRepository;
	@Autowired ProposalPublicationRepository proposalPublicationRepository;
	@Autowired CommentRepository commentRepository;
	@Autowired StudentOfferRepository studentOfferRepository;
	@Autowired StudentAssignmentRepository studentsAssignmentRepository;
	@Autowired ProposalRegistrationRepository proposalRegistrationRepository;
	@Autowired CoordinatorRepository coordinatorRepository;
	@Autowired ProfessorRepository professorRepository;
	@Autowired StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(ThesismarketApiApplication.class, args);
	}

	@PostConstruct
	public void init(){
		Coordinator coordinator = new Coordinator();
		coordinator.setUsername("coordinator1");
		coordinator.setEmail("coordinator1@thesismarket");
		coordinator = coordinatorRepository.save(coordinator);
		Professor professor = new Professor();
		professor.setUsername("professor1");
		professor.setEmail("professor1@thesismarket");
		professor = professorRepository.save(professor);
		Student student = new Student();
		student.setUsername("student1");
		student.setEmail("student1@thesismarket");
		student = studentRepository.save(student);

		if (env.acceptsProfiles("sample-data")) {

			coordinator.setEmail("thesismarket.udl@gmail.com");
			coordinator = coordinatorRepository.save(coordinator);
			professor.setEmail("thesismarket.udl@gmail.com");
			professor = professorRepository.save(professor);
			student.setEmail("thesismarket.udl@gmail.com");
			student = studentRepository.save(student);

			Proposal professorProposal = new Proposal();
			professorProposal.setTitle("Proposal by Professor");
			professorProposal.setCreator(professor);
			professorProposal.setStatus(Proposal.Status.REGISTERED);
			professorProposal.setDegree("Computer Science BSc");
			professorProposal.setTopics(new HashSet<>(Arrays.asList("NoSQL", "Databases")));
			professorProposal = proposalRepository.save(professorProposal);

			Proposal studentProposal = new Proposal();
			studentProposal.setTitle("Proposal by Student");
			studentProposal.setCreator(student);
			studentProposal.setStatus(Proposal.Status.DRAFT);
			professorProposal.setDegree("Computer Science BSc");
			professorProposal.setTopics(new HashSet<>(Collections.singletonList("Web Engineering")));
			studentProposal = proposalRepository.save(studentProposal);

			ProposalSubmission proposalSubmission = new ProposalSubmission();
			proposalSubmission.setAgent(professor);
			proposalSubmission.setSubmits(professorProposal);
			proposalSubmission.setDateTime(ZonedDateTime.now());
			proposalSubmission = proposalSubmissionRepository.save(proposalSubmission);

			ProposalPublication proposalPublication = new ProposalPublication();
			proposalPublication.setAgent(coordinator);
			proposalPublication.setPublishes(proposalSubmission);
			proposalPublication.setDateTime(ZonedDateTime.now());
			proposalPublication = proposalPublicationRepository.save(proposalPublication);

			Comment comment = new Comment();
			comment.setAuthor(student);
			comment.setText("Sounds interesting...");
			comment.setComments(proposalPublication);
			comment = commentRepository.save(comment);

			StudentOffer studentOffer = new StudentOffer();
			studentOffer.setAgent(student);
			studentOffer.setTarget(proposalPublication);
			studentOffer.setDateTime(ZonedDateTime.now());
			studentOffer = studentOfferRepository.save(studentOffer);

			StudentsAssignment studentsAssignment = new StudentsAssignment();
			studentsAssignment.setAssigns(studentOffer);
			studentsAssignment.setDateTime(ZonedDateTime.now());
			studentsAssignment = studentsAssignmentRepository.save(studentsAssignment);

			ProposalRegistration proposalRegistration = new ProposalRegistration();
			proposalRegistration.setAgent(coordinator);
			proposalRegistration.setRegisters(proposalPublication);
			proposalRegistration.setDateTime(ZonedDateTime.now());
			proposalRegistration = proposalRegistrationRepository.save(proposalRegistration);
		}
	}
}
