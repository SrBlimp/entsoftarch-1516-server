package cat.udl.eps.entsoftarch.thesismarket.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
public class StudentsAssignment extends Assignment {
    @OneToMany
    @Size(min = 1, max = 2, message = "Minimum {min} student per proposal and maximum {max} students")
    private Set<StudentOffer> assigns = new HashSet<>();

    public Set<StudentOffer> getAssigns() {
        return assigns;
    }

    public void setAssigns(Set<StudentOffer> assigns) {
        this.assigns = assigns;
    }

}
