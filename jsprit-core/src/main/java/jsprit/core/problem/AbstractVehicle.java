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

package jsprit.core.problem;

import jsprit.core.problem.vehicle.Vehicle;
import jsprit.core.problem.vehicle.VehicleTypeKey;

/**
 * AbstractVehicle to handle indeces of vehicles.
 */
public abstract class AbstractVehicle implements Vehicle {



    public abstract static class AbstractTypeKey implements HasIndex {

        private int index;

        public int getIndex(){ return index; }

        public void setIndex(int index) { this.index = index; }

    }

    private int index;

    private VehicleTypeKey vehicleIdentifier;

    public int getIndex(){ return index; }

    protected void setIndex(int index){ this.index = index; }

    public VehicleTypeKey getVehicleTypeIdentifier(){
        return vehicleIdentifier;
    }

    protected void setVehicleIdentifier(VehicleTypeKey vehicleTypeIdentifier){
        this.vehicleIdentifier = vehicleTypeIdentifier;
    }
}
