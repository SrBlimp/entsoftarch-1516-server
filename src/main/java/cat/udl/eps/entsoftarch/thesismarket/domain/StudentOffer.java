package cat.udl.eps.entsoftarch.thesismarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
public class StudentOffer extends Offer {
    @ManyToOne
    Student agent;
    @OneToOne
    @NotNull
    ProposalPublication target;
    @OneToOne
    @JsonIgnore
    @Nullable
    StudentsAssignment assignedBy;
}
