package cat.udl.eps.entsoftarch.thesismarket.service;

import cat.udl.eps.entsoftarch.thesismarket.domain.Coordinator;
import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalPublication;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalSubmission;
import cat.udl.eps.entsoftarch.thesismarket.repository.CoordinatorRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by ferran on 7/3/16.
 */
@Component
@RepositoryEventHandler(ProposalPublication.class)
public class ProposalPublishEventHandler {
    final Logger logger = LoggerFactory.getLogger(ProposalPublishEventHandler.class);

    @Autowired private ProposalRepository proposalRepository;
    @Autowired private CoordinatorRepository coordinatorRepository;
    @Autowired private MailService mailService;

    @HandleBeforeCreate
    @Transactional
    @PreAuthorize("hasRole('COORDINATOR')")
    public void handleProposalPublicationPreCreated(ProposalPublication proposalPublication) {
        logger.info("Before creating: {}", proposalPublication.toString());

        ProposalSubmission submission = proposalPublication.getPublishes();
        Assert.notNull(submission, "Trying to publish un-existing submission");

        Proposal proposal = submission.getSubmits();
        Assert.isTrue(proposal.getStatus().equals(Proposal.Status.SUBMITTED),
                "Invalid proposal status '"+proposal.getStatus()+"', should be '"+ Proposal.Status.SUBMITTED+"'");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Coordinator coordinator = coordinatorRepository.findOne(username);
        proposalPublication.setAgent(coordinator);

        proposal.setStatus(Proposal.Status.PUBLISHED);
        proposalRepository.save(proposal);

        String subject = "Proposal Published";
        String message = "Dear Proponent, \n\n" +
                "Your proposal with title \"" +
                proposal.getTitle() + "has been published. \n\n" +
                "Best regards, \n\n" +
                username;

        mailService.sendMessage(proposal.getCreator().getEmail(), subject, message);
    }
}
