package cat.udl.eps.entsoftarch.thesismarket.config;

import cat.udl.eps.entsoftarch.thesismarket.domain.Professor;
import cat.udl.eps.entsoftarch.thesismarket.domain.Student;
import cat.udl.eps.entsoftarch.thesismarket.domain.User;
import cat.udl.eps.entsoftarch.thesismarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import java.util.Collection;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .authorizeRequests()
                .antMatchers("/users*/**", "/professors*/**", "/students*/**",
                        "/organizations*/**", "/coordinators*/**").authenticated()
                //ProposalWithdrawal
                .antMatchers(HttpMethod.PUT, "/proposalWithdrawals*/**").authenticated()
                .antMatchers(HttpMethod.POST, "/proposalWithdrawals*/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/proposalWithdrawals*/**").authenticated()
                .antMatchers(HttpMethod.PATCH, "/proposalWithdrawals*/**").authenticated()
                //PublishProposal
                .antMatchers(HttpMethod.PUT, "/proposalPublications*/**").authenticated()
                .antMatchers(HttpMethod.POST, "/proposalPublications*/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/proposalPublications*/**").authenticated()
                .antMatchers(HttpMethod.PATCH, "/proposalPublications*/**").authenticated()
                //Comment
                .antMatchers(HttpMethod.PUT, "/comments*/**").authenticated()
                .antMatchers(HttpMethod.POST, "/comments*/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/comments*/**").authenticated()
                .antMatchers(HttpMethod.PATCH, "/comments*/**").authenticated()
                //Proposal
                .antMatchers(HttpMethod.GET, "/proposals/search/**").authenticated()
                .antMatchers(HttpMethod.GET, "/proposals*/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/proposals*/**").authenticated()
                .antMatchers(HttpMethod.POST, "/proposals*/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/proposals*/**").authenticated()
                .antMatchers(HttpMethod.PATCH, "/proposals*/**").authenticated()
                //ProposalSubmission
                .antMatchers(HttpMethod.POST, "/proposalSubmissions*/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/proposalSubmissions*/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/proposalSubmissions*/**").authenticated()
                .antMatchers(HttpMethod.PATCH, "/proposalSubmissions*/**").authenticated()
                .anyRequest().permitAll()
                .and()
            .httpBasic()
                .realmName("ThesisMarketAPI")
                .and()
            .addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
            .csrf()
                .disable();
    }

    @Bean
    public UserDetailsContextMapper userDetailsContextMapper() {
        return new CustomUserDetailsContextMapper();
    }

    public class CustomUserDetailsContextMapper extends LdapUserDetailsMapper {

        @Autowired private UserRepository userRepository;

        @Override
        public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
            User user = userRepository.findOne(username);
            if (user != null)
                return user;

            String employeeType = ctx.getStringAttribute("employeeType");
            if (employeeType.equals("PDIPAS"))
                user = new Professor();
            else
                user = new Student();
            user.setUsername(username);
            user.setEmail(ctx.getStringAttribute("mail"));

            return userRepository.save(user);
        }
    }
}