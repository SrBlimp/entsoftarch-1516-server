package cat.udl.eps.entsoftarch.thesismarket.config;

import cat.udl.eps.entsoftarch.thesismarket.domain.Coordinator;
import cat.udl.eps.entsoftarch.thesismarket.domain.Professor;
import cat.udl.eps.entsoftarch.thesismarket.domain.Student;
import cat.udl.eps.entsoftarch.thesismarket.domain.User;
import cat.udl.eps.entsoftarch.thesismarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import javax.inject.Inject;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Configuration
public class AuthenticationTestConfig extends GlobalAuthenticationConfigurerAdapter {

    @Inject UserDetailsContextMapper userDetailsContextMapper;
    @Autowired UserRepository userRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("admin").password("password").roles("ADMIN").and()
                .withUser("coordinator1").password("password").roles("COORDINATOR");

        User coordinator = new Coordinator();
        coordinator.setUsername("coordinator1");
        coordinator.setEmail("coordinator1@thesismarket");
        userRepository.save(coordinator);
        User professor = new Professor();
        professor.setUsername("professor1");
        professor.setEmail("professor1@thesismarket");
        userRepository.save(professor);
        User student = new Student();
        student.setUsername("student1");
        student.setEmail("student1@thesismarket");
        userRepository.save(student);

        auth
            .ldapAuthentication()
                .userSearchFilter("uid={0}")
                .contextSource().ldif("classpath:ldap-test.ldif").root("dc=UdL,dc=es")
                .and()
            .userDetailsContextMapper(userDetailsContextMapper);
    }
}