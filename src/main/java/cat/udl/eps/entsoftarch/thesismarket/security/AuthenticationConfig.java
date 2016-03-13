package cat.udl.eps.entsoftarch.thesismarket.security;

import cat.udl.eps.entsoftarch.thesismarket.domain.Coordinator;
import cat.udl.eps.entsoftarch.thesismarket.domain.Professor;
import cat.udl.eps.entsoftarch.thesismarket.domain.Student;
import cat.udl.eps.entsoftarch.thesismarket.domain.User;
import cat.udl.eps.entsoftarch.thesismarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Configuration
@Profile("!UdL-Deployment")
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired UserRepository userRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        encoder.setEncodeHashAsBase64(true);

        auth
                .inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("admin").password("18LcJuI3xeanShlrg/oherDmVf4=").roles("ADMIN").and()
                .withUser("coordinator").password("18LcJuI3xeanShlrg/oherDmVf4=").roles("COORDINATOR").and()
                .withUser("professor1").password("18LcJuI3xeanShlrg/oherDmVf4=").roles("PROFESSOR", "PROPONENT").and()
                .withUser("student1").password("18LcJuI3xeanShlrg/oherDmVf4=").roles("STUDENT", "PROFESSOR");

        User coordinator = new Coordinator();
        coordinator.setUsername("coordinator");
        userRepository.save(coordinator);
        User professor = new Professor();
        professor.setUsername("professor1");
        userRepository.save(professor);
        User student = new Student();
        student.setUsername("student1");
        userRepository.save(student);
    }
}