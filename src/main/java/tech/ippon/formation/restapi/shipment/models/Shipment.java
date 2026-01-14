package tech.ippon.formation.restapi.shipment.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import tech.ippon.formation.restapi.shipment.enums.ShipmentStatus;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Destination city is mandatory")
    private String destination;

    @NotNull(message = "Weight is mandatory")
    @Positive(message = "Weight must be positive")
    private Double weight;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is mandatory")
    private ShipmentStatus status;

    @JsonProperty("shipping_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate shippingDate;
}