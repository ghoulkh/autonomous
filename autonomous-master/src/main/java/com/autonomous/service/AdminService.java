package com.autonomous.service;

import com.autonomous.exception.AutonomousException;
import com.autonomous.repository.AuthorityRepository;
import com.autonomous.repository.UserRepository;
import com.autonomous.entity.Authority;
import com.autonomous.entity.User;
import com.autonomous.model.error.ErrorCode;
import com.autonomous.model.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    public void addAuthorityWithUsername(String username) {
        User userToAdd = userRepository.findById(username).orElseThrow(() ->
                new AutonomousException(ErrorCode.USER_NOT_FOUND));
        Authority authority = new Authority();
        authority.setRole(Authority.Role.ROLE_ADMIN);
        authority.setUser(userToAdd);
        authorityRepository.save(authority);
    }

}
