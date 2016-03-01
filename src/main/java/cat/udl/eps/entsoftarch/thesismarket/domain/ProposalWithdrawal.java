package cat.udl.eps.entsoftarch.thesismarket.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
public class ProposalWithdrawal extends Event {
    @ManyToOne
    Proponent agent;
    @OneToOne
    @NotNull
    ProposalSubmission withdraws;

    public Proponent getAgent() { return agent; }

    public void setAgent(Proponent agent) { this.agent = agent; }

    public ProposalSubmission getWithdraws() { return withdraws; }

    public void setWithdraws(ProposalSubmission withdraws) { this.withdraws = withdraws; }

    @Override
    public String toString() {
        return "ProposalWithdrawal of " + withdraws;
    }
}
