package cat.udl.eps.entsoftarch.thesismarket.domain;

import org.springframework.data.rest.core.config.Projection;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Projection(name = "ProposalPublicationDetails", types = { ProposalPublication.class })
public interface ProposalPublicationDetails {
    Proposal getProposal();
}
