package cat.udl.eps.entsoftarch.thesismarket.service;

import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalPublication;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalRegistration;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalSubmission;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProponentRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RepositoryEventHandler(ProposalRegistration.class)
public class ProposalRegistrationEventHandler {

    final Logger logger = LoggerFactory.getLogger(ProposalRegistrationEventHandler.class);

    @Autowired private ProposalRepository proposalRepository;
    @Autowired private ProponentRepository proponentRepository;

    @HandleBeforeCreate
    @Transactional
    public void handleProposalRegistrationPreCreate(ProposalRegistration proposalRegistration){
        logger.info("Before creating: {}", proposalRegistration.toString());

        ProposalPublication publication = proposalRegistration.getRegisters();
        ProposalSubmission submission = publication.getPublishes();
        Proposal proposal = submission.getSubmits();

        proposal.setStatus(Proposal.Status.REGISTERED);
        proposalRepository.save(proposal);
    }
}
