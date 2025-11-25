package de.ait.taskTracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequestDto {

    private String email;
    private String password;
}


