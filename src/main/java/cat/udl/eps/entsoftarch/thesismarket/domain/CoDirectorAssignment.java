package cat.udl.eps.entsoftarch.thesismarket.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
public class CoDirectorAssignment extends Assignment {
    @OneToOne
    @NotNull
    private CoDirectorOffer assigns;
}
