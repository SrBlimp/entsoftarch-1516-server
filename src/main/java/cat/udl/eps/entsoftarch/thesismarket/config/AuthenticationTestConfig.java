package cat.udl.eps.entsoftarch.thesismarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Configuration
@Profile("!heroku, !UdLDeploy")
public class AuthenticationTestConfig extends GlobalAuthenticationConfigurerAdapter {

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("admin").password("password").roles("ADMIN").and()
                .withUser("coordinator1").password("password").roles("COORDINATOR").and()
                .withUser("professor1").password("password").roles("PROPONENT", "PROFESSOR").and()
                .withUser("student1").password("password").roles("PROPONENT", "STUDENT");
    }
}