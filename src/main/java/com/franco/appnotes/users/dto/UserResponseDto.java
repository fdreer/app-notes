package com.franco.appnotes.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
public class UserResponseDto implements Serializable {
    private final UUID id;
    private final String username;
    private final String role;
}