package tech.ippon.formation.restapi.shipment.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tech.ippon.formation.restapi.shipment.api.LegacyClient;
import tech.ippon.formation.restapi.shipment.models.Shipment;
import tech.ippon.formation.restapi.shipment.repositories.ShipmentRepository;

import java.util.List;

@Service
public class ShipmentService {

    private final ShipmentRepository repository;
    private final LegacyClient legacyClient;

    public ShipmentService(ShipmentRepository repository, LegacyClient legacyClient) {
        this.repository = repository;
        this.legacyClient = legacyClient;
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
        // 1. Save to Local DB (The "Modern" part)
        Shipment saved = repository.save(shipment);

        // 2. Notify Legacy (The "Integration" part)
        String legacyResponse = legacyClient.notifyShipment(saved.getDestination(), saved.getWeight());

        // (Optional) You could attach the legacy ID to the response if you wanted
        System.out.println("Legacy Response: " + legacyResponse);

        return saved;    }
}
