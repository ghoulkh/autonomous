package com.autonomous.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SetAuthorityRequest {
    @NotBlank(message = "username is required!")
    private String username;
}
