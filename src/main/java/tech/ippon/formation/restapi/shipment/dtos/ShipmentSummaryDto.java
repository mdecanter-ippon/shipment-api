package tech.ippon.formation.restapi.shipment.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentSummaryDto {

    @JsonProperty("expedition_id")
    private Long id;

    @JsonProperty("destination_city")
    @NotBlank(message = "Destination city is mandatory")
    private String destination;

    @JsonProperty("weight_kg")
    @NotNull(message = "Weight is mandatory")
    @Positive(message = "Weight must be positive")
    private Double weight;

    @JsonProperty("shipping_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate shippingDate;

    public ShipmentSummaryDto(Long id, String destination, Double weight) {
        this.id = id;
        this.destination = destination;
        this.weight = weight;
    }
}
