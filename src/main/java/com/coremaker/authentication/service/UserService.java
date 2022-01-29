package com.coremaker.authentication.service;

import com.coremaker.authentication.UserRepository;
import com.coremaker.authentication.entity.User;
import com.coremaker.authentication.model.SignUpModel;
import com.coremaker.authentication.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent())
        {
            return new UserPrincipal(user.get());
        }
        throw new UsernameNotFoundException(username);
    }

    public ResponseEntity<Object> signUp(final SignUpModel signUpModel) {
        if (userRepository.findByEmail(signUpModel.getEmail()).isPresent()) {
            return new ResponseEntity<>("Mail already used!", HttpStatus.BAD_REQUEST);
        }

        signUpModel.setPassword(passwordEncoder.encode(signUpModel.getPassword()));
        User user = new User(signUpModel);
        userRepository.save(user);

        return new ResponseEntity<>("User created successfully", HttpStatus.OK);
    }

    public ResponseEntity<Object> getUserDetails(final String username) {
        return new ResponseEntity<>(userRepository.findByName(username), HttpStatus.OK);
    }

}
