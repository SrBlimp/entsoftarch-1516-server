package cat.udl.eps.entsoftarch.thesismarket.domain;

import org.springframework.data.rest.core.config.Projection;

import java.time.ZonedDateTime;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Projection(name = "ProposalPublicationDetails", types = { ProposalPublication.class })
public interface ProposalPublicationDetails {
    Proposal getProposal();
    ZonedDateTime getDateTime();
}
