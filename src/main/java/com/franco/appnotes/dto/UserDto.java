package com.franco.appnotes.dto;

import com.franco.appnotes.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link User}
 */
@Builder
@Getter
@ToString
public class UserDto implements Serializable {
    private final Long id;
    private final String username;
//    private final Set<NoteDto> notes;
}