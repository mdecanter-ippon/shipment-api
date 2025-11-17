package tech.ippon.formation.restapi.shipment.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tech.ippon.formation.restapi.shipment.models.Shipment;
import tech.ippon.formation.restapi.shipment.repositories.ShipmentRepository;

import java.util.List;

@Service
public class ShipmentService {

    private final ShipmentRepository repository;

    public ShipmentService(ShipmentRepository repository) {
        this.repository = repository;
    }

    public List<Shipment> findAll() {
        return repository.findAll();
    }

    public Shipment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Shipment not found"));
    }

    public Shipment create(Shipment shipment) {
        return repository.save(shipment);
    }
}
