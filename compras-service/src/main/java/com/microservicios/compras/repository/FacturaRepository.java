package com.microservicios.compras.repository;

import com.microservicios.compras.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    List<Factura> findByClienteId(Long clienteId );
    Factura findByNumeroFactura(String numeroFactura);

}
