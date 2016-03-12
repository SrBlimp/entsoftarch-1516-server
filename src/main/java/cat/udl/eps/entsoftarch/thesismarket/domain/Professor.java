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

    private boolean isCoordinator = false;

    public boolean isCoordinator() { return isCoordinator; }

    public void setCoordinator(boolean coordinator) { isCoordinator = coordinator; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (isCoordinator)
            return AuthorityUtils.commaSeparatedStringToAuthorityList(
                    "ROLE_PROFESSOR, ROLE_PROPONENT, ROLE_COORDINATOR");
        else
            return AuthorityUtils.commaSeparatedStringToAuthorityList(
                    "ROLE_PROFESSOR, ROLE_PROPONENT");
    }
}
