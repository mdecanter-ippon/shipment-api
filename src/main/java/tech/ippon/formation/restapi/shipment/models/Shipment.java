package tech.ippon.formation.restapi.shipment.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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

    private String destination;
    private Double weight;
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
    @JsonProperty("shipping_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate shippingDate;
}