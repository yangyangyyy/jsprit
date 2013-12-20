package jsprit.core.algorithm.event;

public interface RouteEventListener<T extends RouteEvent> {

	public void sendRouteEvent(String eventSourceId, T event);
	
	public Class<T> getEventType();
	
}
