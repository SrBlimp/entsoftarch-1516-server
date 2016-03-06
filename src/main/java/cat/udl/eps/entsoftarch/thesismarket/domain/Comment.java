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
    private String text;
    @ManyToOne
    private User author;
    @ManyToOne
    @NotNull
    private ProposalPublication comments;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public User getAuthor() { return author; }

    public void setAuthor(User author) { this.author = author; }

    public ProposalPublication getComments() { return comments; }

    public void setComments(ProposalPublication comments) { this.comments = comments; }
}