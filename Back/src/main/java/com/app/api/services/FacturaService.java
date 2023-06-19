package com.app.api.services;


import com.app.api.models.FacturaModel;
import com.app.api.repositories.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;

    @Autowired
    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public List<FacturaModel> obtenerTodasLasFacturas() {
        return facturaRepository.findAll();
    }

    public FacturaModel obtenerFacturaPorId(Long id) {
        Optional<FacturaModel> facturaOptional = facturaRepository.findById(id);
        return facturaOptional.orElse(null);
    }

    public void guardarFactura(FacturaModel factura) {
        facturaRepository.save(factura);
    }
    public void eliminarFacturaPorId(Long id) {
        facturaRepository.deleteById(id);
    }
}
