package org.library.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Document(collection = "users")
public class User {

    String ROLE_PREFIX = "ROLE_";

    @Id
    private String id;
    @Column(nullable = false, length = 120, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false, unique = true)
    private String encryptedPassword;
    @Column(nullable = false, unique = true)
    private String role;

    public User(String email, String password, String userId, String encryptedPassword, String role) {
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.encryptedPassword = encryptedPassword;
        this.role = role;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
        return list;
    }

}
