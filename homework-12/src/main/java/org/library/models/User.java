package org.library.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;

@Data
@Document(collection = "users")
public class User {

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

    public User(String email, String password, String userId, String encryptedPassword) {
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.encryptedPassword = encryptedPassword;
    }

}
