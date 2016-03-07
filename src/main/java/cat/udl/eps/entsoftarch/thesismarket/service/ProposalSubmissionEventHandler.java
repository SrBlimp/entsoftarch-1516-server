package cat.udl.eps.entsoftarch.thesismarket.service;


import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalSubmission;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

public class ProposalSubmissionEventHandler {

    final Logger logger = LoggerFactory.getLogger(ProposalSubmissionEventHandler.class);

    @Autowired
    private ProposalRepository proposalRepository;

    @HandleBeforeCreate
    @Transactional
    public void handleProposalSubmissionPreCreate(ProposalSubmission proposalSubmission){
        logger.info("Before creating: {}", proposalSubmission.toString());

       Proposal submits = proposalSubmission.getSubmits();
        Assert.notNull(submits, "Trying to withdraw un-existing submission");

        Assert.isTrue(submits.getStatus().equals(Proposal.Status.DRAFT),
                "Invalid proposal status '"+submits.getStatus()+"', should be '"+ Proposal.Status.SUBMITTED+"'");


        submits.setStatus(Proposal.Status.SUBMITTED);
        proposalRepository.save(submits);
    }


}
