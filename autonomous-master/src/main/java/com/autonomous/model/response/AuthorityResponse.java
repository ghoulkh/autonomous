package com.autonomous.model.response;

import com.autonomous.entity.Authority;
import lombok.Data;
import org.springframework.beans.BeanUtils;


@Data
public class AuthorityResponse {
    private Long id;
    private Authority.Role role;

    public static AuthorityResponse from(Authority authority) {
        if (authority == null) return null;
        AuthorityResponse response = new AuthorityResponse();
        BeanUtils.copyProperties(authority, response);
        return response;
    }
}
