package com.kinalApplication.Kinal.repository;


import com.kinalApplication.Kinal.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, String> {
    Optional<Estudiante> findByEmail(String email);
}