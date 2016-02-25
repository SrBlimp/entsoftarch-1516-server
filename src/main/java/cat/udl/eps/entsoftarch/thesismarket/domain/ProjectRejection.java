package cat.udl.eps.entsoftarch.thesismarket.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
public class ProjectRejection extends Event {
    @ManyToOne
    Coordinator agent;
    @OneToOne
    @NotNull
    ProposalPublication rejects;
}
