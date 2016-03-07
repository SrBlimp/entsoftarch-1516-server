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
    private Student agent;
    @OneToOne
    @NotNull
    private ProposalPublication target;
    @OneToOne
    @JsonIgnore
    private StudentsAssignment assignedBy;

    public Student getAgent() {
        return agent;
    }

    public void setAgent(Student agent) {
        this.agent = agent;
    }

    public ProposalPublication getTarget() {
        return target;
    }

    public void setTarget(ProposalPublication target) {
        this.target = target;
    }

    @Nullable
    public StudentsAssignment getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(@Nullable StudentsAssignment assignedBy) {
        this.assignedBy = assignedBy;
    }
}
