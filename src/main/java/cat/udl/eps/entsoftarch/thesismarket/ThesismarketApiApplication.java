package cat.udl.eps.entsoftarch.thesismarket;

import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProfessorRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ThesismarketApiApplication {
	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
		return new SecurityEvaluationContextExtension();
	}

	@Autowired Environment env;
	@Autowired ProposalRepository proposalRepository;
	@Autowired ProfessorRepository professorRepository;
	@Autowired StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(ThesismarketApiApplication.class, args);
	}

	@PostConstruct
	public void init(){
		if (env.acceptsProfiles("sample-data")) {
			Proposal professorProposal = new Proposal();
			professorProposal.setTitle("Proposal by Professor");
			professorProposal.setCreator(professorRepository.findOne("professor"));
			professorProposal.setStatus(Proposal.Status.DRAFT);
			proposalRepository.save(professorProposal);

			Proposal studentProposal = new Proposal();
			studentProposal.setTitle("Proposal by Student");
			studentProposal.setCreator(studentRepository.findOne("student"));
			studentProposal.setStatus(Proposal.Status.DRAFT);
			proposalRepository.save(studentProposal);
		}
	}
}
