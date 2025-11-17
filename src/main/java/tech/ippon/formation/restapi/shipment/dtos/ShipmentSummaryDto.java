package tech.ippon.formation.restapi.shipment.dtos;

public class ShipmentSummaryDto {

    private Long id;
    private String destination;
    private Double weight;

    public ShipmentSummaryDto() {
    }

    public ShipmentSummaryDto(Long id, String destination, Double weight) {
        this.id = id;
        this.destination = destination;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }

    public Double getWeight() {
        return weight;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
