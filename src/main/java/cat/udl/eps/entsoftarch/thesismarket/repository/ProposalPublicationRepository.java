package cat.udl.eps.entsoftarch.thesismarket.repository;

/**
 * Created by denis casajus on 25/02/2016.
 */
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalPublication;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface ProposalPublicationRepository extends PagingAndSortingRepository<ProposalPublication, Long> {

}

