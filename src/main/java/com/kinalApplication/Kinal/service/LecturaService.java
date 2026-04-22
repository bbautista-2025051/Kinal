package com.kinalApplication.Kinal.service;

import com.kinalApplication.Kinal.model.Lectura;
import java.util.List;
import java.util.Optional;

public interface LecturaService {
    List<Lectura> findByCarreraId(Long carreraId);
    Optional<Lectura> findById(Long id);
}