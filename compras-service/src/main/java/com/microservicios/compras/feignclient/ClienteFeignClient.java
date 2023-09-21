package com.microservicios.compras.feignclient;

import com.microservicios.compras.model.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente-service" , url ="http://localhost:8001/clientes")
public interface ClienteFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<Cliente> getCliente(@PathVariable("id") long id);

}
