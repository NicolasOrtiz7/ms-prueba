package com.microservicios.compras.entity;

import com.microservicios.compras.model.Producto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FacturaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double cantidad;
    private Double precio;

    @Column(name = "producto_id")
    private Long productoId;


    @Transient
    private Double subTotal;

    @Transient
    private Producto producto;

    public Double getSubTotal(){
        if (this.precio >0  && this.cantidad >0 ){
            return this.cantidad * this.precio;
        }else {
            return (double) 0;
        }
    }
    public FacturaItem(){
        this.cantidad=(double) 0;
        this.precio=(double) 0;

    }

}
