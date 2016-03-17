package cat.udl.eps.entsoftarch.thesismarket.service;


import cat.udl.eps.entsoftarch.thesismarket.domain.Proponent;
import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalSubmission;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalWithdrawal;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProponentRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.transaction.Transactional;


@Component
@RepositoryEventHandler(ProposalSubmission.class)
public class ProposalSubmissionEventHandler {

    final Logger logger = LoggerFactory.getLogger(ProposalSubmissionEventHandler.class);

    @Autowired private ProposalRepository proposalRepository;
    @Autowired private ProponentRepository proponentRepository;

    @HandleBeforeCreate
    @Transactional
    public void handleProposalSubmissionPreCreate(ProposalSubmission proposalSubmission){

        logger.info("Before creating: {}", proposalSubmission.toString());

        Proposal submits = proposalSubmission.getSubmits();

        Assert.isTrue(submits.getStatus().equals(Proposal.Status.DRAFT),
                "Invalid proposal status '"+submits.getStatus()+"', should be '"+ Proposal.Status.DRAFT+"'");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Proponent proponent = proponentRepository.findOne(username);
        proposalSubmission.setAgent(proponent);
        Assert.isTrue(submits.getCreator().equals(proposalSubmission.getAgent()));

        submits.setStatus(Proposal.Status.SUBMITTED);
        proposalRepository.save(submits);
    }


}
