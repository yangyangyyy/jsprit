package jsprit.core.algorithm.event;

import jsprit.core.problem.job.Service;
import jsprit.core.problem.solution.route.VehicleRoute;

public class InsertService implements RouteEvent{

	private Service service;
	
	private VehicleRoute route;
	
	private int insertionIndex;
	
	public InsertService(VehicleRoute route2change, Service service, int insertionIndex) {
		this.service=service;
		this.route=route2change;
		this.insertionIndex=insertionIndex;
	}

	/**
	 * @return the insertionIndex
	 */
	public int getInsertionIndex() {
		return insertionIndex;
	}

	/**
	 * @return the service
	 */
	public Service getService() {
		return service;
	}

	/**
	 * @return the route
	 */
	public VehicleRoute getRoute() {
		return route;
	}

	@Override
	public Class<? extends RouteEvent> getType() {
		return InsertService.class;
	}
	
	

}
