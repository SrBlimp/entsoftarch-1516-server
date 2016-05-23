package cat.udl.eps.entsoftarch.thesismarket.controller;

import cat.udl.eps.entsoftarch.thesismarket.domain.Coordinator;
import cat.udl.eps.entsoftarch.thesismarket.domain.Proponent;
import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalPublication;
import cat.udl.eps.entsoftarch.thesismarket.repository.CoordinatorRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProponentRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalPublicationRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by ferran on 23/5/16.
 */
@RepositoryRestController
public class ProposalPublicationController {

    @Autowired
    PagedResourcesAssembler pagedResourcesAssembler;
    @Autowired
    ProposalPublicationRepository publicationsRepository;
    @Autowired
    CoordinatorRepository coordinatorRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/proposalPublications")
    public @ResponseBody
    PagedResources<PersistentEntityResource> getProposalsPublications(
            HttpServletRequest request, Principal principal, Pageable pageable,
            PersistentEntityResourceAssembler resourceAssembler) {

        Page<ProposalPublication> proposalsPublication;

        if ( request.isUserInRole("ADMIN") || request.isUserInRole("COORDINATOR") )
            proposalsPublication = publicationsRepository.findAll(pageable);
        else {
            Coordinator coordinator = coordinatorRepository.findOne(principal.getName());
            proposalsPublication = publicationsRepository.findByAgent(coordinator, pageable);
        }

        return pagedResourcesAssembler.toResource(proposalsPublication, resourceAssembler);
    }

}