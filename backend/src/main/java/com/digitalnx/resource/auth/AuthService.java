package com.digitalnx.resource.auth;

import com.digitalnx.resource.auth.user.User;
import com.digitalnx.resource.auth.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// TODO
@Component
public class AuthService implements UserDetailsService {
    private UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserRepository userRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void clearData() {
        this.userRepo.deleteAll();
    }

    public User saveUser(User user) {
        log.info("[User] Adding a new user to database.");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public User getUser(String username) {
        log.info("[User] Fetching a user from database.");
        return userRepo.findByUsername(username);
    }

    public List<User> getUsers() {
        log.info("[User] Fetching all users from database.");
        return userRepo.findAll();
    }

    public void addRoleToUser(String username) {
        log.info("[User] Assigning new role to a user.");
        User user = userRepo.findByUsername(username);
        userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

}
