package cat.udl.eps.entsoftarch.thesismarket;

import cat.udl.eps.entsoftarch.thesismarket.config.MailTestConfig;
import cat.udl.eps.entsoftarch.thesismarket.domain.*;
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

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@ContextConfiguration(
        classes = {ThesismarketApiApplication.class, MailTestConfig.class},
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
    @Autowired private ProponentRepository proponentRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private ProfessorRepository professorRepository;
    @Autowired private JavaMailSender javaMailSender;
    @Autowired private StudentOfferRepository studentOfferRepository;

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

    @Given("^there is an existing student with id \"([^\"]*)\"$")
    public void thereIsAnExistingStudentWithId(String id) throws Throwable {
        Student std = new Student();
        std.setUsername(id);
        studentRepository.save(std);
    }

    @And("^the student of the proposal titled \"([^\"]*)\" is not null$")
    public void theStudentOfTheProposalTitledIsNotNull(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        assertNotNull(proposal.getStudents());
    }

    @And("^the director of the proposal titled \"([^\"]*)\" is not null$")
    public void theDirectorOfTheProposalTitledIsNotNull(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        assertNotNull(proposal.getDirector());
    }

    @And("^the student of the proposal titled \"([^\"]*)\" is set to \"([^\"]*)\"$")
    public void theStudentOfTheProposalTitledIsSetTo(String title, String userName) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        Student student = studentRepository.findOne(userName);
        Set<Student> students = new HashSet<>();
        students.add(student);
        proposal.setStudents(students);
    }

    @And("^the director of the proposal titled \"([^\"]*)\" is set to \"([^\"]*)\"$")
    public void theDirectorOfTheProposalTitledIsSetTo(String title, String userName) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        Professor professor = professorRepository.findOne(userName);
        proposal.setDirector(professor);
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

    @When("^I register published proposal titled \"([^\"]*)\"$")
    public void iRegisterPublishedProposalTitled(String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        ProposalSubmission proposalSubmission = proposalSubmissionRepository.findBySubmits(proposal).get(0);
        ProposalPublication proposalPublication =  proposalPublicationRepository.findByPublishes(proposalSubmission).get(0);

        String message = String.format(
                "{ \"registers\": \"proposalPublications/%s\" }", proposalPublication.getId());

        result = mockMvc.perform(post("/proposalRegistrations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
    }

    @When("^I register un-published proposal$")
    public void iRegisterUnPublishedProposal() throws Throwable {
        String message = String.format(
                "{ \"registers\": \"proposalPublications/%s\" }", 101010);

        result = mockMvc.perform(post("/proposalRegistrations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
    }

    @When("^I register un-assigned proposal$")
    public void iRegisterUnAssignedProposal() throws Throwable {
        String message = String.format(
                "{ \"registers\": \"proposalPublications/%s\" }", 111111);

        result = mockMvc.perform(post("/proposalRegistrations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
    }


    @When("^I assign a existing user with id \"([^\"]*)\" to the published proposal titled \"([^\"]*)\"$")
    public void iAssignExistingUserToProposalTitled(String id, String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        ProposalSubmission proposalSubmission = proposalSubmissionRepository.findBySubmits(proposal).get(0);
        ProposalPublication proposalPublication = proposalPublicationRepository.findByPublishes(proposalSubmission).get(0);

        Student std = studentRepository.findOne(id);
        StudentOffer offer = studentOfferRepository.findByAgent(std).get(0);


        String message = String.format(
                "{ \"assigns\": \"studentOffers/%s\" }", offer.getId());

        result = mockMvc.perform(post("/studentsAssignments")
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
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));

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
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));

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
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));

        response = result
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        String submitsUri = JsonPath.read(response, "$._links.submits.href");

        result = mockMvc.perform(get(submitsUri)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));

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
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));

        response = result
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        String submitsUri = JsonPath.read(response, "$._links.submits.href");

        result = mockMvc.perform(get(submitsUri)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));

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

    @Then("^I have two offer student more created of the publication proposal of the submission of the proposal titled \"([^\"]*)\"$")
    public void iHaveTwoOfferStudentCreatedOfThePublicationProposalOfTheSubmissionOfTheProposalTitled(String title) throws Throwable {
        String response = result
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String proposalPublicationUri = JsonPath.read(response, "$._links.target.href");

        result = mockMvc.perform(get(proposalPublicationUri)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));

        response = result
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        String interestedStudents = JsonPath.read(response, "$._links.interestedStudents.href");

        result = mockMvc.perform(get(interestedStudents)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));

        result
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.studentOffers", hasSize(3)));
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

        result = mockMvc.perform(get(creatorUri)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href", containsString(this.currentUsername)));
    }

    @Then("^I have a registered proposal titled \"([^\"]*)\"$")
    public void iHaveARegisteredProposalTitled(String title) throws Throwable {
        String response = result
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String registersUri = JsonPath.read(response, "$._links.registers.href");

        result = mockMvc.perform(get(registersUri)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));

        response = result
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        String publishesUri = JsonPath.read(response, "$._links.publishes.href");

        result = mockMvc.perform(get(publishesUri)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));

        response = result
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        String submitsUri = JsonPath.read(response, "$._links.submits.href");

        result = mockMvc.perform(get(submitsUri)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));

        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(title)));
    }

    @When("^I edit the proposal with title \"([^\"]*)\" with new title \"([^\"]*)\"$")
    public void iEditTheProposalTitleWith(String title, String newTitle) throws Throwable {
        //Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        String message = String.format(
                "{ \"title\" : \"%s\"}", newTitle);

        MockHttpServletRequestBuilder postRequest = put("/proposals/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(message)
                .accept(MediaType.APPLICATION_JSON);

        if (currentUsername != null)
            postRequest.with(httpBasic(currentUsername, currentPassword));

        result = mockMvc.perform(postRequest);
    }

    /*@When("^I withdraw the submission of the proposal titled \"([^\"]*)\"$")
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
    }*/

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

    @Then("^no message has been sent$")
    public void no_message_has_been_sent() throws Throwable {
        verifyZeroInteractions(javaMailSender);
    }

    @And("^there is an existing offer for the user \"([^\"]*)\" and the proposal \"([^\"]*)\"$")
    public void thereIsAnExistingOfferForTheUserAndTheProposal(String studentname, String title) throws Throwable {
        Proposal proposal = proposalRepository.findByTitleContaining(title).get(0);
        ProposalSubmission proposalSubmission = proposalSubmissionRepository.findBySubmits(proposal).get(0);
        ProposalPublication proposalPublication = proposalPublicationRepository.findByPublishes(proposalSubmission).get(0);

        Student student;
        if (studentRepository.exists(studentname))
            student = studentRepository.findOne(studentname);
        else {
            student = new Student();
            student.setUsername(studentname);
            student = studentRepository.save(student);
        }

        StudentOffer offer = new StudentOffer();
        offer.setAgent(student);
        offer.setTarget(proposalPublication);
        studentOfferRepository.save(offer);
    }

    @Then("^I have created a student assignment for student \"([^\"]*)\" and proposal titled \"([^\"]*)\"$")
    public void iHaveCreatedAStudentAssignmentForStudentAndProposalTitled(String userId, String title) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //result.andExpect(status().isCreated());

        String response = result
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

    }

    @Then("^check title editor user is logged$")
    public void checkTitleEditorUserIsLogged() throws Throwable {
        String response = result
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String creatorUri = JsonPath.read(response, "$._links.creator.href");

        result = mockMvc.perform(get(creatorUri)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href", containsString(this.currentUsername)));
    }

    @When("^I list proposals$")
    public void iListProposals() throws Throwable {
        result = mockMvc.perform(get("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
    }

    @When("^I list proposals publications$")
    public void iListProposalsPublications() throws Throwable {
        result = mockMvc.perform(get("/proposalPublications")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
    }

    @Then("^I get \"([^\"]*)\" proposals$")
    public void iGetProposals(int count) throws Throwable {
        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.proposals", hasSize(count)));
    }

    @Then("^I get \"([^\"]*)\" proposals publications$")
    public void iGetProposalsPublications(int count) throws Throwable {
        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.proposalPublications", hasSize(count)));
    }

    @Then("^I get proposals all with title containing \"([^\"]*)\"$")
    public void iGetProposals(String text) throws Throwable {
        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.proposals[*].title", everyItem(containsString(text))));
    }

    @When("^I list proposalSubmissions$")
    public void iListProposalSubmissions() throws Throwable {
        result = mockMvc.perform(get("/proposalSubmissions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(currentUsername, currentPassword)));
    }

    @Then("^I get \"([^\"]*)\" proposalSubmissions$")
    public void iGetProposalSubmissions(int count) throws Throwable {
        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.proposalSubmissions", hasSize(count)));
    }

    @And("^I get proposalSubmissions all with proposal title containing \"([^\"]*)\"$")
    public void iGetProposalSubmissionsAllWithProposalTitleContaining(String title) throws Throwable {
        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.proposalSubmissions[*].submits.title", everyItem(containsString(title))));
    }
}

