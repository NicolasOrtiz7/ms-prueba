package com.microservicios.compras.repository;

import com.microservicios.compras.entity.FacturaItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaItemRepository extends JpaRepository<FacturaItem, Long> {
}
