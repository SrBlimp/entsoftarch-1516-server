package cat.udl.eps.entsoftarch.thesismarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Configuration
@Profile("heroku")
public class AuthenticationHerokuConfig extends GlobalAuthenticationConfigurerAdapter {

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        encoder.setEncodeHashAsBase64(true);

        auth
            .inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("admin").password("18LcJuI3xeanShlrg/oherDmVf4=").roles("ADMIN").and()
                .withUser("coordinator1").password("18LcJuI3xeanShlrg/oherDmVf4=").roles("COORDINATOR").and()
                .withUser("professor1").password("18LcJuI3xeanShlrg/oherDmVf4=").roles("PROFESSOR", "PROPONENT").and()
                .withUser("student1").password("18LcJuI3xeanShlrg/oherDmVf4=").roles("STUDENT", "PROFESSOR");
    }
}