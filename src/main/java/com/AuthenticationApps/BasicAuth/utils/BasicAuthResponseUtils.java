package com.AuthenticationApps.BasicAuth.utils;

import com.AuthenticationApps.BasicAuth.entity.BasicAuthUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicAuthResponseUtils {
    private int statusCode;
    private String message;
    private BasicAuthUser user;
}
