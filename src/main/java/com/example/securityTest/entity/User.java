package com.example.securityTest.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import com.example.securityTest.dto.UserDto;
@Entity
@Setter
@Getter


public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId;

    @Column
    private String userPw;

    @Column
    private String role;

    public static User createUser( UserDto userDto , PasswordEncoder passwordEncoder){
        User user = new User();
        user.setUserId(userDto.getUserId());
        String pw = passwordEncoder.encode( userDto.getUserPw());
        user.setUserPw(pw);
        user.setRole("ADMIN");
        return user;
    }
}
