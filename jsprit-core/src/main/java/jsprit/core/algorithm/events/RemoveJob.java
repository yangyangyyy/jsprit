package jsprit.core.algorithm.events;

import jsprit.core.problem.job.Job;

public class RemoveJob implements RouteChangedEvent{
	
	private Job job;

	public RemoveJob(Job job) {
		super();
		this.job = job;
	}

	/**
	 * @return the job
	 */
	public Job getJob() {
		return job;
	}
	
	

}
