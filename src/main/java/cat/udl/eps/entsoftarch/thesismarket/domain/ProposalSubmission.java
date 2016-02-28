package cat.udl.eps.entsoftarch.thesismarket.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
public class ProposalSubmission extends Event {
    @ManyToOne
    private Proponent agent;
    @ManyToOne
    private Proposal submits;
/*    @OneToOne(mappedBy = "publishes")
    private ProposalPublication publishedBy;
    @OneToOne(mappedBy = "rejects")
    private ProposalRejection rejectedBy;
    @OneToOne(mappedBy = "withdraws")
    private ProposalWithdrawal withdrawedBy;*/

    public Proponent getAgent() { return agent; }

    public void setAgent(Proponent agent) { this.agent = agent; }

    public Proposal getSubmits() { return submits; }

    public void setSubmits(Proposal submits) { this.submits = submits; }

/*    @Nullable
    public ProposalPublication getPublishedBy() { return publishedBy; }

    public void setPublishedBy(ProposalPublication publishedBy) {
        this.publishedBy = publishedBy;
    }

    @Nullable
    public ProposalRejection getRejectedBy() { return rejectedBy; }

    public void setRejectedBy(ProposalRejection rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    @Nullable
    public ProposalWithdrawal getWithdrawedBy() { return withdrawedBy; }

    public void setWithdrawedBy(ProposalWithdrawal withdrawedBy) {
        this.withdrawedBy = withdrawedBy;
    }*/
}
