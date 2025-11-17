package tech.ippon.formation.restapi.shipment.mappers;

import org.springframework.stereotype.Component;
import tech.ippon.formation.restapi.shipment.dtos.ShipmentDetailDto;
import tech.ippon.formation.restapi.shipment.dtos.ShipmentSummaryDto;
import tech.ippon.formation.restapi.shipment.models.Shipment;

@Component
public class ShipmentMapper {

    public ShipmentSummaryDto toSummaryDto(Shipment shipment) {
        return new ShipmentSummaryDto(
                shipment.getId(),
                shipment.getDestination(),
                shipment.getWeight()
        );
    }

    public ShipmentDetailDto toDetailDto(Shipment shipment) {
        return new ShipmentDetailDto(
                shipment.getId(),
                shipment.getDestination(),
                shipment.getWeight(),
                shipment.getStatus()
        );
    }

    public Shipment toEntity(ShipmentDetailDto shipmentDetailDto) {
        Shipment shipment = new Shipment();
        shipment.setId(shipmentDetailDto.getId());
        shipment.setDestination(shipmentDetailDto.getDestination());
        shipment.setWeight(shipmentDetailDto.getWeight());
        shipment.setStatus(shipmentDetailDto.getStatus());
        return shipment;
    }

}
