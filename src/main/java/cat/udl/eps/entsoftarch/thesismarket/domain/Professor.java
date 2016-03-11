package cat.udl.eps.entsoftarch.thesismarket.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.Entity;
import java.util.Collection;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
public class Professor extends Proponent {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_PROFESSOR, ROLE_PROPONENT");
    }
}
