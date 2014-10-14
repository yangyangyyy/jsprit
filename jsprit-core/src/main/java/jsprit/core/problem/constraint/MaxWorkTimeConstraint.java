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

package jsprit.core.problem.constraint;


import jsprit.core.algorithm.state.InternalStates;
import jsprit.core.algorithm.state.StateManager;
import jsprit.core.problem.misc.JobInsertionContext;
import jsprit.core.problem.solution.route.activity.TourActivity;

class MaxWorkTimeConstraint implements HardActivityConstraint{

    private StateManager stateManager;

    MaxWorkTimeConstraint(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public ConstraintsStatus fulfilled(JobInsertionContext iFacts, TourActivity prevAct, TourActivity newAct, TourActivity nextAct, double prevActDepTime) {
        double vehicle_operation_time = iFacts.getNewVehicle().getMaxOperationTime();
        double currentWorkTime = stateManager.getRouteState(iFacts.getRoute(), InternalStates.WORK_TIME, Double.class);
        double new_operation_time = getActivityEnd(nextAct) - prevActDepTime;
        double old_operation_time = nextAct.getEndTime() - prevAct.getEndTime();
        double additional_operation_time =  new_operation_time - old_operation_time;

        if(currentWorkTime + additional_operation_time > vehicle_operation_time){
            return ConstraintsStatus.NOT_FULFILLED;
        }
        return ConstraintsStatus.FULFILLED;
    }

}
