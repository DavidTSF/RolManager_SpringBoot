package dev.davidvega.rolmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {
    @NotNull(message = "El username es obligatorio")
    @NotBlank(message = "El username no puede estar vacío")
    String username;

    @NotNull(message = "La contraseña es obligatorio")
    @NotBlank(message = "La contraseña no puede estar vacía")
    String password;
}