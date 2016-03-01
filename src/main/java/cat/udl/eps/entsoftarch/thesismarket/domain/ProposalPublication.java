package cat.udl.eps.entsoftarch.thesismarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Coordinator agent;
    @OneToOne
    @NotNull
    private ProposalSubmission publishes;
    @OneToMany(mappedBy = "comments")
    @JsonIgnore
    private Set<Comment> commentedBy = new HashSet<>();
    @OneToOne(mappedBy = "rejects")
    @Nullable
    @JsonIgnore
    private ProjectRejection rejectedBy;
    @OneToOne(mappedBy = "registers")
    @Nullable
    @JsonIgnore
    private ProposalRegistration registeredBy;
    @OneToMany(mappedBy = "target")
    @JsonIgnore
    private Set<StudentOffer> interestedStudents = new HashSet<>();
    @OneToMany(mappedBy = "target")
    @JsonIgnore
    private Set<DirectorOffer> interestedDirectors = new HashSet<>();
    @OneToMany(mappedBy = "target")
    @JsonIgnore
    private Set<CoDirectorOffer> interestedCoDirectors = new HashSet<>();
}