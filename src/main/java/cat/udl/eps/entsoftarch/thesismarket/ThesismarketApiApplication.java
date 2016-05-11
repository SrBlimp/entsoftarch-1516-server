package cat.udl.eps.entsoftarch.thesismarket;

import cat.udl.eps.entsoftarch.thesismarket.domain.*;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProfessorRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.StudentRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityDataConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Import(SecurityDataConfiguration.class)
public class ThesismarketApiApplication {

	@Autowired Environment env;
	@Autowired ProposalRepository proposalRepository;
	@Autowired UserRepository userRepository;
	@Autowired ProfessorRepository professorRepository;
	@Autowired StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(ThesismarketApiApplication.class, args);
	}

	@PostConstruct
	public void init(){
		User coordinator = new Coordinator();
		coordinator.setUsername("coordinator1");
		coordinator.setEmail("coordinator1@thesismarket");
		userRepository.save(coordinator);
		User professor = new Professor();
		professor.setUsername("professor1");
		professor.setEmail("professor1@thesismarket");
		userRepository.save(professor);
		User student = new Student();
		student.setUsername("student1");
		student.setEmail("student1@thesismarket");
		userRepository.save(student);

		if (env.acceptsProfiles("sample-data")) {
			Proposal professorProposal = new Proposal();
			professorProposal.setTitle("Proposal by Professor");
			professorProposal.setCreator(professorRepository.findOne("professor1"));
			professorProposal.setStatus(Proposal.Status.DRAFT);
			proposalRepository.save(professorProposal);

			Proposal studentProposal = new Proposal();
			studentProposal.setTitle("Proposal by Student");
			studentProposal.setCreator(studentRepository.findOne("student1"));
			studentProposal.setStatus(Proposal.Status.DRAFT);
			proposalRepository.save(studentProposal);
		}
	}
}
