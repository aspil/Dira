package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.security.JwtHelper;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeepAliveController {
    private final JwtHelper jwtHelper;

    public KeepAliveController(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @PostMapping("keepalive")
    public ResponseEntity<Void> keepalive(@RequestHeader("Authorization") String jwtToken) {
        if (!jwtHelper.validateJwtToken(jwtToken)) {
            throw new JwtException("JWT token provided is not a valid one");
        }

        jwtHelper.refreshExpirationTime(jwtToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }
}
