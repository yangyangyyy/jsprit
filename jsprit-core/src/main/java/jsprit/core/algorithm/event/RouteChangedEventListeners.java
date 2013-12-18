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
	
	public <T extends RouteChangedEvent> void addRouteChangedEventListener(RouteChangedEventListener<T> l){
		@SuppressWarnings("unchecked")
		List<RouteChangedEventListener<T>> list = (List<RouteChangedEventListener<T>>) map.get(l.getEventType());
		if(list == null){
			list = new ArrayList<RouteChangedEventListener<T>>();
			map.put(l.getEventType(), list);
		}
		list.add(l);
	}
	
	public <T extends RouteChangedEvent> void removeRouteChangedEventListener(RouteChangedEventListener<T> l){
		
	}
	
	public <T extends RouteChangedEvent> Collection<RouteChangedEventListener<T>> getListener(Class<T> eventType){
		@SuppressWarnings("unchecked")
		List<RouteChangedEventListener<T>> list = (List<RouteChangedEventListener<T>>) map.get(eventType);
		if(list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
	public static void main(String[] args) {
		RouteChangedEventListeners listeners = new RouteChangedEventListeners();
		
	}

}
