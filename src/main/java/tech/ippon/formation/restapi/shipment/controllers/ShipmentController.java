package tech.ippon.formation.restapi.shipment.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
// 1. Grouping endpoints in the UI
@Tag(name = "shipment API", description = "Management of shipment orders")
public class ShipmentController {
    private final ShipmentService service;
    private final ShipmentMapper mapper;

    public ShipmentController(ShipmentService service, ShipmentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "Get all shipments", description = "Retrieves the full list of all shipments in the database.")
    public List<ShipmentSummaryDto> getAllShipments() {
        return service.findAll()
                .stream()
                .map(mapper::toSummaryDto)
                .toList();
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get an shipment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "shipment found"),
            @ApiResponse(responseCode = "404", description = "shipment not found", content = @Content)
    })
    public ShipmentDetailDto getShipmentById(@PathVariable Long id) {
        return mapper.toDetailDto(service.findById(id));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new shipment", description = "Creates a shipment with initial status PENDING. Validates input data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "shipment successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (validation error)", content = @Content)
    })
    public ShipmentDetailDto createShipment(@RequestBody @Valid ShipmentDetailDto dto) {
        return mapper.toDetailDto(
                service.create(mapper.toEntity(dto))
        );
    }
}