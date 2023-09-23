package com.franco.appnotes.controller;

import com.franco.appnotes.dto.UserDtoResponse;
import com.franco.appnotes.entity.User;
import com.franco.appnotes.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDtoResponse> getUserById(@RequestParam Long id)
    {
        return ResponseEntity.ok(userService.findByIdDto(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers()
    {
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(@RequestParam Long id)
    {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
