package com.kinalApplication.Kinal.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "opciones")
public class Opcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String texto;

    @ManyToOne
    @JoinColumn(name = "pregunta_id", nullable = false)
    @JsonIgnore
    private Pregunta pregunta;

    // No almacenamos boolean correcto aquí, porque la correcta se referencia desde Pregunta.opcionCorrecta

    // Constructores
    public Opcion() {}

    public Opcion(String texto, Pregunta pregunta) {
        this.texto = texto;
        this.pregunta = pregunta;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public Pregunta getPregunta() { return pregunta; }
    public void setPregunta(Pregunta pregunta) { this.pregunta = pregunta; }
}