package com.franco.appnotes.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franco.appnotes.users.dto.UserResponseDto;
import com.franco.appnotes.users.entities.User;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final ObjectMapper mapper;

    @Override
    public UserResponseDto save(User newUser)
    {
        checkIfUserExistsByUsername(newUser);
        var userSaved = userRepository.save(newUser);
        return mapper.convertValue(userSaved, UserResponseDto.class);
    }

    /**
     * @return a user dto search in the db.
     * @throws EntityNotFoundException if {@literal User} is {@literal null}.
     */
    @Override
    public UserResponseDto findByIdDto(UUID id)
    {
        User u = this.findById(id);
        return mapper.convertValue(u, UserResponseDto.class);
    }

    /**
     * @return the entity in the db with the given id.
     * @throws EntityNotFoundException if {@literal User} is {@literal null}.
     */
    @Override
    public User findById(UUID id)
    {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " +
                        id + " not found"));
    }

//    TODO: me deberia traer solamente los usuarios, sin embargo hace la consulta a la tabla notas
    @Override
    public List<UserResponseDto> findAll()
    {
        return userRepository.findAll()
                .stream().map(user ->
                        mapper.convertValue(user, UserResponseDto.class))
                .toList();
    }

    @Override
    public UserResponseDto findByUsername(String username)
    {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with username " + username + " not found"
                ));
        return mapper.convertValue(user, UserResponseDto.class);
    }

    @Override
    public void deleteById(UUID id)
    {
        userRepository.deleteById(id);
    }

    @Override
    public void checkIfUserExistsByUsername(User newUserToCreate)
    {
        if (userRepository.existsByUsername(newUserToCreate.getUsername())) {
            throw new EntityExistsException("El usuario con el nombre " +
                    newUserToCreate.getUsername() + " ya existe");
        }
    }

    @Override
    public void checkIfUserExistsById(User newUserToCreate)
    {
        if (!userRepository.existsById(newUserToCreate.getId())) {
            throw new EntityExistsException("El usuario con el nombre " +
                    newUserToCreate.getUsername() + " ya existe");
        }
    }
}
