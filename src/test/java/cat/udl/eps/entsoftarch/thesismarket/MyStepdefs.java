package cat.udl.eps.entsoftarch.thesismarket;

import cat.udl.eps.entsoftarch.thesismarket.config.AuthenticationTestConfig;
import cat.udl.eps.entsoftarch.thesismarket.config.MailTestConfig;
import cat.udl.eps.entsoftarch.thesismarket.domain.*;
import cat.udl.eps.entsoftarch.thesismarket.repository.*;
import cat.udl.eps.entsoftarch.thesismarket.security.AuthenticationTestConfig;
import cat.udl.eps.entsoftarch.thesismarket.security.WebSecurityConfig;
import cat.udl.eps.entsoftarch.thesismarket.repository.*;
import com.jayway.jsonpath.JsonPath;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@ContextConfiguration(
        classes = {ThesismarketApiApplication.class, MailTestConfig.class, AuthenticationTestConfig.class},
        loader = SpringApplicationContextLoader.class)
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class MyStepdefs {
    private static final Logger logger = LoggerFactory.getLogger(MyStepdefs.class);

    private MockMvc mockMvc;
    private ResultActions result;

    @Autowired private WebApplicationContext wac;
    @Autowired private ProposalRepository proposalRepository;
    @Autowired private ProposalSubmissionRepository proposalSubmissionRepository;
    @Autowired private ProposalPublicationRepository proposalPublicationRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ProponentRepository proponentRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private JavaMailSender javaMailSender;
    @Autowired private UserRepository userRepository;
    @Autowired private StudentRepository stdRepository;

    private String currentUsername;
    private String currentPassword;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Given("^I login as \"([^\"]*)\" with password \"([^\"]*)\"$")
    public void iLoginAsWithPassword(String username, String password) throws Throwable {
        this.currentUsername = username;
        this.currentPassword = password;
    }

    @Given("^I'm not logged in$")
    public void iMNotLoggedIn() throws Throwable {
        this.currentUsername = this.currentPassword = null;
    }

    @And("^there is an existing proposal with title \"([^\"]*)\" by \"([^\"]*)\"$")
    public void thereIsAnExistingProposalWithTitleBy(String title, String username) throws Throwable {
        Proposal proposal = new Proposal();
        proposal.setTitle(title);
        Proponent proponent = proponentRepository.findOne(username);
        proposal.setCreator(proponent);
        proposalRepository.save(proposal);
    }

    @Given("^there is an existing proposal with title \"([^\"]*)\"$")
    public void thereIsAnExistingProposalWithTitle(String title) throws Throwable {
        Proposal proposal = new Proposal();
        proposal.setTitle(title);
        proposalRepository.save(proposal);
    }

    @Given("^there is an existing student with id \"([^\"]*)\"$")
    public void thereIsAnExistingUserWithId(Long id) throws Throwable {
        User user = new User();
        user.setId(id);
        //Falta implementar save a la classe UserRepository
        userRepository.save(user);
    }

    @And("^there is an existing submission of the proposal titled \"([^\"]*)\"$")
    public void thereIsAnExistingSubmissionOfTheProposalTitled(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        ProposalSubmission proposalSubmission = new ProposalSubmission();
        proposalSubmission.setSubmits(proposal);
        proposal.setStatus(Proposal.Status.SUBMITTED);
        proposalRepository.save(proposal);
        proposalSubmissionRepository.save(proposalSubmission);
    }

    @And("^there is an existing publication of the proposal titled \"([^\"]*)\"$")
    public void thereIsAnExistingPublicationOfTheProposalTitled(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        ProposalSubmission proposalSubmission = proposalSubmissionRepository.findBySubmits(proposal).get(0);
        ProposalPublication proposalPublication = new ProposalPublication();
        proposalPublication.setPublishes(proposalSubmission);
        proposal.setStatus(Proposal.Status.PUBLISHED);
        proposalRepository.save(proposal);
        proposalPublicationRepository.save(proposalPublication);
    }

    @And("^the status of the proposal titled \"([^\"]*)\" is \"([^\"]*)\"$")
    public void theStatusOfTheProposalTitledIs(String title, Proposal.Status status) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        assertThat(proposal.getStatus(), is(status));
    }

    @And("^there is not an existing proposal titled \"([^\"]*)\"$")
    public void thereIsNotAnExistingProposalTitled(String title) throws Throwable {
        assertTrue(proposalRepository.findByTitleContaining(title).isEmpty());
    }

    @And("^there is not a submission of the proposal titled \"([^\"]*)\"$")
    public void thereIsNotASubmissionOfTheProposalTitled(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        assertTrue(proposalSubmissionRepository.findBySubmits(proposal).isEmpty());
    }

    @And("^there is not a publication of the submission of the proposal titled \"([^\"]*)\"$")
    public void thereIsNotAPublicationOfTheSubmissionOfTheProposalTitled(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        ProposalSubmission proposalSubmission = proposalSubmissionRepository.findBySubmits(proposal).get(0);
        assertNull(proposalSubmission.getPublishedBy());
    }

    @And("^the status of the proposal titled \"([^\"]*)\" is set to \"([^\"]*)\"$")
    public void theStatusOfTheProposalTitledIsSetTo(String title, Proposal.Status status) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        proposal.setStatus(status);
        proposalRepository.save(proposal);
    }

    @When("^I submit the proposal with title \"([^\"]*)\"$")
    public void iSubmitTheProposalWithTitle(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);

        String message = String.format(
                "{ \"submits\": \"proposals/%s\" }", proposal.getId());

        result = mockMvc.perform(post("/proposalSubmissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
    }

    @When("^I publish the proposal with title \"([^\"]*)\"$")
    public void iPublishTheProposalWithTitle(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        ProposalSubmission proposalSubmission = proposalSubmissionRepository.findBySubmits(proposal).get(0);
        ProposalPublication proposalPublication = new ProposalPublication();
        proposalPublication.setPublishes(proposalSubmission);

        String message = String.format(
                "{ \"publishes\": \"proposalSubmissions/%s\" }",  proposalSubmission.getId());

        result = mockMvc.perform(post("/proposalPublications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
    }

    @When("^I withdraw the submission of the proposal titled \"([^\"]*)\"$")
    public void iWithdrawTheSubmissionOfTheProposalTitled(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        ProposalSubmission proposalSubmission = proposalSubmissionRepository.findBySubmits(proposal).get(0);

        String message = String.format(
                "{ \"withdraws\": \"proposalSubmissions/%s\" }", proposalSubmission.getId());

        MockHttpServletRequestBuilder postRequest = post("/proposalWithdrawals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON);
        if (currentUsername != null)
           postRequest.with(httpBasic(currentUsername, currentPassword));

        result = mockMvc.perform(postRequest);
    }

    @When("^I comment the proposal publication of the proposal titled \"([^\"]*)\"$")
    public void iCommentTheProposalPublicationOfTheProposalTitled(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        ProposalSubmission proposalSubmission = proposalSubmissionRepository.findBySubmits(proposal).get(0);
        ProposalPublication proposalPublication = proposalSubmission.getPublishedBy();

        String message = String.format(
                "{ \"comments\": \"proposalPublications/%s\" }", proposalPublication.getId());

        result = mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
                .accept(MediaType.APPLICATION_JSON));

    }

    @When("^I assign a existing user to the published proposal titled \"([^\"]*)\"$")
    public void iAssignExistingUsertoProposalTitled(String title) throws Throwable {
        /*
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
        */
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
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
    }

    @When("^I withdraw an un-existing submission$")
    public void iWithdrawAnUnexistingSubmission() throws Throwable {
        String message = String.format(
                "{ \"withdraws\": \"proposalSubmissions/%s\" }", 9999);

        result = mockMvc.perform(post("/proposalWithdrawals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
    }

    @When("^I comment an un-existing publication$")
    public void iCommentAnUnExistingPublication() throws Throwable {
        String message = String.format(
                "{ \"comments\": \"proposalPublications/%s\" }", 9999);

        result = mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
    }

    @When("^I publish an un-existing proposal$")
    public void iPublishAnUnexistingProposal() throws Throwable {
        String message = String.format(
                "{ \"submits\": \"proposals/%s\" }", 101929383);

        result = mockMvc.perform(post("/proposalPublications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
    }

    @When("^I publish an un-existing submission$")
    public void iPublishAnUnexistingSubmission() throws Throwable {
        String message = String.format(
                "{ \"publishes\": \"proposalSubmissions/%s\" }", 101929383);

        result = mockMvc.perform(post("/proposalPublications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
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

    @Then("^I have a proposal publication with title \"([^\"]*)\"$")
    public void iHaveAProposalPublicationWithTitle(String title) throws Throwable {

        String response = result
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String publishesUri = JsonPath.read(response, "$._links.publishes.href");

        result = mockMvc.perform(get(publishesUri)
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

    @Then("^I get error (\\d+) with message \"([^\"]*)\"$")
    public void iGetErrorWithMessage(int status, String message) throws Throwable {
        result.andDo(print())
                .andExpect(status().is(status));
        if (result.andReturn().getResponse().getContentAsString().isEmpty())
            result.andExpect(status().reason(is(message)));
        else
            result.andExpect(jsonPath("$..message", hasItem(message)));
    }

    @When("^I create the proposal with title \"([^\"]*)\"$")
    public void iCreateTheProposalWithTitle(String title) throws Throwable {
        String message = String.format(
                "{ \"title\": \"%s\" }", title);

        result = mockMvc.perform(post("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
    }

    @Then("^new proposal with title \"([^\"]*)\"$")
    public void newProposalWithTitle(String title) throws Throwable {
        result.andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(title)));
    }

    @When("^I offer as student to a publication proposal with title \"([^\"]*)\"$")
    public void iOfferAsStudentToAPublicationProposalWithTitle(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        ProposalSubmission proposalSubmission = proposalSubmissionRepository.findBySubmits(proposal).get(0);
        ProposalPublication proposalPublication = proposalPublicationRepository.findByPublishes(proposalSubmission).get(0);

        String message = String.format(
                "{ \"target\": \"proposalPublications/%s\" }", proposalPublication.getId());

        MockHttpServletRequestBuilder postRequest = post("/studentOffers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON);
        if (currentUsername != null)
            postRequest.with(httpBasic(currentUsername, currentPassword));

        result = mockMvc.perform(postRequest);
    }

    @Then("^I have created an offer student of the publication proposal of the submission of the proposal titled \"([^\"]*)\"$")
    public void iHaveCreatedAnOfferStudentOfThePublicationProposalOfTheSubmissionOfTheProposalTitled(String title) throws Throwable {

        String response = result
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String proposalPublicationUri = JsonPath.read(response, "$._links.target.href");

        result = mockMvc.perform(get(proposalPublicationUri).
                accept(MediaType.APPLICATION_JSON));

        response = result
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        String proposalSubmissionUri = JsonPath.read(response, "$._links.publishes.href");

        result = mockMvc.perform(get(proposalSubmissionUri)
                .accept(MediaType.APPLICATION_JSON));

        response = result
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        String submitsUri = JsonPath.read(response, "$._links.submits.href");

        result = mockMvc.perform(get(submitsUri)
                .accept(MediaType.APPLICATION_JSON));

        //aqui no hace falta guardar result en response porque no se necesita ir a ningun atributo de esta respuesta
        result
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(title)));

    }

    @When("^I offer as student to a un-existing publication proposal$")
    public void iOfferAsStudentToAUnExistingPublicationProposal() throws Throwable {
        String message = String.format(
                "{ \"target\": \"proposalPublications/%s\" }", 9999);

        result = mockMvc.perform(post("/studentOffers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON));
    }

    @Given("^there is an existing student with id \"([^\"]*)\"$")
    public void thereIsAnExistingStudentWithId(String id) throws Throwable {
        Student std = new Student();
        std.setUsername(id);
        stdRepository.save(std);
    }

    @When("^I assign a existing user with id \"([^\"]*)\" to the published proposal titled \"([^\"]*)\"$")
    public void iAssignExistingUserToProposalTitled(String id, String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        Student std = stdRepository.findOne(id);

        Set<Student> students = proposal.getStudents();
        students.add(std);
        proposal.setStudents(students);

        /*
        String message = String.format(
                "{ \"assigned\": \"proposal /%s to student /%s\" }", proposal.getTitle(), std.getUsername());*/

        /*
        String message = String.format(
                "{ \"withdraws\": \"proposalSubmissions/%s\" }", proposalSubmission.getId());

        result = mockMvc.perform(post("/proposalWithdrawals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON));

        */
    }



    @Then("^I have two offer student more created of the publication proposal of the submission of the proposal titled \"([^\"]*)\"$")
    public void iHaveTwoOfferStudentCreatedOfThePublicationProposalOfTheSubmissionOfTheProposalTitled(String title) throws Throwable {
        String response = result
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String proposalPublicationUri = JsonPath.read(response, "$._links.target.href");

        result = mockMvc.perform(get(proposalPublicationUri).
                accept(MediaType.APPLICATION_JSON));

        response = result
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        String interestedStudents = JsonPath.read(response, "$._links.interestedStudents.href");

        result = mockMvc.perform(get(interestedStudents)
                .accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.studentOffers", hasSize(3)));
    }

    @When("^I offer as student with name \"([^\"]*)\" to a publication proposal with title \"([^\"]*)\"$")
    public void iOfferAsStudentWithNameToAPublicationProposalWithTitle(String studentname, String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        ProposalSubmission proposalSubmission = proposalSubmissionRepository.findBySubmits(proposal).get(0);
        ProposalPublication proposalPublication = proposalPublicationRepository.findByPublishes(proposalSubmission).get(0);

        Student student = new Student();
        student.setUsername(studentname);

        studentRepository.save(student);


        String message = String.format(
                "{ \"target\": \"proposalPublications/%s\", \"agent\": \"students/%s\"}",
                proposalPublication.getId() ,student);

        MockHttpServletRequestBuilder postRequest = post("/studentOffers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON);
        if (currentUsername != null)
            postRequest.with(httpBasic(currentUsername, currentPassword));

        result = mockMvc.perform(postRequest);

    }

    @When("^I submit an unexisting proposal$")
    public void iSubmitAnUnexistingProposal() throws Throwable {
        String message = String.format(
                "{ \"submits\": \"proposals/%s\" }", 9999);

        result = mockMvc.perform(post("/proposalSubmissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
    }

    @Then("^check proposal status is \"([^\"]*)\"$")
    public void checkProposalStatusIs(String status) throws Throwable {
        result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.status", is(status)));
    }

    @Then("^check proposal creator is user logged$")
    public void checkProposalCreatorIsUserLogged() throws Throwable {
        String response = result
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String creatorUri = JsonPath.read(response, "$._links.creator.href");

        result = mockMvc.perform(get(creatorUri).
                accept(MediaType.APPLICATION_JSON));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href", containsString(this.currentUsername)));
    }

    @When("^I edit the proposal with title \"([^\"]*)\" with new title \"([^\"]*)\"$")
    public void iEditTheProposalTitleWith(String title, String newTitle) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        String message = String.format(
                "{ \"title\" : \"%s\"}", newTitle);

        result = mockMvc.perform(put("/proposals/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
    }

    @Given("^there isn't any proposal$")
    public void thereIsnTAnyProposal() throws Throwable {
        proposalRepository.deleteAll();
    }

    @Then("^I have edited the proposal with title \"([^\"]*)\"$")
    public void iHaveEditedTheProposalWithTitle(String newTitle) throws Throwable {
        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(newTitle)));
    }

    @Then("^an email has been sent to \"([^\"]*)\" with subject \"([^\"]*)\" and containing \"([^\"]*)\"$")
    public void an_email_has_been_sent_to_containing(String recipient, String subject, String bodyText) throws Throwable {
        ArgumentCaptor<SimpleMailMessage> argument = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender, atLeastOnce()).send(argument.capture());
        SimpleMailMessage lastEMail = argument.getAllValues().get(argument.getAllValues().size()-1);
        assertTrue(lastEMail.getTo()[0].equals(recipient));
        assertTrue(lastEMail.getSubject().equals(subject));
        assertThat(lastEMail.getText(), containsString(bodyText));
    }
}

