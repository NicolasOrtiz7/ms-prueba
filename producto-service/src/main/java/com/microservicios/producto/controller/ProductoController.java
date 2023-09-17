package com.microservicios.producto.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicios.producto.entity.Categoria;
import com.microservicios.producto.entity.Producto;
import com.microservicios.producto.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listProductos(@RequestParam(name = "categoria_id", required = false) Long categoria_id){
        List<Producto> productos = new ArrayList<>();

        if(categoria_id == null)
            productos = productoService.listAllProducto();
        else
            productos = productoService.findByCategory(Categoria.builder().id(categoria_id).build());

        if (productos.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable Long id){
        Optional<Producto> productoExists = Optional.ofNullable(productoService.getProducto(id));

        if (productoExists.isEmpty()){
            return null;
        }
        return ResponseEntity.ok(productoService.getProducto(id));
    }

    @PostMapping
    public void createProducto(@RequestBody Producto producto){
        productoService.createProducto(producto);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable("id") Long id, @RequestBody Producto producto){
        producto.setId(id);
        productoService.updateProducto(producto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
         productoService.deleteProducto(id);
    }

    @PutMapping ("/{id}/stock")
    public ResponseEntity<Producto> updateStockProduct(@PathVariable Long id, @RequestParam(name = "cantidad", required = true) Double cantidad){
        Producto product = productoService.updateStock(id, cantidad);
        if (product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }


    // #########################################

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
