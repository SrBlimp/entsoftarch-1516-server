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
public class ProposalRegistration extends Event {
    @ManyToOne
    Coordinator agent;
    @OneToOne(mappedBy = "withdraws")
    @Nullable
    @JsonIgnore
    private ProposalWithdrawal withdrawedBy;
    @OneToOne
    @NotNull
    ProposalPublication registers;
    @OneToOne(mappedBy = "deposits")
    @Nullable
    @JsonIgnore
    private ProposalDeposit depositedBy;
}
