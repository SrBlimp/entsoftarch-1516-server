package cat.udl.eps.entsoftarch.thesismarket.service;

import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalPublication;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalSubmission;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalWithdrawal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by ferran on 7/3/16.
 */
@Component
@RepositoryEventHandler(ProposalPublication.class)
public class ProposalPublishEventHandler {
    final Logger logger = LoggerFactory.getLogger(ProposalWithdrawalEventHandler.class);

    @HandleBeforeCreate
    @Transactional
    public void handleProposalPublicationPreCreated(ProposalPublication proposalPublication) {
        logger.info("Before creating: {}", proposalPublication.toString());

        //Proposal proposal = proposalPublication.getPublishes().getSubmits();

        ProposalSubmission submission = proposalPublication.getPublishes();
        Assert.notNull(submission, "Trying to publish un-existing submission");

        Proposal proposal = submission.getSubmits();
        Assert.isTrue(proposal.getStatus().equals(Proposal.Status.SUBMITTED),
                "Invalid proposal status '"+proposal.getStatus()+"', should be '"+ Proposal.Status.SUBMITTED+"'");
    }
}
