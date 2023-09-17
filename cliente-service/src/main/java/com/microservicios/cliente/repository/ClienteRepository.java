package com.microservicios.cliente.repository;

import com.microservicios.cliente.entity.Cliente;
import com.microservicios.cliente.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByDni(String dni);
    List<Cliente> findByApellido(String apellido);
    List<Cliente> findByRegion(Region region);

}
