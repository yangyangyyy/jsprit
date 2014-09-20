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
import jsprit.core.util.ActivityTimeTracker;

public class ActivityTimeSchedulerImpl implements ActivityTimeScheduler{

    private UpdateActivityTimes updateActivityTimes;

    private boolean optimizeStartTimes;

    private StartTimeScheduler startTimeScheduler;

    public ActivityTimeSchedulerImpl(TransportTime transportTime, ActivityTimeTracker.ActivityPolicy activityPolicy, boolean optimizeStartTimes) {
        this.updateActivityTimes = new UpdateActivityTimes(transportTime,activityPolicy);
        this.optimizeStartTimes = optimizeStartTimes;
        startTimeScheduler = new StartTimeScheduler(transportTime);
    }

    @Override
    public void begin(VehicleRoute route) {
        if(optimizeStartTimes) startTimeScheduler.scheduleStartTime(route);
        updateActivityTimes.begin(route);
    }

    @Override
    public void visit(TourActivity activity) {
        updateActivityTimes.visit(activity);
    }

    @Override
    public void finish() {
        updateActivityTimes.finish();
    }
}
