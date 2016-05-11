package cat.udl.eps.entsoftarch.thesismarket.repository;

/**
 * Created by http://rhizomik.net/~roberto/
 */

import cat.udl.eps.entsoftarch.thesismarket.domain.Proponent;
import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ProposalRepository extends PagingAndSortingRepository<Proposal, Long> {

    List<Proposal> findByTitleContaining(String title);

    Proposal findByTitle(String title);

    Page<Proposal> findByCreator(Proponent creator, Pageable pageable);
}
