package tech.ippon.formation.restapi.shipment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for ShipmentController.
 * We use @WebMvcTest to slice the context and only load the Web layer.
 */
@WebMvcTest(ShipmentController.class)
class ShipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShipmentRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllShipments() throws Exception {
        // 1. GIVEN
        Shipment exp = new Shipment();
        exp.setId(1L);
        exp.setDestination("Paris");
        exp.setStatus("PENDING");

        when(repository.findAll()).thenReturn(List.of(exp));

        // 2. WHEN & THEN
        mockMvc.perform(get("/api/v1/shipments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].destination").value("Paris"));
    }

    @Test
    void shouldCreateShipment() throws Exception {
        // 1. GIVEN
        Shipment input = new Shipment();
        input.setDestination("Lyon");
        input.setWeight(10.5);
        input.setStatus("PENDING");

        Shipment saved = new Shipment();
        saved.setId(123L);
        saved.setDestination("Lyon");
        saved.setWeight(10.5);
        saved.setStatus("PENDING");

        when(repository.save(any(Shipment.class))).thenReturn(saved);

        // 2. WHEN & THEN
        mockMvc.perform(post("/api/v1/shipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(123))
                .andExpect(jsonPath("$.destination").value("Lyon"));
    }

    @Test
    void shouldReturn404WhenNotFound() throws Exception {
        // 1. GIVEN
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // 2. WHEN & THEN
        mockMvc.perform(get("/api/v1/shipments/99"))
                .andExpect(status().isNotFound());
    }
}