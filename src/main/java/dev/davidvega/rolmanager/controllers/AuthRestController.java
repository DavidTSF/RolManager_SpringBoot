package dev.davidvega.hogwarts.controllers;

import dev.davidvega.hogwarts.dtos.LoginDTO;
import dev.davidvega.hogwarts.dtos.RegisterDTO;
import dev.davidvega.hogwarts.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hogwarts")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.login(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO dto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.register(dto));
    }
}