package com.example.ecommerce.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Users;
import com.example.ecommerce.repository.UserRepository;

@Service
public class MyUserDetailServices implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return User.builder()
                    .username(user.get().getUsername())
                    .password(user.get().getPassword())
                    .roles(getRoles(user.get()))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public String[] getRoles(Users user){
        if(user.getRole() == null){
            return new String[]{"USER"};
        } else {
            return user.getRole().split(",");
        }
    }
}
