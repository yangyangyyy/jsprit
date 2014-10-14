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

import jsprit.core.problem.cost.VehicleRoutingTransportCosts;
import jsprit.core.problem.job.Service;
import jsprit.core.problem.solution.route.VehicleRoute;
import jsprit.core.problem.solution.route.activity.TimeWindow;
import jsprit.core.problem.vehicle.VehicleImpl;
import jsprit.core.util.CostFactory;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;


public class StartTimeSchedulerTest {

    VehicleRoute route0;

    VehicleRoute route1;

    VehicleRoute route2;

    VehicleRoutingTransportCosts euclidean;

    StartTimeSchedulerImpl updateStartTimes;

    @Before
    public void doBefore(){
        VehicleImpl vehicle = VehicleImpl.Builder.newInstance("v").setStartLocationId("0,0").build();
        route0 = VehicleRoute.emptyRoute();
        Service service1 = Service.Builder.newInstance("s1").setLocationId("10, 0").setTimeWindow(TimeWindow.newInstance(20, 30)).build();
        Service service2 = Service.Builder.newInstance("s2").setLocationId("20, 0").setTimeWindow(TimeWindow.newInstance(20, 30)).build();
        route1 = VehicleRoute.Builder.newInstance(vehicle).addService(service1).addService(service2).build();

        Service service3 = Service.Builder.newInstance("s3").setLocationId("15, 0").setTimeWindow(TimeWindow.newInstance(20,30)).build();
        Service service4 = Service.Builder.newInstance("s4").setLocationId("20, 0").setTimeWindow(TimeWindow.newInstance(20, 30)).build();
        route2 = VehicleRoute.Builder.newInstance(vehicle).addService(service3).addService(service4).build();

        euclidean = CostFactory.createEuclideanCosts();
        updateStartTimes = new StartTimeSchedulerImpl(euclidean);
    }

    @Test
    public void whenJobHasBeenInserted_itShouldDealWithEmptyRoute(){
        updateStartTimes.scheduleStartTime(route0);

        Assert.assertEquals(0.,route0.getDepartureTime(),0.01);
        Assert.assertEquals(0.,route0.getStart().getEndTime(),0.01);
    }

    @Test
    public void whenJobHasBeenInserted_itShouldUpdateRoute1(){
        updateStartTimes.scheduleStartTime(route1);

        Assert.assertEquals(10.,route1.getDepartureTime(),0.01);
        Assert.assertEquals(10.,route1.getStart().getEndTime(),0.01);
    }

    @Test
    public void whenJobHasBeenInserted_itShouldUpdateRoute2(){
        updateStartTimes.scheduleStartTime(route2);

        Assert.assertEquals(5.,route2.getDepartureTime(),0.01);
        Assert.assertEquals(5.,route2.getStart().getEndTime(),0.01);
    }
}
