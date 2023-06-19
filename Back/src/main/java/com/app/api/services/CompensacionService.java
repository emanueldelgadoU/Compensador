package com.app.api.services;

import com.app.api.models.CompensacionModel;
import com.app.api.repositories.CompensacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompensacionService {
    private CompensacionRepository compensacionRepository;

    @Autowired
    public CompensacionService(CompensacionRepository compensacionRepository) {
        this.compensacionRepository = compensacionRepository;
    }

    public void guardarCompensacion(CompensacionModel compensacion) {
        // Lógica para guardar la compensación en la base de datos
        compensacionRepository.save(compensacion);
    }

    public List<CompensacionModel> getAllCompensaciones() {
        return compensacionRepository.findAll();
    }
}
