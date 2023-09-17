package com.microservicios.producto.service;


import com.microservicios.producto.entity.Categoria;
import com.microservicios.producto.entity.Producto;

import java.util.List;

 public interface ProductoService {

     List<Producto> listAllProducto();
     Producto getProducto(Long id);

     Producto createProducto(Producto Producto);
     void updateProducto(Producto Producto);
     void deleteProducto(Long id);
     List<Producto> findByCategory(Categoria categoria);
     Producto updateStock(Long id, Double cantidad);
    
}
