package com.microservicios.compras.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microservicios.compras.model.Cliente;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_factura")
    private String numeroFactura;
    private String descripcion;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Temporal(TemporalType.DATE)
    private Date creado;


    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id")
    private List<FacturaItem> items;

    private String estado;

    @Transient
    private Cliente cliente;

    public Factura(){
        items = new ArrayList<>();
    }

    @PrePersist
    public void prePersist() {
        this.creado = new Date();
    }

}
