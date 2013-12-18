package jsprit.core.algorithm.event;

import jsprit.core.problem.job.Shipment;

public class RemoveShipment {
	
	private Shipment shipment;

	public RemoveShipment(Shipment shipment) {
		super();
		this.shipment = shipment;
	}

	/**
	 * @return the shipment
	 */
	public Shipment getShipment() {
		return shipment;
	}
	
	

}
