package jsprit.core.algorithm.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RouteChangedEventListeners {

	private Map<Class<?>, List<?>> map = new HashMap<Class<?>, List<?>>();
	
	public <T extends RouteChangedEvent> void sendRouteChangedEvent(T event){
		@SuppressWarnings("unchecked")
		List<RouteChangedEventListener<T>> list = (List<RouteChangedEventListener<T>>) map.get(event.getClass());
		if(list == null) return;
		for(RouteChangedEventListener<T> l : list){
			l.sendRouteChangedEvent(event);
		}
	}
	
	/**
	 * Adds RouteChangedEventListener.
	 * 
	 * <p>null allowed but has no effect. However, the eventType in RouteChangedEventListener<T> must be specified (thus null is 
	 * not allowed). 
	 * @param l
	 */
	public <T extends RouteChangedEvent> void addRouteChangedEventListener(RouteChangedEventListener<T> l){
		if(l == null) return;
		if(l.getEventType() == null) throw new IllegalStateException("eventType in listener is unspecified. this must not be.");
		@SuppressWarnings("unchecked")
		List<RouteChangedEventListener<T>> list = (List<RouteChangedEventListener<T>>) map.get(l.getEventType());
		if(list == null){
			list = new ArrayList<RouteChangedEventListener<T>>();
			map.put(l.getEventType(), list);
		}
		list.add(l);
	}
	
	/**
	 * Removes RouteChangedEventListener.
	 * 
	 * <p>null allowed but has no effect. l.getEventType() must be specified, i.e. null is not allowed.
	 * 
	 * @param l
	 */
	public <T extends RouteChangedEvent> void removeRouteChangedEventListener(RouteChangedEventListener<T> l){
		if(l == null) return;
		if(l.getEventType() == null) throw new IllegalStateException("eventType in listener is unspecified. this must not be.");
		@SuppressWarnings("unchecked")
		List<RouteChangedEventListener<T>> list = (List<RouteChangedEventListener<T>>) map.get(l.getEventType());
		if(list != null){
			list.remove(l);
		}
	}
	
	/**
	 * Returns listeners for the specified eventType.
	 * 
	 * <p>null returns an empty list.
	 * @param eventType
	 * @return
	 */
	public <T extends RouteChangedEvent> Collection<RouteChangedEventListener<T>> getListener(Class<T> eventType){
		if(eventType == null) return Collections.emptyList();
		@SuppressWarnings("unchecked")
		List<RouteChangedEventListener<T>> list = (List<RouteChangedEventListener<T>>) map.get(eventType);
		if(list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
}
