package com.microservicios.cliente.service;

import com.microservicios.cliente.entity.Cliente;
import com.microservicios.cliente.entity.Region;
import com.microservicios.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository repository;

    @Override
    public List<Cliente> findAllClientes() {
        return repository.findAll();
    }

    @Override
    public List<Cliente> findClienteByRegion(Region region) {
        return repository.findByRegion(region);
    }

    @Override
    public void createCliente(Cliente cliente) {
        repository.save(cliente);
    }

    @Override
    public void updateCliente(Cliente cliente) {
        repository.save(cliente);
    }

    @Override
    public void deleteCliente(Cliente cliente) {
        repository.deleteById(cliente.getId());
    }

    @Override
    public Cliente getCliente(Long id) {
        return repository.findById(id).orElse(null);
    }
}
