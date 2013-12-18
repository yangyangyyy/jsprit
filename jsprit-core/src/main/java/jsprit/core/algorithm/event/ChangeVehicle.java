package jsprit.core.algorithm.event;

import jsprit.core.problem.solution.route.VehicleRoute;
import jsprit.core.problem.vehicle.Vehicle;

public class ChangeVehicle implements RouteChangedEvent{
	
	private VehicleRoute vehicleRoute;
	
	private Vehicle newVehicle;

	public ChangeVehicle(VehicleRoute vehicleRoute, Vehicle newVehicle) {
		super();
		this.vehicleRoute = vehicleRoute;
		this.newVehicle = newVehicle;
	}

	/**
	 * @return the vehicleRoute
	 */
	public VehicleRoute getVehicleRoute() {
		return vehicleRoute;
	}

	/**
	 * @return the newVehicle
	 */
	public Vehicle getNewVehicle() {
		return newVehicle;
	}
	
	

}
