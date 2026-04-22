package com.kinalApplication.Kinal.service;


import com.kinalApplication.Kinal.model.Pregunta;
import com.kinalApplication.Kinal.repository.PreguntaRepository;
import com.kinalApplication.Kinal.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ITestService implements TestService {

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Override
    public List<Pregunta> obtenerPreguntasAleatorias(Long lecturaId, int cantidad) {
        List<Pregunta> todas = preguntaRepository.findByLecturaId(lecturaId);
        if (todas == null || todas.size() < cantidad) {
            return Collections.emptyList();
        }
        Collections.shuffle(todas);
        return todas.stream().limit(cantidad).collect(Collectors.toList());
    }

    @Override
    public double calificar(List<Long> respuestasIds, List<Pregunta> preguntas) {
        if (respuestasIds == null || preguntas == null || respuestasIds.size() != preguntas.size()) {
            return 0.0;
        }
        int aciertos = 0;
        for (int i = 0; i < preguntas.size(); i++) {
            Pregunta p = preguntas.get(i);
            Long respuestaCorrectaId = p.getRespuestaCorrecta() != null ? p.getRespuestaCorrecta().getId() : null;
            if (respuestaCorrectaId != null && respuestaCorrectaId.equals(respuestasIds.get(i))) {
                aciertos++;
            }
        }
        return (aciertos * 100.0) / preguntas.size();
    }
}