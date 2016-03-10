package cat.udl.eps.entsoftarch.thesismarket.repository;

import cat.udl.eps.entsoftarch.thesismarket.domain.StudentsAssignment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by Anna i Raul.
 */
@Repository
@RepositoryRestResource
public interface StudentAssignmentRepository extends PagingAndSortingRepository<StudentsAssignment, Long> {
}
