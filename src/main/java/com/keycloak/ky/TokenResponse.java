package com.keycloak.ky;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class TokenResponse {
    private String token;

}
