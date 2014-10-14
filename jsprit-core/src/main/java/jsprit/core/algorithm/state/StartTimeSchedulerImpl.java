/*******************************************************************************
 * Copyright (C) 2014  Stefan Schroeder
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package jsprit.core.algorithm.state;

import jsprit.core.problem.cost.TransportTime;
import jsprit.core.problem.solution.route.VehicleRoute;
import jsprit.core.problem.solution.route.activity.TourActivity;

/**
 * Determines vehicle's departure so that it is has no waiting time at first activity.
 */
class StartTimeSchedulerImpl {

    private TransportTime transportTime;

    StartTimeSchedulerImpl(TransportTime transportTime) {
        this.transportTime = transportTime;
    }

    public void scheduleStartTime(VehicleRoute route) {
        if(!route.isEmpty()){
            double earliestDepartureTime = route.getVehicle().getEarliestDeparture();
            TourActivity firstActivity = route.getActivities().get(0);
            double tpTime_startToFirst = transportTime.getBackwardTransportTime(route.getStart().getLocationId(), firstActivity.getLocationId(),
                    earliestDepartureTime, null, route.getVehicle());
            double newDepartureTime = Math.max(earliestDepartureTime, firstActivity.getTheoreticalEarliestOperationStartTime()-tpTime_startToFirst);
            route.setVehicleAndDepartureTime(route.getVehicle(), newDepartureTime);
        }
    }

}
