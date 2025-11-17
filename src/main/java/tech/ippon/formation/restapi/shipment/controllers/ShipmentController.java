package tech.ippon.formation.restapi.shipment.controllers;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import tech.ippon.formation.restapi.shipment.dtos.ShipmentDetailDto;
import tech.ippon.formation.restapi.shipment.dtos.ShipmentSummaryDto;
import tech.ippon.formation.restapi.shipment.mappers.ShipmentMapper;
import tech.ippon.formation.restapi.shipment.services.ShipmentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipments")
public class ShipmentController {
    private final ShipmentService service;
    private final ShipmentMapper mapper;

    // Injection de dépendance par constructeur (Best Practice)
    public ShipmentController(ShipmentService service, ShipmentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // 1. GET - Récupérer la liste
    @GetMapping
    public List<ShipmentSummaryDto> getAllShipments() {
        return service.findAll()
                .stream()
                .map(mapper::toSummaryDto)
                .toList();
    }


    // 2. GET {id} - Récupérer une ressource unique (avec gestion 404)
    @GetMapping("/{id}")
    public ShipmentDetailDto getShipmentById(@PathVariable Long id) {
        return mapper.toDetailDto(service.findById(id));
    }


    // 3. POST - Créer une ressource (Retourne 201 Created)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShipmentDetailDto createShipment(@RequestBody ShipmentDetailDto dto) {
        return mapper.toDetailDto(
                service.create(mapper.toEntity(dto))
        );
    }
}