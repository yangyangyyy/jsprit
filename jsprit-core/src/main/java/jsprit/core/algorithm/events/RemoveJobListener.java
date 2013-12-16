package jsprit.core.algorithm.events;

import jsprit.core.problem.job.Service;

public class RemoveJobListener implements RoutesChangedEventListener<RemoveJob>{

	@Override
	public void sendRouteChangedEvent(RemoveJob event) {
		
		
	}
	
	public static void main(String[] args) {
		RouteChangedEventListeners listeners = new RouteChangedEventListeners();
		listeners.addRouteChangedEventListener(new RemoveJobListener());
		listeners.sendChangeEvent(new RemoveJob(Service.Builder.newInstance("j", 1).build()));
	}

	@Override
	public Class<RemoveJob> getEventType() {
		return RemoveJob.class;
	}



}
