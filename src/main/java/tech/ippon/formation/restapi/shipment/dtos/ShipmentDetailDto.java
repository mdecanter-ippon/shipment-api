package tech.ippon.formation.restapi.shipment.dtos;

import lombok.Data;

public class ShipmentDetailDto {

    private Long id;
    private String destination;
    private Double weight;
    private String status;

    public ShipmentDetailDto() {
    }

    public ShipmentDetailDto(Long id, String destination, Double weight, String status) {
        this.id = id;
        this.destination = destination;
        this.weight = weight;
        this.status = status;
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

    public String getStatus() {
        return status;
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

    public void setStatus(String status) {
        this.status = status;
    }
}
