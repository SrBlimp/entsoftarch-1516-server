package cat.udl.eps.entsoftarch.thesismarket.service;

import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalWithdrawal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Component
@RepositoryEventHandler(ProposalWithdrawal.class)
public class ProposalWithdrawalEventHandler {
    final Logger logger = LoggerFactory.getLogger(ProposalWithdrawalEventHandler.class);

    @HandleBeforeCreate
    @Transactional
    public void handleProposalWithdrawalPreCreate(ProposalWithdrawal proposalWithdrawal){
        logger.info("Before creating: {}", proposalWithdrawal.toString());
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
