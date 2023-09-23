package com.franco.appnotes.dto;

import lombok.Builder;

@Builder
public record NoteDtoRequest(
        String title,
        String content,
        boolean important,
        boolean completed,
        Long userId
) {
}
