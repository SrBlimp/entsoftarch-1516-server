package cat.udl.eps.entsoftarch.thesismarket.repository;

import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalWithdrawal;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface ProposalWithdrawalRepository extends PagingAndSortingRepository<ProposalWithdrawal, Long> {

}

