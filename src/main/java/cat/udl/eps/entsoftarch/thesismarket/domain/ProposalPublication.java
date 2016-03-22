package cat.udl.eps.entsoftarch.thesismarket.domain;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
public class ProposalPublication extends Event {
    @ManyToOne
    private Proponent agent;
    @OneToOne
    @NotNull
    private ProposalSubmission publishes;
    @OneToMany(mappedBy = "comments")
    private Set<Comment> commentedBy = new HashSet<>();
    @OneToOne(mappedBy = "rejects")
    private ProjectRejection rejectedBy;
    @OneToOne(mappedBy = "registers")
    private ProposalRegistration registeredBy;
    @OneToMany(mappedBy = "target")
    private Set<StudentOffer> interestedStudents = new HashSet<>();
    @OneToMany(mappedBy = "target")
    private Set<DirectorOffer> interestedDirectors = new HashSet<>();
    @OneToMany(mappedBy = "target")
    private Set<CoDirectorOffer> interestedCoDirectors = new HashSet<>();

    public Proponent getAgent() { return agent; }

    public void setAgent(Proponent agent) { this.agent = agent; }

    public ProposalSubmission getPublishes() { return publishes; }

    public void setPublishes(ProposalSubmission publishes) { this.publishes = publishes; }

    public Set<Comment> getCommentedBy() { return commentedBy; }

    public void setCommentedBy(Set<Comment> commentedBy) { this.commentedBy = commentedBy; }

    @Nullable
    public ProjectRejection getRejectedBy() { return rejectedBy; }

    public void setRejectedBy(@Nullable ProjectRejection rejectedBy) { this.rejectedBy = rejectedBy; }

    @Nullable
    public ProposalRegistration getRegisteredBy() { return registeredBy; }

    public void setRegisteredBy(@Nullable ProposalRegistration registeredBy) { this.registeredBy = registeredBy; }

    public Set<StudentOffer> getInterestedStudents() { return interestedStudents; }

    public void setInterestedStudents(Set<StudentOffer> interestedStudents) { this.interestedStudents = interestedStudents; }

    public Set<DirectorOffer> getInterestedDirectors() { return interestedDirectors; }

    public void setInterestedDirectors(Set<DirectorOffer> interestedDirectors) { this.interestedDirectors = interestedDirectors; }

    public Set<CoDirectorOffer> getInterestedCoDirectors() { return interestedCoDirectors; }

    public void setInterestedCoDirectors(Set<CoDirectorOffer> interestedCoDirectors) { this.interestedCoDirectors = interestedCoDirectors; }
}
