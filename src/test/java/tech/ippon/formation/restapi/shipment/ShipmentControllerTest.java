package tech.ippon.formation.restapi.shipment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tech.ippon.formation.restapi.shipment.controllers.ShipmentController;
import tech.ippon.formation.restapi.shipment.dtos.ShipmentDetailDto;
import tech.ippon.formation.restapi.shipment.dtos.ShipmentSummaryDto;
import tech.ippon.formation.restapi.shipment.enums.ShipmentStatus;
import tech.ippon.formation.restapi.shipment.mappers.ShipmentMapper;
import tech.ippon.formation.restapi.shipment.models.Shipment;
import tech.ippon.formation.restapi.shipment.services.ShipmentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShipmentController.class)
class ShipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShipmentService service;

    @MockitoBean
    private ShipmentMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllShipments() throws Exception {
        Shipment shipment = new Shipment();
        shipment.setId(1L);
        shipment.setDestination("Paris");
        shipment.setWeight(5.0);
        shipment.setStatus(ShipmentStatus.PENDING);

        ShipmentSummaryDto summaryDto =
                new ShipmentSummaryDto(1L, "Paris", 5.0);

        when(service.findAll()).thenReturn(List.of(shipment));
        when(mapper.toSummaryDto(shipment)).thenReturn(summaryDto);

        mockMvc.perform(get("/api/v1/shipments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].destination_city").value("Paris"))
                .andExpect(jsonPath("$[0].weight_kg").value(5.0));
    }

    @Test
    void shouldCreateShipment() throws Exception {
        ShipmentDetailDto inputDto =
                new ShipmentDetailDto(null, "Lyon", 10.5, ShipmentStatus.PENDING);

        Shipment entity = new Shipment();
        entity.setDestination("Lyon");
        entity.setWeight(10.5);
        entity.setStatus(ShipmentStatus.PENDING);

        Shipment saved = new Shipment();
        saved.setId(123L);
        saved.setDestination("Lyon");
        saved.setWeight(10.5);
        saved.setStatus(ShipmentStatus.PENDING);

        ShipmentDetailDto outputDto =
                new ShipmentDetailDto(123L, "Lyon", 10.5, ShipmentStatus.PENDING);

        when(mapper.toEntity(any(ShipmentDetailDto.class))).thenReturn(entity);
        when(service.create(any(Shipment.class))).thenReturn(saved);
        when(mapper.toDetailDto(any(Shipment.class))).thenReturn(outputDto);

        mockMvc.perform(post("/api/v1/shipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.expedition_id").value(123))
                .andExpect(jsonPath("$.destination_city").value("Lyon"));
    }

    @Test
    void shouldReturn404WhenNotFound() throws Exception {
        when(service.findById(99L))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/v1/shipments/99"))
                .andExpect(status().isNotFound());
    }
}
