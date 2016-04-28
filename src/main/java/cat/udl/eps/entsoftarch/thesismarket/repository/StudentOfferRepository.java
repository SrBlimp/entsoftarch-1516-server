package cat.udl.eps.entsoftarch.thesismarket.repository;

import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import cat.udl.eps.entsoftarch.thesismarket.domain.Student;
import cat.udl.eps.entsoftarch.thesismarket.domain.StudentOffer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by victor on 29/02/16.
 */
@Repository
@RepositoryRestResource
public interface StudentOfferRepository extends PagingAndSortingRepository<StudentOffer,Long>{

    List<StudentOffer> findByAgent(Student student);



}
