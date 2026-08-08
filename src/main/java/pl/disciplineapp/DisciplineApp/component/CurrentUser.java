package pl.disciplineapp.DisciplineApp.component;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CurrentUser extends User {
    private pl.disciplineapp.DisciplineApp.model.User user;

    public CurrentUser(String username, String password,
                       Collection<? extends GrantedAuthority> authorities,
                       pl.disciplineapp.DisciplineApp.model.User user) {
        super(username, password, authorities);
        this.user = user;
    }
}
