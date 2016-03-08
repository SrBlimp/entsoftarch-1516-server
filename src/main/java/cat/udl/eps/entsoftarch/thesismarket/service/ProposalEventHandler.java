package cat.udl.eps.entsoftarch.thesismarket.service;

import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;

import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import javax.validation.constraints.AssertFalse;


@Component
@RepositoryEventHandler(Proposal.class)
public class ProposalEventHandler {

    final Logger logger = LoggerFactory.getLogger(ProposalWithdrawalEventHandler.class);

    @Autowired
    private ProposalRepository proposalRepository;

    @HandleBeforeCreate
    @Transactional
    public void handleProposalWithdrawalCreate(Proposal new_proposal){
        logger.info("Before creating: {}", new_proposal.toString());

        Proposal proposal = proposalRepository.findByTitle(new_proposal.getTitle());
        Assert.isNull(proposal,"Trying to create a proposal already created.");

        proposalRepository.save(new_proposal);

    }

}
