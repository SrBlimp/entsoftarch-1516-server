package cat.udl.eps.entsoftarch.thesismarket.repository;


import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalSubmission;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProposalSubmissionRepository extends PagingAndSortingRepository<ProposalSubmission, Long> {
}
