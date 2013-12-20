package jsprit.core.algorithm.event;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import jsprit.core.problem.job.Service;
import jsprit.core.problem.job.Shipment;
import jsprit.core.problem.solution.route.VehicleRoute;

import org.junit.Test;

public class TestRouteChangedEventListeners {
	
	static class InsertServiceListener implements RouteEventListener<InsertService> {

		@Override
		public void sendRouteEvent(String eventSourceId, InsertService event) {
			System.out.println("insertion service event. huhuhu.");
		}

		@Override
		public Class<InsertService> getEventType() {
			return InsertService.class;
		}

	}
	
	static class InsertShipmentListener implements RouteEventListener<InsertShipment> {

		@Override
		public void sendRouteEvent(String eventSourceId, InsertShipment event) {
			System.out.println("insertion of shipment event. lalala.");
		}

		@Override
		public Class<InsertShipment> getEventType() {
			return InsertShipment.class;
		}
		
	}
	
	@Test
	public void whenAddingListener_itIsAdded(){
		RouteEventListeners listeners = new RouteEventListeners();
		listeners.addRouteEventListener(new InsertServiceListener());
		
		InsertService iService = new InsertService(mock(VehicleRoute.class),mock(Service.class),1);
		listeners.sendRouteEvent("source", iService);
		
		assertEquals(1,listeners.getRouteEventListeners(InsertService.class).size());
	}
	
	@Test
	public void whenAddingNListener_theyAreAdded(){
		RouteEventListeners listeners = new RouteEventListeners();
		Random rand = new Random();
		int n = rand.nextInt(20);
		for(int i=0;i<n;i++)
			listeners.addRouteEventListener(new InsertServiceListener());
		
		InsertService iService = new InsertService(mock(VehicleRoute.class),mock(Service.class),1);
		listeners.sendRouteEvent("source", iService);
		
		assertEquals(n,listeners.getRouteEventListeners(InsertService.class).size());
	}
	
	@Test
	public void whenAddingListenersOfDiffType_theyAreAdded(){
		RouteEventListeners listeners = new RouteEventListeners();
		listeners.addRouteEventListener(new InsertServiceListener());
		listeners.addRouteEventListener(new InsertShipmentListener());
		
		InsertService iService = new InsertService(mock(VehicleRoute.class),mock(Service.class),1);
		listeners.sendRouteEvent("source", iService);
		
		InsertShipment iShipment = new InsertShipment(mock(VehicleRoute.class),mock(Shipment.class),1,1);
		listeners.sendRouteEvent("source", iShipment);
		
		assertEquals(1,listeners.getRouteEventListeners(InsertService.class).size());
		assertEquals(1,listeners.getRouteEventListeners(InsertShipment.class).size());
		
	}
	
	@Test
	public void whenAddingNullListener_itIsIgnored(){
		RouteEventListeners listeners = new RouteEventListeners();
		listeners.addRouteEventListener(null);
		assertTrue(true);
	}
	
	@Test
	public void whenRemovingListener_itShouldBeRemoved(){
		RouteEventListeners listeners = new RouteEventListeners();
		InsertServiceListener iServiceListener = new InsertServiceListener();
		listeners.addRouteEventListener(iServiceListener);
		InsertShipmentListener iShipmentListener = new InsertShipmentListener();
		listeners.addRouteEventListener(iShipmentListener);
		
		assertEquals(1,listeners.getRouteEventListeners(InsertService.class).size());
		assertEquals(1,listeners.getRouteEventListeners(InsertShipment.class).size());
		
		listeners.removeRouteEventListener(iServiceListener);
		
		assertEquals(0,listeners.getRouteEventListeners(InsertService.class).size());
		assertEquals(1,listeners.getRouteEventListeners(InsertShipment.class).size());
		
		listeners.removeRouteEventListener(iShipmentListener);
		
		assertEquals(0,listeners.getRouteEventListeners(InsertService.class).size());
		assertEquals(0,listeners.getRouteEventListeners(InsertShipment.class).size());
	}
	
	@Test
	public void whenRequestingListenersWithNullArg_itShouldReturnAnEmptyList(){
		RouteEventListeners listeners = new RouteEventListeners();
		
		assertTrue(listeners.getRouteEventListeners(null).isEmpty());
	}
	
	@Test
	public void whenCollectingDiffEventTypes_theyShouldBeRecognizedCorrectly(){
		RouteEventListeners listeners = new RouteEventListeners();
		InsertServiceListener iServiceListener = new InsertServiceListener();
		listeners.addRouteEventListener(iServiceListener);
		InsertShipmentListener iShipmentListener = new InsertShipmentListener();
		listeners.addRouteEventListener(iShipmentListener);
		
		Collection<RouteEvent> events = new ArrayList<RouteEvent>();
		
		InsertService iService = new InsertService(mock(VehicleRoute.class),mock(Service.class),1);
		InsertShipment iShipment = new InsertShipment(mock(VehicleRoute.class),mock(Shipment.class),1,1);
		
		events.add(iService);
		events.add(iShipment);

		for(RouteEvent e : events){
			listeners.sendRouteEvent("source", e);
		}
	}
	

}
