package com.autonomous.service;

import com.autonomous.repository.AuthorityRepository;
import com.autonomous.repository.UserRepository;
import com.autonomous.entity.Authority;
import com.autonomous.entity.User;
import com.autonomous.exception.BannedUserException;
import com.autonomous.exception.AutonomousException;
import com.autonomous.model.error.ErrorCode;
import com.autonomous.model.request.RegisterUserRequest;
import com.autonomous.model.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService  {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    public void registrationUser(RegisterUserRequest request) {
        if (userRepository.findById(request.getUsername()).isPresent())
            throw new AutonomousException(ErrorCode.CONFLICT_USERNAME);
        User userToSave = request.toUser();
        userToSave.setPassword(passwordEncoder.encode(request.getPassword()));

        User result = userRepository.save(userToSave);
        Authority authority = new Authority();
        authority.setUser(result);
        authority.setRole(Authority.Role.ROLE_USER);
        authorityRepository.save(authority);
        UserResponse.from(result);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findById(username);
    }

    public void processOAuthPostLogin(String username, String name, String avaUrl, User.Provider provider) {
        Optional<User> existUser = userRepository.findById(username);

        if (existUser.isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setProvider(provider);
            user.setActive(true);
            user.setAvaUrl(avaUrl);
            user.setCreatedBy(username);
            user.setFullName(name);
            user.setProvider(User.Provider.LOCAL);
            userRepository.save(user);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(username);
        if (!user.get().isActive()) {
            throw new BannedUserException("Banded perform action!");
        }
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        List<String> roles = user.get().getAuthorities().stream().map(authority ->
                authority.getRole().getValue()).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                user.get().getPassword() != null ? user.get().getPassword() : "",
                getAuthorities(roles));
    }

    private List<SimpleGrantedAuthority> getAuthorities(List<String> roles) {
        List<SimpleGrantedAuthority> result =  new ArrayList<>();
        for (int i = 0; i < roles.size(); i ++) {
            result.add(new SimpleGrantedAuthority(roles.get(i)));
        }
        return result;
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
