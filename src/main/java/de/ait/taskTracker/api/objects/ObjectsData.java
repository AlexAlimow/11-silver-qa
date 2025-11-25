package de.ait.taskTracker.api.objects;

import de.ait.taskTracker.dto.AuthRequestDto;

import static de.ait.taskTracker.config.AppConfigApi.getProperty;

//the class stores different types of data for different objects
public class ObjectsData {

    public static AuthRequestDto requestDto = AuthRequestDto.builder()
            .email(getProperty("default.email"))
            .password(getProperty("default.password"))
            .build();
}
