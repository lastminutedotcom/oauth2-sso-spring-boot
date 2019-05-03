package com.lastminute.fderatedsso.javaoauth2server;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;

@FrameworkEndpoint
public class UserInfoEndPoint {

    @GetMapping("/user-info")
    public ResponseEntity key(JwtAuthenticationToken principal) {
        String userName = String.valueOf(principal.getToken().getClaims().get("user_name"));
        return ResponseEntity.ok(new UserInfo(userName));
    }
}

class UserInfo {
    private final String userName;

    UserInfo(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}