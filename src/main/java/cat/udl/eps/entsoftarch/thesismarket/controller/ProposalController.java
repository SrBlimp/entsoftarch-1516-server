package cat.udl.eps.entsoftarch.thesismarket.controller;

import cat.udl.eps.entsoftarch.thesismarket.domain.Proponent;
import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProponentRepository;
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
 * Created by http://rhizomik.net/~roberto/
 */
@RepositoryRestController
public class ProposalController {

    @Autowired PagedResourcesAssembler pagedResourcesAssembler;
    @Autowired ProposalRepository proposalRepository;
    @Autowired ProponentRepository proponentRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/proposals")
    public @ResponseBody PagedResources<PersistentEntityResource> getProposals(
            HttpServletRequest request, Principal principal, Pageable pageable,
            PersistentEntityResourceAssembler resourceAssembler) {

        Page<Proposal> proposals;

        if (request.isUserInRole("ADMIN"))
            proposals = proposalRepository.findAll(pageable);
        else {
            Proponent proponent = proponentRepository.findOne(principal.getName());
            proposals = proposalRepository.findByCreator(proponent, pageable);
        }

        return pagedResourcesAssembler.toResource(proposals, resourceAssembler);
    }
}
