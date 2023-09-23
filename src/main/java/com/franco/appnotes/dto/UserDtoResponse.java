package com.franco.appnotes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * DTO for {@link com.franco.appnotes.entity.User}
 */
@AllArgsConstructor
@Getter
@ToString
public class UserDtoResponse implements Serializable {
    private final Long id;
    private final String username;
    private final String role;
}