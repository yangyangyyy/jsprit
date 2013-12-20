package jsprit.core.algorithm.ruin;

import jsprit.core.algorithm.event.RemoveJob;
import jsprit.core.algorithm.event.RouteEventListener;
import jsprit.core.algorithm.ruin.listener.RuinListeners;
import jsprit.core.problem.solution.route.VehicleRoute;

class RemoveJobListener implements RouteEventListener<RemoveJob>{

	private RuinListeners ruinListeners;
	
	public RemoveJobListener(RuinListeners ruinListeners) {
		super();
		this.ruinListeners = ruinListeners;
	}

	@Override
	public void sendRouteEvent(String eventSourceId, RemoveJob event) {
		boolean removed = false;
		for (VehicleRoute route : event.getRoutes()) {
			removed = route.getTourActivities().removeJob(event.getJob());
			if (removed) {
				ruinListeners.removed(event.getJob(),route);
				break;
			}
		}
	}

	@Override
	public Class<RemoveJob> getEventType() {
		return RemoveJob.class;
	}
	
}