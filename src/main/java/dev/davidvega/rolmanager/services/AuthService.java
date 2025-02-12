package dev.davidvega.rolmanager.services;


import dev.davidvega.rolmanager.dtos.AuthDTO;
import dev.davidvega.rolmanager.dtos.LoginDTO;
import dev.davidvega.rolmanager.dtos.RegisterDTO;

public interface AuthService {
    AuthDTO login(LoginDTO login) throws Exception;
    AuthDTO register(RegisterDTO register) throws Exception;
}
