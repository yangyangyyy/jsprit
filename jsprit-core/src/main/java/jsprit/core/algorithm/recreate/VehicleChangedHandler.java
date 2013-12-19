package jsprit.core.algorithm.recreate;

import jsprit.core.algorithm.event.ChangeVehicle;
import jsprit.core.algorithm.event.RouteChangedEventListener;

class VehicleChangedHandler implements RouteChangedEventListener<ChangeVehicle>{

	@Override
	public void sendRouteChangedEvent(ChangeVehicle event) {
		handle(event);
	}

	private void handle(ChangeVehicle event) {
//		event.getVehicleRoute().setVehicle(event.getNewVehicle());
	}

	@Override
	public Class<ChangeVehicle> getEventType() {
		return ChangeVehicle.class;
	}
	
	
}
