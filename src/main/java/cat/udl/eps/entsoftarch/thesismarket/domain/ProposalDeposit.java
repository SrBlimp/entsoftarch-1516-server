package cat.udl.eps.entsoftarch.thesismarket.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
public class ProposalDeposit extends Event {
    @ManyToOne
    Professor agent;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    ZonedDateTime defenseTime;
    @NotBlank
    String defensePlace;
    @OneToOne
    @NotNull
    ProposalRegistration deposits;
}
