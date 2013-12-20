package jsprit.core.algorithm.event;

import jsprit.core.problem.solution.route.VehicleRoute;

public class RouteChanged implements RouteEvent{

	private VehicleRoute route;
	
	public RouteChanged(VehicleRoute route) {
		this.route = route;
	}

	/**
	 * @return the route
	 */
	public VehicleRoute getRoute() {
		return route;
	}

	@Override
	public Class<? extends RouteEvent> getType() {
		return RouteChanged.class;
	}

}
