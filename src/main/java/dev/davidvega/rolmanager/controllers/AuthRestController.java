package dev.davidvega.rolmanager.controllers;


import dev.davidvega.rolmanager.dtos.LoginDTO;
import dev.davidvega.rolmanager.dtos.RegisterDTO;
import dev.davidvega.rolmanager.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rolmanager/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthService service;
    public final static String AUTH_ROOT_URL  = "/rolmanager/auth";
    public final static String AUTH_REGISTER_URL  = "/register";
    public final static String AUTH_LOGIN_URL = "/login";

    @PostMapping(AUTH_LOGIN_URL)
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.login(dto));
    }

    @PostMapping(AUTH_REGISTER_URL)
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.register(dto));
    }
}