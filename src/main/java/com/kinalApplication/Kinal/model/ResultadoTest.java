package com.kinalApplication.Kinal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resultados")
public class ResultadoTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estudiante_email", nullable = false)
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "lectura_id", nullable = false)
    private Lectura lectura;

    private LocalDateTime fecha;

    private int aciertos;

    private int totalPreguntas;

    private double nota; // sobre 100

    // Constructores
    public ResultadoTest() {}

    public ResultadoTest(Estudiante estudiante, Lectura lectura, int aciertos, int totalPreguntas, double nota) {
        this.estudiante = estudiante;
        this.lectura = lectura;
        this.fecha = LocalDateTime.now();
        this.aciertos = aciertos;
        this.totalPreguntas = totalPreguntas;
        this.nota = nota;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    public Lectura getLectura() { return lectura; }
    public void setLectura(Lectura lectura) { this.lectura = lectura; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public int getAciertos() { return aciertos; }
    public void setAciertos(int aciertos) { this.aciertos = aciertos; }
    public int getTotalPreguntas() { return totalPreguntas; }
    public void setTotalPreguntas(int totalPreguntas) { this.totalPreguntas = totalPreguntas; }
    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
}