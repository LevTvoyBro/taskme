package com.blm.taskme.services.mappers;

import com.blm.taskme.api.v1.request.RegistrationRequest;
import com.blm.taskme.api.v1.response.UserProfileResponse;
import com.blm.taskme.domains.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    void mergeIntoUser(@MappingTarget User user, RegistrationRequest registrationRequest);

    UserProfileResponse toProfileResponse(User user);
}
