package com.franco.appnotes.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franco.appnotes.dto.UserDtoResponse;
import com.franco.appnotes.entity.User;
import com.franco.appnotes.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final ObjectMapper mapper;

    public UserDtoResponse save(User newUser)
    {
        checkIfUserExistsByUsername(newUser);
        var userSaved = userRepository.save(newUser);
        return mapper.convertValue(userSaved, UserDtoResponse.class);
    }

    /**
     * @return a user dto search in the db.
     * @throws EntityNotFoundException if {@literal User} is {@literal null}.
     */
    public UserDtoResponse findByIdDto(Long id)
    {
        User u = this.findById(id);
        return mapper.convertValue(u, UserDtoResponse.class);
    }

    /**
     * @return the entity in the db with the given id.
     * @throws EntityNotFoundException if {@literal User} is {@literal null}.
     */
    public User findById(Long id)
    {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    public List<User> findAll()
    {
        return userRepository.findAll();
    }

    public UserDtoResponse findByUsername(String username)
    {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username " + username + " not found"));
        return mapper.convertValue(user, UserDtoResponse.class);
    }

    public void deleteById(Long id)
    {
        userRepository.deleteById(id);
    }

//    public void updateUsername(String newUsername, Long id)
//    {
//        User userToUpdate = this.findById(id);
//        userRepository.updateUsernameById(newUsername, id);
//    }

    private void checkIfUserExistsByUsername(User newUserToCreate)
    {
        if (userRepository.existsByUsername(newUserToCreate.getUsername())) {
            throw new EntityExistsException("El usuario con el nombre " + newUserToCreate.getUsername() + " ya existe");
        }
    }

    private void checkIfUserExistsById(User newUserToCreate)
    {
        if (!userRepository.existsById(newUserToCreate.getId())) {
            throw new EntityExistsException("El usuario con el nombre " + newUserToCreate.getUsername() + " ya existe");
        }
    }
}
