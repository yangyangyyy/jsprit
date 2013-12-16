package jsprit.core.algorithm.events;

public interface RoutesChangedEventListener<T extends RouteChangedEvent> {

	public void sendRouteChangedEvent(T event);
	
	public Class<T> getEventType();
	
}
