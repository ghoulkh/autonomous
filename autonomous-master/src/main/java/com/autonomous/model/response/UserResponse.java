package com.autonomous.model.response;

import com.autonomous.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserResponse {
    private String username;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdDate;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date lastModifiedDate;
    private List<AuthorityResponse> authorities;
    private String fullName;
    private String avaUrl;
    private String email;
    private User.Provider provider;
    private String createdBy;
    private Double ratingValue;
    private boolean isActive;
    public static UserResponse from(User user) {
        UserResponse result = new UserResponse();
        BeanUtils.copyProperties(user, result);
        if (user.getAuthorities() != null)
            result.setAuthorities(user.getAuthorities().stream()
                    .map(authority -> AuthorityResponse.from(authority))
                    .collect(Collectors.toList()));
        return result;
    }
}
