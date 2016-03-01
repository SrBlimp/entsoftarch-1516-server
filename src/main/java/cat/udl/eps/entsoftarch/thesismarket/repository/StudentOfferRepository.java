package cat.udl.eps.entsoftarch.thesismarket.repository;

import cat.udl.eps.entsoftarch.thesismarket.domain.Student;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by victor on 29/02/16.
 */
public interface StudentOfferRepository extends PagingAndSortingRepository<Student,Long>{
}
