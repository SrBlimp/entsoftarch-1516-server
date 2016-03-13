package cat.udl.eps.entsoftarch.thesismarket.security;

import cat.udl.eps.entsoftarch.thesismarket.domain.Coordinator;
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
                .withUser("coordinator").password("password").roles("COORDINATOR");

        User coordinator = new Coordinator();
        coordinator.setUsername("coordinator");
        userRepository.save(coordinator);

        auth
            .ldapAuthentication()
                .userSearchFilter("uid={0}")
                .contextSource().ldif("classpath:ldap-test.ldif").root("dc=UdL,dc=es")
                .and()
            .userDetailsContextMapper(userDetailsContextMapper);
    }
}