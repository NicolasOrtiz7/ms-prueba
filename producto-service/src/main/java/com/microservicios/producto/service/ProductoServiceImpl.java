package com.microservicios.producto.service;

import com.microservicios.producto.entity.Categoria;
import com.microservicios.producto.entity.Producto;
import com.microservicios.producto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository repository;

    @Override
    public List<Producto> listAllProducto() {
        return repository.findAll();
    }

    @Override
    public Producto getProducto(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Producto createProducto(Producto producto) {
        producto.setEstado("creado");
        producto.setCreado(new Date());
        return repository.save(producto);
    }

    @Override
    public void updateProducto(Producto producto) {
        repository.save(producto);
    }

    @Override
    public void deleteProducto(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Producto> findByCategory(Categoria categoria) {
        return repository.findByCategoria(categoria);
    }

    @Override
    public Producto updateStock(Long id, Double cantidad) {

        Optional<Producto> productoExists = repository.findById(id);

        if (productoExists.isEmpty()){
            return null;
        }
        Double stock = productoExists.get().getStock() + cantidad;

        productoExists.get().setId(id);
        productoExists.get().setStock(stock);

        return repository.save(productoExists.get());
    }
}
