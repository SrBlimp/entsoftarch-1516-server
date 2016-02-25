package cat.udl.eps.entsoftarch.thesismarket.repository;

/**
 * Created by Albert Merino
 */

import cat.udl.eps.entsoftarch.thesismarket.domain.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
}
