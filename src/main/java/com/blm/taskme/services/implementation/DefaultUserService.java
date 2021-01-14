package com.blm.taskme.services.implementation;

import com.blm.taskme.api.v1.request.RegistrationRequest;
import com.blm.taskme.api.v1.response.UserProfileResponse;
import com.blm.taskme.domains.User;
import com.blm.taskme.domains.enums.UserStatus;
import com.blm.taskme.repository.UserRepository;
import com.blm.taskme.services.UserService;
import com.blm.taskme.services.exception.RegistrationException;
import com.blm.taskme.services.exception.UserNotFoundException;
import com.blm.taskme.services.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public DefaultUserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public void register(RegistrationRequest request) throws RegistrationException {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("User with this email already exists");
        }

        User user = new User();

        userMapper.mergeIntoUser(user, request);

        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);
    }

    @Transactional
    public UserProfileResponse getProfile(String username) throws UserNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return userMapper.toProfileResponse(user);
    }
}
