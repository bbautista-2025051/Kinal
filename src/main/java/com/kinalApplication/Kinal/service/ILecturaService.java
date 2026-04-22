package com.kinalApplication.Kinal.service;

import com.kinalApplication.Kinal.model.Lectura;
import com.kinalApplication.Kinal.repository.LecturaRepository;
import com.kinalApplication.Kinal.service.LecturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ILecturaService implements LecturaService {

    @Autowired
    private LecturaRepository lecturaRepository;

    @Override
    public List<Lectura> findByCarreraId(Long carreraId) {
        return lecturaRepository.findByCarreraId(carreraId);
    }

    @Override
    public Optional<Lectura> findById(Long id) {
        return lecturaRepository.findById(id);
    }
}