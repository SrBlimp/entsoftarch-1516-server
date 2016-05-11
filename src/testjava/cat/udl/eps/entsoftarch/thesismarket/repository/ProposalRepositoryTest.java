package cat.udl.eps.entsoftarch.thesismarket.repository;

import cat.udl.eps.entsoftarch.thesismarket.ThesismarketApiApplication;
import cat.udl.eps.entsoftarch.thesismarket.domain.Proponent;
import cat.udl.eps.entsoftarch.thesismarket.domain.Proposal;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ThesismarketApiApplication.class})
@WebAppConfiguration
public class ProposalRepositoryTest {

    @Autowired private WebApplicationContext wac;
    @Autowired private ProposalRepository proposalRepository;
    @Autowired private ProponentRepository proponentRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private ProfessorRepository professorRepository;

    private MockMvc mockMvc;

    private static boolean setUpIsDone = false;

    private void createProposalWithTitleBy(String title, String username) {
        Proposal proposal = new Proposal();
        proposal.setTitle(title);
        Proponent proponent = proponentRepository.findOne(username);
        proposal.setCreator(proponent);
        proposalRepository.save(proposal);
    }

    private ResultActions listProposals(String username, String password) throws Exception {
        return mockMvc.perform(get("/proposals")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(httpBasic(username, password)));
    }

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
        if (!setUpIsDone) {
            createProposalWithTitleBy("Professor1 proposal", "professor1");
            createProposalWithTitleBy("Student1 proposal", "student1");
            setUpIsDone = true;
        }
    }

    @Test
    public void findAllAsProfessor1() throws Exception {
        ResultActions result = listProposals("professor1", "password");

        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.proposals", hasSize(1)));
    }

    @Test
    public void findAllAsStudent1() throws Exception {

        ResultActions result = listProposals("student1", "password");

        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.proposals", hasSize(1)));
    }

    @Test
    public void findAllAsAdmin() throws Exception {

        ResultActions result = listProposals("admin", "password");

        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.proposals", hasSize(2)));
    }
}