package tech.ippon.formation.restapi.shipment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import tech.ippon.formation.restapi.shipment.dtos.ShipmentDetailDto;
import tech.ippon.formation.restapi.shipment.enums.ShipmentStatus;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest // Only loads Jackson ObjectMapper and related beans
class ShipmentJsonTest {

    @Autowired
    private JacksonTester<ShipmentDetailDto> json;

    @Test
    void testSerialization() throws Exception {
        // GIVEN
        ShipmentDetailDto shipment = new ShipmentDetailDto();
        shipment.setId(1L);
        shipment.setDestination("Marseille");
        shipment.setWeight(10.0);
        shipment.setStatus(ShipmentStatus.SHIPPED);
        shipment.setShippingDate(LocalDate.of(2023, 12, 25));

        // WHEN
        JsonContent<ShipmentDetailDto> result = json.write(shipment);

        // THEN
        // Checks fields renaming (snake_case) and values
        assertThat(result).hasJsonPathValue("$.destination_city");
        assertThat(result).extractingJsonPathStringValue("$.destination_city").isEqualTo("Marseille");

        assertThat(result).hasJsonPathValue("$.weight_kg");
        assertThat(result).extractingJsonPathNumberValue("$.weight_kg").isEqualTo(10.0);

        // Checks date formatting (DD-MM-YYYY)
        assertThat(result).hasJsonPathValue("$.shipping_date");
        assertThat(result).extractingJsonPathStringValue("$.shipping_date").isEqualTo("25-12-2023");

        // Checks that the sensitive field is not present
        assertThat(result).doesNotHaveJsonPathValue("$.investigationCode");
        assertThat(result).doesNotHaveJsonPathValue("$.investigation_code");
    }

    @Test
    void testDeserialization() throws Exception {
        // GIVEN (raw JSON content retrieved from an external source)
        String jsonContent = """
                {
                    "destination_city": "Lille",
                    "weight_kg": 5.5,
                    "status": "PENDING",
                    "shipping_date": "01-01-2024"
                }
                """;

        // WHEN
        ShipmentDetailDto result = json.parse(jsonContent).getObject();

        // THEN
        assertThat(result.getDestination()).isEqualTo("Lille");
        assertThat(result.getWeight()).isEqualTo(5.5);
        assertThat(result.getStatus()).isEqualTo(ShipmentStatus.PENDING);
        assertThat(result.getShippingDate()).isEqualTo(LocalDate.of(2024, 1, 1));
    }
}