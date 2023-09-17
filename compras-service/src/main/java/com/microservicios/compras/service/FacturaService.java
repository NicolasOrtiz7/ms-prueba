package com.microservicios.compras.service;

import com.microservicios.compras.entity.Factura;

import java.util.List;

public interface FacturaService {

    List<Factura> findAllFacturas();

    void createFactura(Factura factura);
    void updateFactura(Factura factura);
    void deleteFactura(Factura factura);
    Factura getFactura(Long id);

}
