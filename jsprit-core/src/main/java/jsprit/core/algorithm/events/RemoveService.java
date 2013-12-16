package jsprit.core.algorithm.events;

import jsprit.core.problem.job.Service;

public class RemoveService implements RouteChangedEvent{

	private Service service;

	public RemoveService(Service service) {
		super();
		this.service = service;
	}

	/**
	 * @return the service
	 */
	public Service getService() {
		return service;
	}
	
}
