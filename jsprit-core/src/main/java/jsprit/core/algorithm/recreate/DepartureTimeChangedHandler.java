package jsprit.core.algorithm.recreate;

import jsprit.core.algorithm.event.ChangeDepartureTime;
import jsprit.core.algorithm.event.RouteEventListener;

class DepartureTimeChangedHandler implements RouteEventListener<ChangeDepartureTime>{

	@Override
	public void sendRouteEvent(String eventSourceId, ChangeDepartureTime event) {
		handle(event);
	}
	
	private void handle(ChangeDepartureTime event){
		event.getVehicleRoute().setDepartureTime(event.getNewDepartureTime());
	}

	@Override
	public Class<ChangeDepartureTime> getEventType() {
		return ChangeDepartureTime.class;
	}

}
