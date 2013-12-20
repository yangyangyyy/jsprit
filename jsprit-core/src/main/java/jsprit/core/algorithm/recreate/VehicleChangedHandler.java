package jsprit.core.algorithm.recreate;

import jsprit.core.algorithm.event.ChangeVehicle;
import jsprit.core.algorithm.event.RouteEventListener;

class VehicleChangedHandler implements RouteEventListener<ChangeVehicle>{

	@Override
	public void sendRouteEvent(String eventSourceId, ChangeVehicle event) {
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
