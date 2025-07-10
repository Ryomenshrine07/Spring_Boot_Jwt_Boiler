package com.example.demo.Controllers;

import com.example.demo.Helpers.AuthenticationRequest;
import com.example.demo.Helpers.AuthenticationResponse;
import com.example.demo.Helpers.UserDao;
import com.example.demo.Service.MyUserDetailService;
import com.example.demo.Utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    MyUserDetailService myUserDetailService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDao userDao;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authRequest) throws Exception {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),authRequest.getPassword()
                    )
            );
        }catch (Exception e){
            throw new Exception("Incorrect Username Or Password");
        }
        final UserDetails userDetails = myUserDetailService.loadUserByUsername(authRequest.getEmail());
        final String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody AuthenticationRequest authRequest){
        return ResponseEntity.ok(userDao.registerUser(authRequest));
    }
    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello, this is a secured endpoint!");
    }
}
