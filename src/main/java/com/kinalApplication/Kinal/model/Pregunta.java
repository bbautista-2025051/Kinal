package com.kinalApplication.Kinal.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "preguntas")
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String texto;

    @ManyToOne
    @JoinColumn(name = "lectura_id", nullable = false)
    private Lectura lectura;

    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Opcion> opciones = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "opcion_correcta_id")
    private Opcion respuestaCorrecta;

    // Constructores
    public Pregunta() {}

    public Pregunta(String texto, Lectura lectura) {
        this.texto = texto;
        this.lectura = lectura;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public Lectura getLectura() { return lectura; }
    public void setLectura(Lectura lectura) { this.lectura = lectura; }
    public List<Opcion> getOpciones() { return opciones; }
    public void setOpciones(List<Opcion> opciones) { this.opciones = opciones; }
    public Opcion getRespuestaCorrecta() { return respuestaCorrecta; }
    public void setRespuestaCorrecta(Opcion respuestaCorrecta) { this.respuestaCorrecta = respuestaCorrecta; }
}