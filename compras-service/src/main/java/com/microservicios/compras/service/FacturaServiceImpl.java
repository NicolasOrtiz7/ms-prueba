package com.microservicios.compras.service;

import com.microservicios.compras.entity.Factura;
import com.microservicios.compras.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaServiceImpl implements FacturaService{

    @Autowired
    private FacturaRepository repository;

    @Override
    public List<Factura> findAllFacturas() {
        return repository.findAll();
    }

    @Override
    public void createFactura(Factura factura) {
        repository.save(factura);
    }

    @Override
    public void updateFactura(Factura factura) {
        repository.save(factura);
    }

    @Override
    public void deleteFactura(Factura factura) {
        repository.delete(factura);
    }

    @Override
    public Factura getFactura(Long id) {
        return repository.findById(id).orElse(null);
    }


}
