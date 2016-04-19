package cat.udl.eps.entsoftarch.thesismarket.service;

import cat.udl.eps.entsoftarch.thesismarket.domain.*;
import cat.udl.eps.entsoftarch.thesismarket.repository.CoordinatorRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProponentRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Component
@RepositoryEventHandler(ProposalWithdrawal.class)
public class ProposalWithdrawalEventHandler {
    final Logger logger = LoggerFactory.getLogger(ProposalWithdrawalEventHandler.class);

    @Autowired private ProposalRepository proposalRepository;
    @Autowired private ProponentRepository proponentRepository;
    @Autowired private CoordinatorRepository coordinatorRepository;
    @Autowired private MailService mailService;

    @HandleBeforeCreate
    @Transactional
    @PreAuthorize("#proposalWithdrawal.withdraws.submits.creator.username == authentication.name")
    public void handleProposalWithdrawalPreCreate(ProposalWithdrawal proposalWithdrawal) {
        logger.info("Before creating: {}", proposalWithdrawal.toString());

        ProposalSubmission submission = proposalWithdrawal.getWithdraws();

        Proposal proposal = submission.getSubmits();
        Assert.isTrue(proposal.getStatus().equals(Proposal.Status.SUBMITTED),
                "Invalid proposal status '"+proposal.getStatus()+"', should be '"+ Proposal.Status.SUBMITTED+"'");
        Assert.isNull(submission.getPublishedBy(),
                "To withdraw a proposal submission it should be unpublished");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Proponent proponent = proponentRepository.findOne(username);
        proposalWithdrawal.setAgent(proponent);

        proposal.setStatus(Proposal.Status.DRAFT);
        proposalRepository.save(proposal);

        String subject = "Proposal Withdrawal";
        String message = "Dear coordinador, \n\n" +
                "Please, be aware that the unpublished submission of the proposal \"" +
                proposal.getTitle() + "\" by " + proponent.getUsername() + " has been withdrawn. \n\n" +
                "Best regards, \n\n" +
                "Thesis Market";

        coordinatorRepository.findAll().forEach(
                coordinator -> mailService.sendMessage(coordinator.getEmail(), subject, message));

        //TODO: Warn also professors with role coordinator!
    }

    @HandleBeforeSave
    @Transactional
    public void handleProposalWithdrawalPreSave(ProposalWithdrawal proposalWithdrawal){
        logger.info("Before updating: {}", proposalWithdrawal.toString());
    }

    @HandleBeforeDelete
    @Transactional
    public void handleProposalWithdrawalPreDelete(ProposalWithdrawal proposalWithdrawal){
        logger.info("Before deleting: {}", proposalWithdrawal.toString());
    }

    @HandleBeforeLinkSave
    public void handleProposalWithdrawalPreLinkSave(ProposalWithdrawal proposalWithdrawal, Object o) {
        logger.info("Before linking: {} to {}", proposalWithdrawal.toString(), o.toString());
    }

    @HandleAfterCreate
    @Transactional
    public void handleProposalWithdrawalPostCreate(ProposalWithdrawal proposalWithdrawal){
        logger.info("After creating: {}", proposalWithdrawal.toString());
    }

    @HandleAfterSave
    @Transactional
    public void handleProposalWithdrawalPostSave(ProposalWithdrawal proposalWithdrawal){
        logger.info("After updating: {}", proposalWithdrawal.toString());
    }

    @HandleAfterDelete
    @Transactional
    public void handleProposalWithdrawalPostDelete(ProposalWithdrawal proposalWithdrawal){
        logger.info("After deleting: {}", proposalWithdrawal.toString());
    }

    @HandleAfterLinkSave
    public void handleProposalWithdrawalPostLinkSave(ProposalWithdrawal proposalWithdrawal, Object o) {
        logger.info("After linking: {} to {}", proposalWithdrawal.toString(), o.toString());
    }
}
