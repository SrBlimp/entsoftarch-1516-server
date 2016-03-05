package cat.udl.eps.entsoftarch.thesismarket;

import cat.udl.eps.entsoftarch.thesismarket.domain.*;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalPublicationRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalSubmissionRepository;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalWithdrawalRepository;
import com.jayway.jsonpath.JsonPath;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@ContextConfiguration(classes = {ThesismarketApiApplication.class}, loader = SpringApplicationContextLoader.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class MyStepdefs {
    private static final Logger logger = LoggerFactory.getLogger(MyStepdefs.class);

    private MockMvc mockMvc;
    private ResultActions result;

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private ProposalSubmissionRepository proposalSubmissionRepository;
    @Autowired
    private ProposalWithdrawalRepository proposalWithdrawalRepository;
    @Autowired
    private ProposalPublicationRepository proposalPublicationRepository;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .build();
    }

    @Given("^there is an existing proposal with title \"([^\"]*)\"$")
    public void thereIsAnExistingProposalWithTitle(String title) throws Throwable {
        Proposal proposal = new Proposal();
        proposal.setTitle(title);
        proposalRepository.save(proposal);
    }

    @And("^there is an existing submission of the proposal titled \"([^\"]*)\"$")
    public void thereIsAnExistingSubmissionOfTheProposalTitled(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        ProposalSubmission proposalSubmission = new ProposalSubmission();
        proposalSubmission.setSubmits(proposal);
        proposalSubmissionRepository.save(proposalSubmission);
    }

    @And("^there is an existing proposal publication of a submission of the proposal titled \"([^\"]*)\"$")
    public void thereIsAnExistingProposalPublicationOfASubmissionOfTheProposalTitled(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        ProposalSubmission proposalSubmission = proposalSubmissionRepository.findBySubmits(proposal).get(0);
        ProposalPublication proposalPublication = new ProposalPublication();
        proposalPublication.setPublishes(proposalSubmission);
        proposalPublicationRepository.save(proposalPublication);
    }

    @When("^I submit the proposal with title \"([^\"]*)\"$")
    public void iSubmitTheProposalWithTitle(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);

        String message = String.format(
                "{ \"submits\": \"proposals/%s\" }", proposal.getId());

        result = mockMvc.perform(post("/proposalSubmissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON));
    }

    @When("^I withdraw the submission of the proposal titled \"([^\"]*)\"$")
    public void iWithdrawTheSubmissionOfTheProposalTitled(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        ProposalSubmission proposalSubmission = proposalSubmissionRepository.findBySubmits(proposal).get(0);
        ProposalWithdrawal proposalWithdrawal = new ProposalWithdrawal();
        proposalWithdrawal.setWithdraws(proposalSubmission);

        String message = String.format(
                "{ \"withdraws\": \"proposalSubmissions/%s\" }", proposalSubmission.getId());

        result = mockMvc.perform(post("/proposalWithdrawals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON));
    }

    @When("^I comment the proposal with title \"([^\"]*)\" with a comment with text \"([^\"]*)\"$")
    public void iCommentTheProposalWithTitleWithACommentWithText(String title, String text) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        ProposalSubmission proposalSubmission = proposalSubmissionRepository.findBySubmits(proposal).get(0);
        ProposalPublication proposalPublication = proposalPublicationRepository.findByPublishes(proposalSubmission).get(0);

        String message = String.format(
                "{ \"comments\" : \"proposalPublications/%s\", \"text\":\"%s\"}", proposalPublication.getId(), text);

        result = mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON));
    }

    @Then("^I have created a proposal submission that submits a proposal with title \"([^\"]*)\"$")
    public void iHaveCreatedAProposalSubmissionThatSubmitsAProposalWithTitle(String title) throws Throwable {

        String response = result
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String submitsUri = JsonPath.read(response, "$._links.submits.href");

        result = mockMvc.perform(get(submitsUri)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(title)));
    }

    @Then("^I have created a withdrawal of the submission of the proposal titled \"([^\"]*)\"$")
    public void iHaveCreatedAWithdrawalOfTheSubmissionOfTheProposalTitled(String title) throws Throwable {

        String response = result
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String withdrawsUri = JsonPath.read(response, "$._links.withdraws.href");

        result = mockMvc.perform(get(withdrawsUri)
                .accept(MediaType.APPLICATION_JSON));

        response = result
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        String submitsUri = JsonPath.read(response, "$._links.submits.href");

        result = mockMvc.perform(get(submitsUri)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(title)));
    }


    @Then("^I have created a comment that comments a proposal with text \"([^\"]*)\"$")
    public void iHaveCreatedACommentThatCommentsAProposalWithText(String text) throws Throwable {

        result.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text", is(text)));
    }
}

