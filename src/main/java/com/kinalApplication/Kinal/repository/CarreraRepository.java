package com.kinalApplication.Kinal.repository;

import com.kinalApplication.Kinal.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {

    // JOIN FETCH para cargar las lecturas en la misma query
    // y evitar LazyInitializationException en el template
    @Query("SELECT DISTINCT c FROM Carrera c LEFT JOIN FETCH c.lecturas")
    List<Carrera> findAllWithLecturas();
}