package cat.udl.eps.entsoftarch.thesismarket.repository;

import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by xavier on 25/02/16.
 */
public interface EditProposalRepository extends PagingAndSortingRepository<Proposal, Long> {
}
