package com.kinalApplication.Kinal.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class RespuestaTestDTO {
    @NotNull
    @Size(min = 1, message = "Debes responder todas las preguntas")
    private List<Long> respuestas;

    public List<Long> getRespuestas() { return respuestas; }
    public void setRespuestas(List<Long> respuestas) { this.respuestas = respuestas; }
}