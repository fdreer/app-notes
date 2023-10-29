package com.franco.appnotes.users;

import com.franco.appnotes.users.dto.UserResponseDto;
import com.franco.appnotes.users.entities.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    UserResponseDto save(User newUser);
    UserResponseDto findByIdDto(UUID id);
    User findById(UUID id);
    List<UserResponseDto> findAll();
    UserResponseDto findByUsername(String username);
    void deleteById(UUID id);
    void checkIfUserExistsByUsername(User newUserToCreate);
    void checkIfUserExistsById(User newUserToCreate);
}
