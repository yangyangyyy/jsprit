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

import jsprit.core.algorithm.recreate.listener.InsertionStartsListener;
import jsprit.core.algorithm.recreate.listener.JobInsertedListener;
import jsprit.core.problem.cost.TransportTime;
import jsprit.core.problem.job.Job;
import jsprit.core.problem.solution.route.VehicleRoute;

import java.util.Collection;


class UpdateStartTimes implements StateUpdater, InsertionStartsListener, JobInsertedListener{

    private TransportTime transportTime;

    UpdateStartTimes(TransportTime transportTime) {
        this.transportTime = transportTime;
    }

    @Override
    public void informInsertionStarts(Collection<VehicleRoute> vehicleRoutes, Collection<Job> unassignedJobs) {
        for(VehicleRoute route : vehicleRoutes){
            if(!route.isEmpty()){

            }
        }
    }

    @Override
    public void informJobInserted(Job job2insert, VehicleRoute inRoute, double additionalCosts, double additionalTime) {

    }
}
