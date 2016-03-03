package cat.udl.eps.entsoftarch.thesismarket.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Comment text cannot be blank")
    @Size(max = 1024, message = "Comment maximum text length is {max} characters long")
    String text;
    @ManyToOne
    User author;
    @ManyToOne
    @NotNull
    ProposalPublication comments;

    public void setComments(ProposalPublication comments) {
        this.comments = comments;
    }
}
