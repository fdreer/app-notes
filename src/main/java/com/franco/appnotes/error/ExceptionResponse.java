package com.franco.appnotes.error;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class ExceptionResponse {
    private LocalDateTime timestamp;
    private int status;
    private String message;
}
