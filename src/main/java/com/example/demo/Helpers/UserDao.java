package com.example.demo.Helpers;

import com.example.demo.Entities.User;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDao {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public boolean registerUser(AuthenticationRequest authRequest){
        if(userRepository.findByEmail(authRequest.getEmail()) == null){
            User user = new User();
            user.setEmail(authRequest.getEmail());
            user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
            userRepository.save(user);
            return true;
        }else{
            return false;
        }
    }
}
