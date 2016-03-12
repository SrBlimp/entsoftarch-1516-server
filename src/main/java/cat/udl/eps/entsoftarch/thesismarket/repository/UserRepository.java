package cat.udl.eps.entsoftarch.thesismarket.repository;

import cat.udl.eps.entsoftarch.thesismarket.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Repository
@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, String> {
}
