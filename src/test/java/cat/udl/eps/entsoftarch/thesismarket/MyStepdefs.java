package cat.udl.eps.entsoftarch.thesismarket;

import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import cat.udl.eps.entsoftarch.thesismarket.repository.ProposalRepository;
import com.jayway.jsonpath.JsonPath;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
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
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class MyStepdefs {
    private static final Logger logger = LoggerFactory.getLogger(MyStepdefs.class);

    private MockMvc mockMvc;
    private ResultActions result;

    @Autowired private WebApplicationContext wac;
    @Autowired private ProposalRepository proposalRepository;

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

    @When("^I submit the proposal with title \"([^\"]*)\"$")
    public void iSubmitTheProposalWithTitle(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        result = mockMvc.perform(post("/proposalSubmissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"submits\": \"proposals/" + proposal.getId() + "\"" +
                        "}")
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
}

