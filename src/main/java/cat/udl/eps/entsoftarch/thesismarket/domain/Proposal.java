package cat.udl.eps.entsoftarch.thesismarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    private Set<ProposalSubmission> submittedBy = new HashSet<>();
}


