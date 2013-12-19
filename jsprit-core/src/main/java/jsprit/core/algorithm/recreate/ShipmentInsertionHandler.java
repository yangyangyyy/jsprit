package jsprit.core.algorithm.recreate;

import jsprit.core.algorithm.event.InsertShipment;
import jsprit.core.algorithm.event.RouteChangedEventListener;
import jsprit.core.problem.solution.route.activity.DefaultShipmentActivityFactory;
import jsprit.core.problem.solution.route.activity.TourActivity;
import jsprit.core.problem.solution.route.activity.TourShipmentActivityFactory;


class ShipmentInsertionHandler implements RouteChangedEventListener<InsertShipment>{

	private TourShipmentActivityFactory activityFactory = new DefaultShipmentActivityFactory();

	@Override
	public void sendRouteChangedEvent(InsertShipment event) {
		handle(event);
	}

	private void handle(InsertShipment event) {
		TourActivity pickupShipment = this.activityFactory.createPickup(event.getShipment());
		TourActivity deliverShipment = this.activityFactory.createDelivery(event.getShipment());
		event.getRoute().getTourActivities().addActivity(event.getDeliveryIndex(), deliverShipment);
		event.getRoute().getTourActivities().addActivity(event.getPickupIndex(), pickupShipment);
	}

	@Override
	public Class<InsertShipment> getEventType() {
		return InsertShipment.class;
	}

}
