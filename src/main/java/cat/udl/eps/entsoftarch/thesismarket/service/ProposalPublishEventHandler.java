package cat.udl.eps.entsoftarch.thesismarket.service;

import cat.udl.eps.entsoftarch.thesismarket.domain.*;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProponentRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
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
    @Autowired private ProponentRepository proponentRepository;

    @HandleBeforeCreate
    @Transactional
    public void handleProposalPublicationPreCreated(ProposalPublication proposalPublication) {
        logger.info("Before creating: {}", proposalPublication.toString());

        ProposalSubmission submission = proposalPublication.getPublishes();
        Assert.notNull(submission, "Trying to publish un-existing submission");

        Proposal proposal = submission.getSubmits();
        Assert.isTrue(proposal.getStatus().equals(Proposal.Status.SUBMITTED),
                "Invalid proposal status '"+proposal.getStatus()+"', should be '"+ Proposal.Status.SUBMITTED+"'");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Proponent proponent = proponentRepository.findOne(username);
        proposalPublication.setAgent(proponent);

        proposal.setStatus(Proposal.Status.PUBLISHED);
        proposalRepository.save(proposal);
    }
}
