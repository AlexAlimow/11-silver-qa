package de.ait.taskTracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDto {

    private String accessToken;
    private String refreshToken;
}
