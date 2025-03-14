package com.example.springbasicnewspeed.domain.user.entity;

import com.example.springbasicnewspeed.domain.auth.dto.AuthUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;
    private String userName;

    @Column(nullable = false)
    private String password;

    public User(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateUserName(String userName) {
        this.userName = userName;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
