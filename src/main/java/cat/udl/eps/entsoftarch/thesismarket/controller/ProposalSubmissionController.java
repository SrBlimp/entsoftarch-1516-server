package cat.udl.eps.entsoftarch.thesismarket.controller;


import cat.udl.eps.entsoftarch.thesismarket.domain.Proponent;
import cat.udl.eps.entsoftarch.thesismarket.domain.ProposalSubmission;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProponentRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalSubmissionRepository;
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

@RepositoryRestController
public class ProposalSubmissionController {

    @Autowired
    PagedResourcesAssembler pagedResourcesAssembler;
    @Autowired
    ProposalSubmissionRepository proposalSubmissionRepository;
    @Autowired
    ProponentRepository proponentRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/proposalSubmissions")
    public @ResponseBody
    PagedResources<PersistentEntityResource> getProposalSubmissions(
            HttpServletRequest request, Principal principal, Pageable pageable,
            PersistentEntityResourceAssembler resourceAssembler) {

        Page<ProposalSubmission> proposalSubmissions;

        if (request.isUserInRole("ADMIN") || request.isUserInRole("COORDINATOR")) {
            proposalSubmissions = proposalSubmissionRepository.findAll(pageable);
        } else {
            Proponent proponent = proponentRepository.findOne(principal.getName());
            proposalSubmissions = proposalSubmissionRepository.findByAgent(proponent, pageable);
        }

        return pagedResourcesAssembler.toResource(proposalSubmissions, resourceAssembler);
    }

}
