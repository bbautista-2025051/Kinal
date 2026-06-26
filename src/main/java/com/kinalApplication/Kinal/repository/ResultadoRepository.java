package com.kinalApplication.Kinal.repository;

import com.kinalApplication.Kinal.model.Estudiante;
import com.kinalApplication.Kinal.model.Lectura;
import com.kinalApplication.Kinal.model.ResultadoTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultadoRepository extends JpaRepository<ResultadoTest, Long> {

    @Query("SELECT r FROM ResultadoTest r " +
            "JOIN FETCH r.lectura l " +
            "JOIN FETCH l.carrera " +
            "WHERE r.estudiante = :estudiante " +
            "ORDER BY r.fecha DESC")
    List<ResultadoTest> findByEstudianteOrderByFechaDesc(@Param("estudiante") Estudiante estudiante);
    Optional<ResultadoTest> findByEstudianteAndLectura(Estudiante estudiante, Lectura lectura);
    boolean existsByEstudianteAndLectura(Estudiante estudiante, Lectura lectura);
}