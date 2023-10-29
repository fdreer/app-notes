package com.franco.appnotes.notes.dto;

import lombok.Builder;

@Builder
public record NoteUpdateDto(
        String title,
        String content,
        boolean important
) {
}
