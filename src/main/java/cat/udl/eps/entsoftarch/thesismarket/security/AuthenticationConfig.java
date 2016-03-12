package cat.udl.eps.entsoftarch.thesismarket.security;

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
@Profile("!UdL-Deployment")
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

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
        auth
            .ldapAuthentication()
                .userSearchFilter("uid={0}")
                .contextSource().ldif("classpath:ldap.ldif").root("dc=UdL,dc=es")
                .and()
            .userDetailsContextMapper(userDetailsContextMapper);
    }
}