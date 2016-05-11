package cat.udl.eps.entsoftarch.thesismarket.repository;

/**
 * Created by http://rhizomik.net/~roberto/
 */

import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ProposalRepository extends PagingAndSortingRepository<Proposal, Long> {

    // PagingAndSortingRepository provides:
    // exists(ID id), delete(T entity), findAll(Pageable), findAll(Sort), findOne(ID id), save(T entity),...
    // http://docs.spring.io/spring-data/jpa/docs/current/reference/html/

    @Query("select p from Proposal as p where " +
            "p.creator.username=?#{authentication.principal.username} or 1=?#{hasRole('ROLE_ADMIN') ? 1 : 0}")
    List<Proposal> findMine();

    List<Proposal> findByTitleContaining(String title);

    Proposal findByTitle(String title);
}
