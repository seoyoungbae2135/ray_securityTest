package com.example.securityTest.service;

import com.example.securityTest.dto.UserDto;
import com.example.securityTest.entity.User;
import com.example.securityTest.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService { //import후 빨간줄에 커서위치후 아래@Override 생성

    private final UserRepo userRepo;

    public void saveUser(UserDto userDto, PasswordEncoder passwordEncoder){
        User user = User.createUser(userDto,passwordEncoder);
        userRepo.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUserId(username);
        if(user ==null){
            throw new UsernameNotFoundException (username);
        }
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUserId())
                .password(user.getUserPw())
                .roles(user.getRole())
                .build();
    }
}
