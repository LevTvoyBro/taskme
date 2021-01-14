package com.blm.taskme.services.implementation;

import com.blm.taskme.domains.User;
import com.blm.taskme.repository.UserRepository;
import com.blm.taskme.services.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DefaultUserResolver {
    private final UserRepository userRepository;

    @Autowired
    public DefaultUserResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(propagation = Propagation.NESTED, readOnly = true)
    public User getUser(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with this email not found"));
    }
}
