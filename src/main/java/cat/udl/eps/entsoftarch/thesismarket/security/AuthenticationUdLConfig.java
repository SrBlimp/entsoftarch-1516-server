package cat.udl.eps.entsoftarch.thesismarket.security;

import cat.udl.eps.entsoftarch.thesismarket.domain.Coordinator;
import cat.udl.eps.entsoftarch.thesismarket.domain.User;
import cat.udl.eps.entsoftarch.thesismarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import javax.inject.Inject;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Configuration
@Profile("UdL-Deployment")
public class AuthenticationUdLConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired UserRepository userRepository;
    @Inject UserDetailsContextMapper userDetailsContextMapper;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        encoder.setEncodeHashAsBase64(true);

        auth
            .inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("admin").password("18LcJuI3xeanShlrg/oherDmVf4=").roles("ADMIN").and()
                .withUser("coordinator").password("18LcJuI3xeanShlrg/oherDmVf4=").roles("COORDINATOR");

        User coordinator = new Coordinator();
        coordinator.setUsername("coordinator");
        userRepository.save(coordinator);

        auth
            .ldapAuthentication()
                .userSearchFilter("uid={0}")
                .contextSource().url("ldap://dir3.udl.net:389/dc=UdL,dc=es")
                .and()
            .userDetailsContextMapper(userDetailsContextMapper);
    }
}