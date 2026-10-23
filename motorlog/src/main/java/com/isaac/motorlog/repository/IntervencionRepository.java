package com.isaac.motorlog.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isaac.motorlog.model.Intervencion;

@Repository
public interface IntervencionRepository extends JpaRepository<Intervencion, Long> {
    
    // Con este nombre tan largo, Spring Boot hace un "SELECT * FROM intervencion WHERE vehiculo_id = ? ORDER BY fecha DESC"
    List<Intervencion> findByVehiculoIdOrderByFechaDesc(Long vehiculoId);
}
