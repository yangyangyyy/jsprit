package jsprit.core.algorithm.recreate;

import jsprit.core.algorithm.event.InsertService;
import jsprit.core.algorithm.event.RouteChangedEventListener;
import jsprit.core.problem.solution.route.activity.DefaultTourActivityFactory;
import jsprit.core.problem.solution.route.activity.TourActivityFactory;


class ServiceInsertionHandler implements RouteChangedEventListener<InsertService>{

	private TourActivityFactory activityFactory = new DefaultTourActivityFactory();
	
	@Override
	public void sendRouteChangedEvent(InsertService event) {
		handle(event);
	}

	private void handle(InsertService event) {
		event.getRoute().getTourActivities()
			.addActivity(event.getInsertionIndex(), this.activityFactory.createActivity(event.getService()));
	}

	@Override
	public Class<InsertService> getEventType() {
		return InsertService.class;
	}

}
