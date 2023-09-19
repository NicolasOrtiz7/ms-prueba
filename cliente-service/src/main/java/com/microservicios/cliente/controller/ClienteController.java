package com.microservicios.cliente.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicios.cliente.entity.Cliente;
import com.microservicios.cliente.entity.Region;
import com.microservicios.cliente.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> listAllClientes(@RequestParam(name = "regionId" , required = false) Long regionId ) {
        List<Cliente> clientes =  new ArrayList<>();
        if (null ==  regionId) {
            clientes = clienteService.findAllClientes();
            if (clientes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        }else{
            Region region= new Region();
            region.setId(regionId);
            clientes = clienteService.findClienteByRegion(region);
            if ( null == clientes ) {
                log.error("Customers with Region id {} not found.", regionId);
                return  ResponseEntity.notFound().build();
            }
        }

        return  ResponseEntity.ok(clientes);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable("id") long id) {
        log.info("Fetching Customer with id {}", id);
        Cliente cliente = clienteService.getCliente(id);
        if (  null == cliente) {
            log.error("Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(cliente);
    }

    @PostMapping
    public void createCliente(@RequestBody Cliente cliente, BindingResult result) {
        log.info("Creating Customer : {}", cliente);
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        clienteService.createCliente(cliente);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable("id") long id, @RequestBody Cliente cliente) {
        log.info("Updating Customer with id {}", id);

        Cliente currentCustomer = clienteService.getCliente(id);

        if ( null == currentCustomer ) {
            log.error("Unable to update. Customer with id {} not found.", id);
            return null;
        }
        cliente.setId(id);
        clienteService.updateCliente(cliente);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Cliente> deleteCliente(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Customer with id {}", id);

        Cliente cliente = clienteService.getCliente(id);
        if ( null == cliente ) {
            log.error("Unable to delete. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        clienteService.deleteCliente(cliente);
        return  ResponseEntity.ok(cliente);
    }




    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
