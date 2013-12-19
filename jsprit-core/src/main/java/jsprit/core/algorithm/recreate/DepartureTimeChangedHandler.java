package jsprit.core.algorithm.recreate;

import jsprit.core.algorithm.event.ChangeDepartureTime;
import jsprit.core.algorithm.event.RouteChangedEventListener;

class DepartureTimeChangedHandler implements RouteChangedEventListener<ChangeDepartureTime>{

	@Override
	public void sendRouteChangedEvent(ChangeDepartureTime event) {
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
