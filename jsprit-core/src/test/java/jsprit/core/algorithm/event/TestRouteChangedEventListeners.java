package jsprit.core.algorithm.event;



import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

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
	

}
