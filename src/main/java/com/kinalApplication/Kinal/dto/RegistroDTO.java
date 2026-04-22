package com.kinalApplication.Kinal.dto;

import jakarta.validation.constraints.*;

public class RegistroDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Pattern(regexp = "^[\\p{L} .'-]+$",
             message = "El nombre solo puede contener letras, espacios, puntos y guiones")
    private String nombreCompleto;

    @Email(message = "Formato de correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    @Size(max = 254, message = "El correo es demasiado largo")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 128, message = "La contraseña debe tener entre 8 y 128 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
             message = "La contraseña debe tener al menos una mayúscula, una minúscula y un número")
    private String password;

    @NotNull(message = "Debe seleccionar una carrera")
    @Positive(message = "Carrera inválida")
    private Long carreraId;

    // Getters y Setters
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Long getCarreraId() { return carreraId; }
    public void setCarreraId(Long carreraId) { this.carreraId = carreraId; }
}
