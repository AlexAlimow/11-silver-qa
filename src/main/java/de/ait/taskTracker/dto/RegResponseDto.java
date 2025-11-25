package de.ait.taskTracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegResponseDto {

    private String id;
    private String email;
    private String role;
    private boolean confirmationResent;
    private String confirmationStatus;
}
