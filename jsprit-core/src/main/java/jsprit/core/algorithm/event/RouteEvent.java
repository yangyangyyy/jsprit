package jsprit.core.algorithm.event;

public interface RouteEvent {
	
	public Class<? extends RouteEvent> getType();
}
