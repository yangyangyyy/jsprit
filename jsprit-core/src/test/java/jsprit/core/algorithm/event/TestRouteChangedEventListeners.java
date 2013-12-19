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
	
	static class InsertServiceListener implements RouteChangedEventListener<InsertService> {

		@Override
		public void sendRouteChangedEvent(InsertService event) {
			System.out.println("insertion service event. huhuhu.");
		}

		@Override
		public Class<InsertService> getEventType() {
			return InsertService.class;
		}

	}
	
	static class InsertShipmentListener implements RouteChangedEventListener<InsertShipment> {

		@Override
		public void sendRouteChangedEvent(InsertShipment event) {
			System.out.println("insertion of shipment event. lalala.");
		}

		@Override
		public Class<InsertShipment> getEventType() {
			return InsertShipment.class;
		}
		
	}
	
	@Test
	public void whenAddingListener_itIsAdded(){
		RouteChangedEventListeners listeners = new RouteChangedEventListeners();
		listeners.addRouteChangedEventListener(new InsertServiceListener());
		
		InsertService iService = new InsertService(mock(VehicleRoute.class),mock(Service.class),1);
		listeners.sendRouteChangedEvent(iService);
		
		assertEquals(1,listeners.getListener(InsertService.class).size());
	}
	
	@Test
	public void whenAddingNListener_theyAreAdded(){
		RouteChangedEventListeners listeners = new RouteChangedEventListeners();
		Random rand = new Random();
		int n = rand.nextInt(20);
		for(int i=0;i<n;i++)
			listeners.addRouteChangedEventListener(new InsertServiceListener());
		
		InsertService iService = new InsertService(mock(VehicleRoute.class),mock(Service.class),1);
		listeners.sendRouteChangedEvent(iService);
		
		assertEquals(n,listeners.getListener(InsertService.class).size());
	}
	
	@Test
	public void whenAddingListenersOfDiffType_theyAreAdded(){
		RouteChangedEventListeners listeners = new RouteChangedEventListeners();
		listeners.addRouteChangedEventListener(new InsertServiceListener());
		listeners.addRouteChangedEventListener(new InsertShipmentListener());
		
		InsertService iService = new InsertService(mock(VehicleRoute.class),mock(Service.class),1);
		listeners.sendRouteChangedEvent(iService);
		
		InsertShipment iShipment = new InsertShipment(mock(VehicleRoute.class),mock(Shipment.class),1,1);
		listeners.sendRouteChangedEvent(iShipment);
		
		assertEquals(1,listeners.getListener(InsertService.class).size());
		assertEquals(1,listeners.getListener(InsertShipment.class).size());
		
	}
	
	@Test
	public void whenAddingNullListener_itIsIgnored(){
		RouteChangedEventListeners listeners = new RouteChangedEventListeners();
		listeners.addRouteChangedEventListener(null);
		assertTrue(true);
	}
	
	@Test
	public void whenRemovingListener_itShouldBeRemoved(){
		RouteChangedEventListeners listeners = new RouteChangedEventListeners();
		InsertServiceListener iServiceListener = new InsertServiceListener();
		listeners.addRouteChangedEventListener(iServiceListener);
		InsertShipmentListener iShipmentListener = new InsertShipmentListener();
		listeners.addRouteChangedEventListener(iShipmentListener);
		
		assertEquals(1,listeners.getListener(InsertService.class).size());
		assertEquals(1,listeners.getListener(InsertShipment.class).size());
		
		listeners.removeRouteChangedEventListener(iServiceListener);
		
		assertEquals(0,listeners.getListener(InsertService.class).size());
		assertEquals(1,listeners.getListener(InsertShipment.class).size());
		
		listeners.removeRouteChangedEventListener(iShipmentListener);
		
		assertEquals(0,listeners.getListener(InsertService.class).size());
		assertEquals(0,listeners.getListener(InsertShipment.class).size());
	}
	
	@Test
	public void whenRequestingListenersWithNullArg_itShouldReturnAnEmptyList(){
		RouteChangedEventListeners listeners = new RouteChangedEventListeners();
		
		assertTrue(listeners.getListener(null).isEmpty());
	}
	
	@Test
	public void whenCollectingDiffEventTypes_theyShouldBeRecognizedCorrectly(){
		RouteChangedEventListeners listeners = new RouteChangedEventListeners();
		InsertServiceListener iServiceListener = new InsertServiceListener();
		listeners.addRouteChangedEventListener(iServiceListener);
		InsertShipmentListener iShipmentListener = new InsertShipmentListener();
		listeners.addRouteChangedEventListener(iShipmentListener);
		
		Collection<RouteChangedEvent> events = new ArrayList<RouteChangedEvent>();
		
		InsertService iService = new InsertService(mock(VehicleRoute.class),mock(Service.class),1);
		InsertShipment iShipment = new InsertShipment(mock(VehicleRoute.class),mock(Shipment.class),1,1);
		
		events.add(iService);
		events.add(iShipment);

		for(RouteChangedEvent e : events){
			listeners.sendRouteChangedEvent(e);
		}
	}
	

}
