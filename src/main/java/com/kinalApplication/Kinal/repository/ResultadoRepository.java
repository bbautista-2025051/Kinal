package com.kinalApplication.Kinal.repository;

import com.kinalApplication.Kinal.model.Estudiante;
import com.kinalApplication.Kinal.model.Lectura;
import com.kinalApplication.Kinal.model.ResultadoTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultadoRepository extends JpaRepository<ResultadoTest, Long> {
    List<ResultadoTest> findByEstudianteOrderByFechaDesc(Estudiante estudiante);

    // Verifica si el estudiante ya completó el test de una lectura específica
    Optional<ResultadoTest> findByEstudianteAndLectura(Estudiante estudiante, Lectura lectura);

    boolean existsByEstudianteAndLectura(Estudiante estudiante, Lectura lectura);
}
