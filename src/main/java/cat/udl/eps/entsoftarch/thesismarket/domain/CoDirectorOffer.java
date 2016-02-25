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
public class CoDirectorOffer extends Offer {
    @ManyToOne
    Professor agent;
    @OneToOne
    @NotNull
    ProposalPublication target;
    @OneToOne(mappedBy = "assigns")
    @JsonIgnore
    @Nullable
    CoDirectorAssignment assignedBy;
}
