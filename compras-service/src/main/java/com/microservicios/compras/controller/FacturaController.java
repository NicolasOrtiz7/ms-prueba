package com.microservicios.compras.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicios.compras.entity.Factura;
import com.microservicios.compras.model.Cliente;
import com.microservicios.compras.service.FacturaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public ResponseEntity<List<Factura>> listAllFacturas() {
        List<Factura> facturas = facturaService.findAllFacturas();
        if (facturas.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(facturas);
    }

    @CircuitBreaker(name = "clientesCB", fallbackMethod = "fallBackClientes")
    @GetMapping("/{id}")
    public ResponseEntity<Factura> getFactura(@PathVariable("id") long id) {
        log.info("Fetching Invoice with id {}", id);
        Factura factura  = facturaService.getFactura(id);
        if (null == factura) {
            log.error("Invoice with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(factura);
    }

    @PostMapping
    public ResponseEntity<Factura> createFacturas(@RequestBody Factura factura, BindingResult result) {
        log.info("Creating Invoice : {}", factura);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        facturaService.createFactura(factura);

        return  ResponseEntity.status( HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFacturas(@PathVariable("id") long id, @RequestBody Factura factura) {
        log.info("Updating Invoice with id {}", id);

        factura.setId(id);
        facturaService.updateFactura(factura);

        return  ResponseEntity.ok("actualizado");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Factura> deleteFacturas(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Invoice with id {}", id);

        Factura factura = facturaService.getFactura(id);
        if (factura == null) {
            log.error("Unable to delete. Invoice with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        facturaService.deleteFactura(factura);
        return ResponseEntity.ok(factura);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
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


    // ############## CIRCUIT BREAKER

    private ResponseEntity<List<Cliente>> fallBackClientes(RuntimeException e){
        return new ResponseEntity("El servicio esta deshabilitado actualmente", HttpStatus.OK);
    }


}
