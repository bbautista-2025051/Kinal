package com.kinalApplication.Kinal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estudiantes", indexes = {
        @Index(name = "idx_estudiante_email", columnList = "email")
})
public class Estudiante {

    @Id
    @Column(length = 254)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 100)
    private String nombreCompleto;

    private String avatarUrl;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean cuentaBloqueada = false;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int intentosFallidos = 0;

    private LocalDateTime bloqueadoHasta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ResultadoTest> resultados = new ArrayList<>();

    public Estudiante() {}

    public Estudiante(String email, String password, String nombreCompleto, Carrera carrera) {
        this.email = email;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.carrera = carrera;
    }

    // ── Getters y Setters ────────────────────────────────────────────────────
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String n) { this.nombreCompleto = n; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public boolean isCuentaBloqueada() { return cuentaBloqueada; }
    public void setCuentaBloqueada(boolean b) { this.cuentaBloqueada = b; }
    public int getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(int i) { this.intentosFallidos = i; }
    public LocalDateTime getBloqueadoHasta() { return bloqueadoHasta; }
    public void setBloqueadoHasta(LocalDateTime t) { this.bloqueadoHasta = t; }
    public Carrera getCarrera() { return carrera; }
    public void setCarrera(Carrera carrera) { this.carrera = carrera; }
    public List<ResultadoTest> getResultados() { return resultados; }
    public void setResultados(List<ResultadoTest> r) { this.resultados = r; }
}
