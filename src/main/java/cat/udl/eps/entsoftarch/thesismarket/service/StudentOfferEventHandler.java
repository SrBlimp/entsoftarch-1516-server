package cat.udl.eps.entsoftarch.thesismarket.service;

import cat.udl.eps.entsoftarch.thesismarket.domain.*;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProponentRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.StudentOfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * Created by victor on 4/04/16.
 */
@Component
@RepositoryEventHandler(StudentOffer.class)
public class StudentOfferEventHandler {

    final Logger logger = LoggerFactory.getLogger(ProposalWithdrawalEventHandler.class);

    @Autowired
    private StudentOfferRepository studentOfferRepository;
//    @Autowired private ProponentRepository proponentRepository;

    @HandleBeforeCreate
    @Transactional
//    @PreAuthorize("#proposalWithdrawal.withdraws.submits.creator.username == authentication.name")

    public void handleProposalStudentOfferPreCreate(StudentOffer studentOffer) {
        logger.info("Before creating: {}", studentOffer.toString());

        ProposalPublication publication = studentOffer.getTarget();

        ProposalSubmission submission = publication.getPublishes();

        Proposal proposal = submission.getSubmits();
        Assert.isTrue(proposal.getStatus().equals(Proposal.Status.PUBLISHED),
                "Invalid proposal status '"+proposal.getStatus()+"', should be '"+ Proposal.Status.PUBLISHED+"'");
        /*Assert.isNull(submission.getPublishedBy(),
                "To withdraw a proposal submission it should be unpublished");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Proponent proponent = proponentRepository.findOne(username);
        proposalWithdrawal.setAgent(proponent);

        proposal.setStatus(Proposal.Status.DRAFT);
        proposalRepository.save(proposal);
        */
    }

}
