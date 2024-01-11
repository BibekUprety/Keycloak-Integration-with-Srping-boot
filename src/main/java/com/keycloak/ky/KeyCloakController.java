package com.keycloak.ky;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KeyCloakController {

    private final KeyCloakAddUser keyCloakController;
    @GetMapping("/login")
    public String login() {
        return "Welcome to login screen";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/save")
    public void set(@RequestBody UserDTO userDTO){
        keyCloakController.setUser(userDTO);
    }
}
