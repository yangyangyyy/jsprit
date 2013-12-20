package jsprit.core.algorithm.event;

import jsprit.core.problem.job.Shipment;
import jsprit.core.problem.solution.route.VehicleRoute;

public class InsertShipment implements RouteEvent{

	private Shipment shipment;
	
	private int pickupIndex;
	
	private int deliveryIndex;
	
	private VehicleRoute route;
	
	public InsertShipment(VehicleRoute route2change, Shipment shipment, int pickupIndex, int deliveryIndex) {
		this.shipment = shipment;
		this.pickupIndex = pickupIndex;
		this.deliveryIndex = deliveryIndex;
		route=route2change;
	}

	/**
	 * @return the shipment
	 */
	public Shipment getShipment() {
		return shipment;
	}

	/**
	 * @return the pickupIndex
	 */
	public int getPickupIndex() {
		return pickupIndex;
	}
	
	/**
	 * @return the route
	 */
	public VehicleRoute getRoute() {
		return route;
	}

	/**
	 * @return the deliveryIndex
	 */
	public int getDeliveryIndex() {
		return deliveryIndex;
	}

	@Override
	public Class<? extends RouteEvent> getType() {
		return InsertShipment.class;
	}
	
	

}
