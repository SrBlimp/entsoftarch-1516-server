package cat.udl.eps.entsoftarch.thesismarket.repository;

import cat.udl.eps.entsoftarch.thesismarket.domain.Proponent;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Repository
@RepositoryRestResource
public interface ProponentRepository extends PagingAndSortingRepository<Proponent, String> {
}
