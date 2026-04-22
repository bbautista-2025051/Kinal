package com.kinalApplication.Kinal.service;


import com.kinalApplication.Kinal.model.Pregunta;
import java.util.List;

public interface TestService {
    List<Pregunta> obtenerPreguntasAleatorias(Long lecturaId, int cantidad);
    double calificar(List<Long> respuestasIds, List<Pregunta> preguntas);
}