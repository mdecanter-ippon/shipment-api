package tech.ippon.formation.restapi.shipment.controllers;

import jakarta.validation.Valid;
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

    public ShipmentController(ShipmentService service, ShipmentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ShipmentSummaryDto> getAllShipments() {
        return service.findAll()
                .stream()
                .map(mapper::toSummaryDto)
                .toList();
    }


    @GetMapping("/{id}")
    public ShipmentDetailDto getShipmentById(@PathVariable Long id) {
        return mapper.toDetailDto(service.findById(id));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShipmentDetailDto createShipment(@RequestBody ShipmentDetailDto dto) {
        return mapper.toDetailDto(
                service.create(mapper.toEntity(dto))
        );
    }
}