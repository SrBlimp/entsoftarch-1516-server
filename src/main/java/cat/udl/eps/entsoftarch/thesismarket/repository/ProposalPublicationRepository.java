package cat.udl.eps.entsoftarch.thesismarket.repository;

/**
 * Created by denis casajus on 25/02/2016.
 */

import cat.udl.eps.entsoftarch.thesismarket.domain.Coordinator;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalPublication;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalPublicationDetails;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalSubmission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RepositoryRestResource(excerptProjection = ProposalPublicationDetails.class)
public interface ProposalPublicationRepository extends PagingAndSortingRepository<ProposalPublication, Long> {

    List<ProposalPublication> findByPublishes(ProposalSubmission proposalSubmission);

    Page<ProposalPublication> findByAgent(Coordinator agent, Pageable pageable);

}

