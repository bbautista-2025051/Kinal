// CarreraRepository.java
package com.kinalApplication.Kinal.repository;

import com.kinalApplication.Kinal.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {
}