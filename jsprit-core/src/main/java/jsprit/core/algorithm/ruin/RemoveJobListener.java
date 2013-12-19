package jsprit.core.algorithm.ruin;

import jsprit.core.algorithm.event.RemoveJob;
import jsprit.core.algorithm.event.RouteChangedEventListener;
import jsprit.core.algorithm.ruin.listener.RuinListeners;
import jsprit.core.problem.solution.route.VehicleRoute;

class RemoveJobListener implements RouteChangedEventListener<RemoveJob>{

	private RuinListeners ruinListeners;
	
	public RemoveJobListener(RuinListeners ruinListeners) {
		super();
		this.ruinListeners = ruinListeners;
	}

	@Override
	public void sendRouteChangedEvent(RemoveJob event) {
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