package cat.udl.eps.entsoftarch.thesismarket.repository;

/**
 * Created by http://rhizomik.net/~roberto/
 */

import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProposalRepository extends PagingAndSortingRepository<Proposal, Long> {

    // PagingAndSortingRepository provides:
    // exists(ID id), delete(T entity), findAll(Pageable), findAll(Sort), findOne(ID id), save(T entity),...
    // http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
}
