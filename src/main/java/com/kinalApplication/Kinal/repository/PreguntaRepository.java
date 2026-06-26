package com.kinalApplication.Kinal.repository;

import com.kinalApplication.Kinal.model.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    List<Pregunta> findByLecturaId(Long lecturaId);
}