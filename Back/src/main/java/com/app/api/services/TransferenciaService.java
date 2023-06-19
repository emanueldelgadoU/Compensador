package com.app.api.services;

import com.app.api.models.TransferenciaModel;
import com.app.api.repositories.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferenciaService {

    private TransferenciaRepository transferenciaRepository;

    @Autowired
    public TransferenciaService(TransferenciaRepository transferenciaRepository) {
        this.transferenciaRepository = transferenciaRepository;
    }

    public List<TransferenciaModel> obtenerTodasTransferencias() {
        return transferenciaRepository.findAll();
    }

    public void guardarTransferencia(TransferenciaModel transferencia) {
        transferenciaRepository.save(transferencia);
    }
}
