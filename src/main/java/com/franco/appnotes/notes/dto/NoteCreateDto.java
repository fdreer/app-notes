package com.franco.appnotes.notes.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record NoteCreateDto(
        String title,
        String content,
        boolean important,
        UUID userId
) {
}
