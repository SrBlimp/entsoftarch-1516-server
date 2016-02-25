package cat.udl.eps.entsoftarch.thesismarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
public class ProposalSubmission extends Event {
    @ManyToOne
    private Proponent agent;
    @ManyToOne
    private Proposal submits;
    @OneToOne(mappedBy = "publishes")
    @Nullable
    @JsonIgnore
    private ProposalPublication publishedBy;
    @OneToOne(mappedBy = "rejects")
    @Nullable
    @JsonIgnore
    private ProposalRejection rejectedBy;
    @OneToOne(mappedBy = "withdraws")
    @Nullable
    @JsonIgnore
    private ProposalWithdrawal withdrawedBy;
}
