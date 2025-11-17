package tech.ippon.formation.restapi.shipment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ippon.formation.restapi.shipment.models.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    // Spring Data JPA génère automatiquement findAll, findById, save, etc.
}