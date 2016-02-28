package cat.udl.eps.entsoftarch.thesismarket.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String title;
    private String description;
    private String degree;
    private String status;
    @ElementCollection
    private Set<String> topics = new HashSet<>();
    @ManyToOne
    private Proponent creator;
    @ManyToMany
    @Size(max = 2, message = "Maximum student per proposal is {max}")
    private Set<Student> students = new HashSet<>();
    @ManyToOne
    private Professor director;
    @ManyToOne
    private Professor codirector;
    @OneToMany(mappedBy = "submits")
    private Set<ProposalSubmission> submittedBy = new HashSet<>();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getDegree() { return degree; }

    public void setDegree(String degree) { this.degree = degree; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Set<String> getTopics() { return topics; }

    public void setTopics(Set<String> topics) { this.topics = topics; }

    public Proponent getCreator() { return creator; }

    public void setCreator(Proponent creator) { this.creator = creator; }

    public Set<Student> getStudents() { return students; }

    public void setStudents(Set<Student> students) { this.students = students; }

    public Professor getDirector() { return director; }

    public void setDirector(Professor director) { this.director = director; }

    public Professor getCodirector() { return codirector; }

    public void setCodirector(Professor codirector) { this.codirector = codirector; }

    public Set<ProposalSubmission> getSubmittedBy() { return submittedBy; }

    public void setSubmittedBy(Set<ProposalSubmission> submittedBy) { this.submittedBy = submittedBy; }
}


