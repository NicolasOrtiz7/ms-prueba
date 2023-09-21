package com.microservicios.compras.service;

import com.microservicios.compras.entity.Factura;
import com.microservicios.compras.entity.FacturaItem;
import com.microservicios.compras.feignclient.ClienteFeignClient;
import com.microservicios.compras.feignclient.ProductoFeignClient;
import com.microservicios.compras.model.Cliente;
import com.microservicios.compras.model.Producto;
import com.microservicios.compras.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaServiceImpl implements FacturaService{

    @Autowired
    private FacturaRepository repository;

    @Autowired
    private ClienteFeignClient clienteFeignClient;

    @Autowired
    private ProductoFeignClient productoFeignClient;

    @Override
    public List<Factura> findAllFacturas() {
        return repository.findAll();
    }

    @Override
    public void createFactura(Factura factura) {

        factura.setEstado("creado");
        factura.getItems().forEach(facturaItem -> {
            productoFeignClient.updateStockProduct(facturaItem.getProductoId(), facturaItem.getCantidad() * -1);
        });

        repository.save(factura);
    }

    @Override
    public void updateFactura(Factura factura) {
        repository.save(factura);
    }

    @Override
    public void deleteFactura(Factura factura) {
        repository.delete(factura);
    }

    @Override
    public Factura getFactura(Long id) {
        Factura factura =  repository.findById(id).orElse(null);

        if (factura != null){
            Cliente cliente = clienteFeignClient.getCliente(factura.getClienteId()).getBody();
            factura.setCliente(cliente);

            List<FacturaItem> listaItems = factura.getItems().stream().map(facturaItem -> {
                Producto producto = productoFeignClient.getProducto(facturaItem.getProductoId()).getBody();
                facturaItem.setProducto(producto);
                return facturaItem;
            }).collect(Collectors.toList());
            factura.setItems(listaItems);
        }
        return factura;
    }


}
