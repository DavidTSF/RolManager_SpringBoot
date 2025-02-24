package dev.davidvega.rolmanager.services;


import dev.davidvega.rolmanager.dto.AuthDTO;
import dev.davidvega.rolmanager.dto.LoginDTO;
import dev.davidvega.rolmanager.dto.RegisterDTO;

public interface AuthService {
    AuthDTO login(LoginDTO login) throws Exception;
    AuthDTO register(RegisterDTO register) throws Exception;
}
