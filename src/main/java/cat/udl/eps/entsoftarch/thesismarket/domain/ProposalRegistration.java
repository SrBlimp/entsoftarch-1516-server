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
    private ProjectDeposit depositedBy;


    public Coordinator getAgent() {return agent;}

    public void setAgent(Coordinator agent){this.agent = agent;}

    public ProposalPublication getRegisters(){return registers;}

    public void setRegisters(ProposalPublication proposalPublication){ this.registers = proposalPublication;}

    @Nullable
    public ProjectDeposit getDepositedBy(){return depositedBy;}

    public  void setDepositedBy(ProjectDeposit projectDeposit){ this.depositedBy = projectDeposit;}

    @Nullable
    public ProposalWithdrawal getWithdrawedBy(){return getWithdrawedBy();}

    public void setWithdrawedBy(ProposalWithdrawal proposalWithdrawal){this.withdrawedBy = proposalWithdrawal;}
}
