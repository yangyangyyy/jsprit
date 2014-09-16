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

package jsprit.core.algorithm;


import jsprit.core.problem.VehicleRoutingProblem;
import jsprit.core.problem.job.Service;
import jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import jsprit.core.problem.solution.route.activity.TimeWindow;
import jsprit.core.problem.vehicle.VehicleImpl;
import jsprit.core.util.Coordinate;
import jsprit.core.util.Solutions;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Collection;

public class OptimizeStartTimesTest {

    @Test
    public void startTimesShouldBeOptimized(){

        Service service = Service.Builder.newInstance("s").setTimeWindow(TimeWindow.newInstance(40.,50.)).setCoord(Coordinate.newInstance(30., 0)).build();

        VehicleImpl vehicle = VehicleImpl.Builder.newInstance("v").setStartLocationCoordinate(Coordinate.newInstance(0,0)).build();

        VehicleRoutingProblem vrp = VehicleRoutingProblem.Builder.newInstance().addVehicle(vehicle).addJob(service).build();

        VehicleRoutingAlgorithmBuilder vraBuilder = new VehicleRoutingAlgorithmBuilder(vrp,"src/test/resources/algorithmConfig.xml");
        vraBuilder.addDefaultCostCalculators();
        vraBuilder.addCoreConstraints();
        vraBuilder.setOptimizeStartTimes(true);
        VehicleRoutingAlgorithm vra = vraBuilder.build();
        vra.setMaxIterations(100);
        Collection<VehicleRoutingProblemSolution> solutions = vra.searchSolutions();

        Assert.assertEquals(10., Solutions.bestOf(solutions).getRoutes().iterator().next().getDepartureTime());

    }
}
