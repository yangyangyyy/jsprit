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
import jsprit.core.problem.job.Job;
import jsprit.core.problem.job.Service;
import jsprit.core.problem.solution.route.VehicleRoute;
import jsprit.core.problem.solution.route.activity.TimeWindow;
import jsprit.core.problem.vehicle.VehicleImpl;
import jsprit.core.util.Coordinate;
import jsprit.core.util.CostFactory;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UpdateStartTimesTest {

    VehicleRoute route0;

    VehicleRoute route1;

    VehicleRoute route2;

    VehicleRoutingTransportCosts euclidean;

    UpdateStartTimes updateStartTimes;

    @Before
    public void doBefore(){
        VehicleImpl vehicle = VehicleImpl.Builder.newInstance("v").setStartLocationCoordinate(Coordinate.newInstance(0,0)).build();
        route0 = VehicleRoute.emptyRoute();
        Service service1 = Service.Builder.newInstance("s1").setCoord(Coordinate.newInstance(10, 0)).setTimeWindow(TimeWindow.newInstance(20,30)).build();
        Service service2 = Service.Builder.newInstance("s2").setCoord(Coordinate.newInstance(20, 0)).setTimeWindow(TimeWindow.newInstance(20,30)).build();
        route1 = VehicleRoute.Builder.newInstance(vehicle).addService(service1).addService(service2).build();

        Service service3 = Service.Builder.newInstance("s3").setCoord(Coordinate.newInstance(15, 0)).setTimeWindow(TimeWindow.newInstance(20,30)).build();
        Service service4 = Service.Builder.newInstance("s4").setCoord(Coordinate.newInstance(20, 0)).setTimeWindow(TimeWindow.newInstance(20,30)).build();
        route2 = VehicleRoute.Builder.newInstance(vehicle).addService(service3).addService(service4).build();

        euclidean = CostFactory.createEuclideanCosts();
        updateStartTimes = new UpdateStartTimes(euclidean);
    }

    @Test
    public void whenInsertionStarts_itShouldDealWithEmptyRoute(){
        List<VehicleRoute> routes = new ArrayList<VehicleRoute>();
        routes.add(route0);
        routes.add(route1);
        routes.add(route2);

        updateStartTimes.informInsertionStarts(routes, Collections.<Job>emptyList());
        Assert.assertEquals(0.,route0.getDepartureTime(),0.01);
        Assert.assertEquals(0.,route0.getStart().getEndTime(),0.01);
    }

    @Test
    public void whenInsertionStarts_itShouldUpdateRoute1(){
        List<VehicleRoute> routes = new ArrayList<VehicleRoute>();
        routes.add(route0);
        routes.add(route1);
        routes.add(route2);

        updateStartTimes.informInsertionStarts(routes, Collections.<Job>emptyList());

        Assert.assertEquals(10.,route1.getDepartureTime(),0.01);
        Assert.assertEquals(10.,route1.getStart().getEndTime(),0.01);
    }

    @Test
    public void whenInsertionStarts_itShouldUpdateRoute2(){
        List<VehicleRoute> routes = new ArrayList<VehicleRoute>();
        routes.add(route0);
        routes.add(route1);
        routes.add(route2);

        updateStartTimes.informInsertionStarts(routes, Collections.<Job>emptyList());

        Assert.assertEquals(5.,route2.getDepartureTime(),0.01);
        Assert.assertEquals(5.,route2.getStart().getEndTime(),0.01);
    }

    @Test
    public void whenJobHasBeenInserted_itShouldDealWithEmptyRoute(){
        updateStartTimes.informJobInserted(null,route0,0.,0.);

        Assert.assertEquals(0.,route0.getDepartureTime(),0.01);
        Assert.assertEquals(0.,route0.getStart().getEndTime(),0.01);
    }

    @Test
    public void whenJobHasBeenInserted_itShouldUpdateRoute1(){
        updateStartTimes.informJobInserted(null,route1,0.,0.);

        Assert.assertEquals(10.,route1.getDepartureTime(),0.01);
        Assert.assertEquals(10.,route1.getStart().getEndTime(),0.01);
    }

    @Test
    public void whenJobHasBeenInserted_itShouldUpdateRoute2(){
        updateStartTimes.informJobInserted(null,route2,0.,0.);

        Assert.assertEquals(5.,route2.getDepartureTime(),0.01);
        Assert.assertEquals(5.,route2.getStart().getEndTime(),0.01);
    }
}
