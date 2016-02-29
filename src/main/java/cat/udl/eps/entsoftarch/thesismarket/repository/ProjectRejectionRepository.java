package cat.udl.eps.entsoftarch.thesismarket.repository;


import cat.udl.eps.entsoftarch.thesismarket.domain.ProjectRejection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface ProjectRejectionRepository extends PagingAndSortingRepository<ProjectRejection, Long> {
}
