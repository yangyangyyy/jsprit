package jsprit.core.algorithm.event;

public interface RouteChangedEventListener<T extends RouteChangedEvent> {

	public void sendRouteChangedEvent(T event);
	
	public Class<T> getEventType();
	
}
