package com.kinalApplication.Kinal.repository;

import com.kinalApplication.Kinal.model.Lectura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LecturaRepository extends JpaRepository<Lectura, Long> {
    List<Lectura> findByCarreraId(Long carreraId);
}