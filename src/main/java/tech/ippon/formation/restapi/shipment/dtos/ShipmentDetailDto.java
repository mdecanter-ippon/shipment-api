package tech.ippon.formation.restapi.shipment.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.ippon.formation.restapi.shipment.enums.ShipmentStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentDetailDto {

    @JsonProperty("expedition_id")
    private Long id;

    @JsonProperty("destination_city")
    private String destination;

    @JsonProperty("weight_kg")
    private Double weight;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    @JsonProperty("shipping_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate shippingDate;

    public ShipmentDetailDto(Long id, String destination, Double weight, ShipmentStatus status) {
        this.id = id;
        this.destination = destination;
        this.weight = weight;
        this.status = status;
    }
}
