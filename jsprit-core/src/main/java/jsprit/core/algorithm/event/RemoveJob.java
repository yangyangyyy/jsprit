package jsprit.core.algorithm.event;

import java.util.Collection;

import jsprit.core.problem.job.Job;
import jsprit.core.problem.solution.route.VehicleRoute;

public class RemoveJob implements RouteEvent{
	
	private Job job;
	
	private Collection<VehicleRoute> routes;

	public RemoveJob(Collection<VehicleRoute> routes, Job job) {
		super();
		this.job = job;
		this.routes = routes;
	}

	/**
	 * @return the job
	 */
	public Job getJob() {
		return job;
	}

	/**
	 * @return the routes
	 */
	public Collection<VehicleRoute> getRoutes() {
		return routes;
	}

	@Override
	public Class<? extends RouteEvent> getType() {
		return RemoveJob.class;
	}
	
	

}
