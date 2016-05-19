package cat.udl.eps.entsoftarch.thesismarket.service;

import cat.udl.eps.entsoftarch.thesismarket.domain.Proponent;
import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;

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
import javax.validation.constraints.AssertFalse;
import java.util.Arrays;
import java.util.List;


@Component
@RepositoryEventHandler(Proposal.class)
public class ProposalEventHandler {

    final Logger logger = LoggerFactory.getLogger(ProposalWithdrawalEventHandler.class);

    @Autowired private ProposalRepository proposalRepository;
    @Autowired private ProponentRepository proponentRepository;
    @Autowired private MailService mailService;

    @HandleBeforeCreate
    @Transactional
    @PreAuthorize("hasRole('PROPONENT')")

    public void handleProposalCreate(Proposal new_proposal){
        logger.info("Before creating: {}", new_proposal.toString());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Proponent proponent = proponentRepository.findOne(username);
        new_proposal.setCreator(proponent);

        Proposal proposal = proposalRepository.findByTitle(new_proposal.getTitle());
        Assert.isNull(proposal,"Trying to create a proposal already created.");

        proposalRepository.save(new_proposal);


        String subject = "Create Proposal";
        String message = "Dear proponent, \n\n" +
                "It has created a proposal with the title\"" +
                new_proposal.getTitle() + ".\" \n\n" +
                "Best regards, \n\n" +
                "Thesis Market";

        mailService.sendMessage(proponent.getEmail(), subject, message);
    }

}
