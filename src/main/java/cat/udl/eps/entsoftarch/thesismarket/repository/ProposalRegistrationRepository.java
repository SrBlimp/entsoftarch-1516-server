package cat.udl.eps.entsoftarch.thesismarket.repository;

/**
 * Created by denis casajus on 25/02/2016.
 */

import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalRegistration;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


@Repository
@RepositoryRestResource
public interface ProposalRegistrationRepository extends PagingAndSortingRepository<ProposalRegistration, Long> {
}

