package cat.udl.eps.entsoftarch.thesismarket.repository;

import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalSubmission;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource
public interface ProposalSubmissionRepository extends PagingAndSortingRepository<ProposalSubmission, Long> {

    List<ProposalSubmission> findBySubmits(Proposal proposal);
}