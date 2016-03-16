package cat.udl.eps.entsoftarch.thesismarket.service;

import cat.udl.eps.entsoftarch.thesismarket.domain.Comment;
import cat.udl.eps.entsoftarch.thesismarket.domain.Proponent;
import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalPublication;
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

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Component
@RepositoryEventHandler(Comment.class)
public class CommentEventHandler {
    final Logger logger = LoggerFactory.getLogger(CommentEventHandler.class);

    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private ProponentRepository proponentRepository;

    @HandleBeforeCreate
    @Transactional
    public void handleCommentPreCreate(Comment comment) {
        logger.info("Before creating: {}", comment.toString());

        ProposalPublication proposalPublication = comment.getComments();
        Assert.isNull(proposalPublication.getRegisteredBy(), "Register is not null");

        Proposal proposal = proposalPublication.getPublishes().getSubmits();
        Assert.isTrue(proposal.getStatus().equals(Proposal.Status.PUBLISHED),
                "Invalid proposal status '" + proposal.getStatus() + "', should be '" +
                        Proposal.Status.PUBLISHED + "'");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Proponent proponent = proponentRepository.findOne(username);
        comment.setAuthor(proponent);
        Assert.isTrue(proposal.getCreator().equals(comment.getAuthor()));

        proposal.setStatus(Proposal.Status.PUBLISHED);
        proposalRepository.save(proposal);
    }

    @HandleBeforeSave
    @Transactional
    public void handleCommentPreSave(Comment comment) {
        logger.info("Before updating: {}", comment.toString());
    }

    @HandleBeforeDelete
    @Transactional
    public void handleCommentPreDelete(Comment comment) {
        logger.info("Before deleting: {}", comment.toString());
    }

    @HandleBeforeLinkSave
    public void handleCommentPreLinkSave(Comment comment, Object o) {
        logger.info("Before linking: {} to {}", comment.toString(), o.toString());
    }

    @HandleAfterCreate
    @Transactional
    public void handleCommentPostCreate(Comment comment) {
        logger.info("After creating: {}", comment.toString());
    }

    @HandleAfterSave
    @Transactional
    public void handleCommentPostSave(Comment comment) {
        logger.info("After updating: {}", comment.toString());
    }

    @HandleAfterDelete
    @Transactional
    public void handleCommentPostDelete(Comment comment) {
        logger.info("After deleting: {}", comment.toString());
    }

    @HandleAfterLinkSave
    public void handleCommentPostLinkSave(Comment comment, Object o) {
        logger.info("After linking: {} to {}", comment.toString(), o.toString());
    }
}
