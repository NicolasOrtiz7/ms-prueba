package com.microservicios.compras.feignclient;

import com.microservicios.compras.model.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "producto-service" , url ="http://localhost:8003/productos")
public interface ProductoFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<Producto> getProducto(@PathVariable Long id);

    @PutMapping("/{id}/stock")
    ResponseEntity<Producto> updateStockProduct(@PathVariable Long id,
                                                       @RequestParam(name = "cantidad",
                                                               required = true) Double cantidad);

}
