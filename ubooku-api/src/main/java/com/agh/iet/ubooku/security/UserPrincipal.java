package com.agh.iet.ubooku.security;

import com.agh.iet.ubooku.model.auth.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This is the class whose
 * instances will be returned
 * from our custom UserDetailsService.
 * Spring Security will use the
 * information stored in the UserPrincipal
 * object to perform authentication and authorization.
 */
@Data
@AllArgsConstructor
public class UserPrincipal implements UserDetails, OAuth2User {

    private String id;
    private String firstName;
    private String lastName;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private Map<String, Object> attributes;




    public static UserPrincipal create(User user, @Nullable Map<String, Object> attributes) {
        List<GrantedAuthority> authorities = user.getRoles()
                .stream().map(role ->
                        new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                attributes
        );
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return id;
    }
}
