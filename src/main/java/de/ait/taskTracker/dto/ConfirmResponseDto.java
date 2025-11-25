package de.ait.taskTracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmResponseDto {

    private String email;
    private String role;
    private boolean confirmationResent;
}
