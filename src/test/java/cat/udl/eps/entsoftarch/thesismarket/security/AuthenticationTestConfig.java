package cat.udl.eps.entsoftarch.thesismarket.security;

import cat.udl.eps.entsoftarch.thesismarket.domain.Professor;
import cat.udl.eps.entsoftarch.thesismarket.domain.Student;
import cat.udl.eps.entsoftarch.thesismarket.domain.User;
import cat.udl.eps.entsoftarch.thesismarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Configuration
public class AuthenticationTestConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired UserRepository userRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("admin").password("password").roles("ADMIN").and()
                .withUser("coordinator").password("password").roles("COORDINATOR").and()
                .withUser("professor1").password("password").roles("PROFESSOR", "PROPONENT").and()
                .withUser("student1").password("password").roles("STUDENT", "PROPONENT");

        User professor = new Professor();
        professor.setUsername("professor1");
        userRepository.save(professor);
        User student = new Student();
        professor.setUsername("student1");
        userRepository.save(student);
    }
}