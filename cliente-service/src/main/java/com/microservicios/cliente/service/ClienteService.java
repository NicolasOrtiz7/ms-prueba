package com.microservicios.cliente.service;

import com.microservicios.cliente.entity.Cliente;
import com.microservicios.cliente.entity.Region;

import java.util.List;

public interface ClienteService {

    List<Cliente> findAllClientes();
    List<Cliente> findClienteByRegion(Region region);

    void createCliente(Cliente cliente);
    void updateCliente(Cliente cliente);
    void deleteCliente(Cliente cliente);
    Cliente getCliente(Long id);

}
