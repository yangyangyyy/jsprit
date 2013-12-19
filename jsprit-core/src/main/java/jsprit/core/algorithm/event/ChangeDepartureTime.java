package jsprit.core.algorithm.event;

import jsprit.core.problem.solution.route.VehicleRoute;

public class ChangeDepartureTime implements RouteChangedEvent{
	
	private VehicleRoute vehicleRoute;
	
	private double newDepartureTime;

	public ChangeDepartureTime(VehicleRoute vehicleRoute,
			double newDepartureTime) {
		super();
		this.vehicleRoute = vehicleRoute;
		this.newDepartureTime = newDepartureTime;
	}

	/**
	 * @return the vehicleRoute
	 */
	public VehicleRoute getVehicleRoute() {
		return vehicleRoute;
	}

	/**
	 * @return the newDepartureTime
	 */
	public double getNewDepartureTime() {
		return newDepartureTime;
	}

	@Override
	public Class<? extends RouteChangedEvent> getType() {
		return ChangeDepartureTime.class;
	}
	
	
	

}
