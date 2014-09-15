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

package jsprit.core.problem.vehicle;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VehicleTypeKeyTest {

    @Test
    public void typeIdentifierShouldBeEqual(){
        Vehicle v1 = VehicleImpl.Builder.newInstance("v1").setStartLocationId("start").addSkill("skill1").addSkill("skill2")
                .addSkill("skill3").build();
        Vehicle v2 = VehicleImpl.Builder.newInstance("v2").setStartLocationId("start").addSkill("skill2").addSkill("skill1")
                .addSkill("skill3").build();
        assertTrue(v1.getVehicleTypeIdentifier().equals(v2.getVehicleTypeIdentifier()));
    }

    @Test
    public void typeIdentifierShouldNotBeEqual(){
        Vehicle v1 = VehicleImpl.Builder.newInstance("v1").setStartLocationId("start").addSkill("skill1").addSkill("skill2")
                .build();
        Vehicle v2 = VehicleImpl.Builder.newInstance("v2").setStartLocationId("start").addSkill("skill2").addSkill("skill1")
                .addSkill("skill3").build();
        assertFalse(v1.getVehicleTypeIdentifier().equals(v2.getVehicleTypeIdentifier()));
    }

    @Test
    public void whenTwoVehiclesHaveDifferentMaxOperationTime_theyShouldNotBeEqual(){
        Vehicle v1 = VehicleImpl.Builder.newInstance("v1").setStartLocationId("start").setEarliestStart(10.).setLatestArrival(60.)
                .setMaxOperationTime(30.).build();

        Vehicle v2 = VehicleImpl.Builder.newInstance("v2").setStartLocationId("start").setEarliestStart(10.).setLatestArrival(60.)
                .setMaxOperationTime(40.).build();
        assertTrue(!v1.getVehicleTypeIdentifier().equals(v2.getVehicleTypeIdentifier()));
    }

    @Test
    public void whenTwoVehiclesHaveSameMaxOperationTime_theyShouldBeEqual(){
        Vehicle v1 = VehicleImpl.Builder.newInstance("v1").setStartLocationId("start").setEarliestStart(10.).setLatestArrival(60.)
                .setMaxOperationTime(30.).build();

        Vehicle v2 = VehicleImpl.Builder.newInstance("v2").setStartLocationId("start").setEarliestStart(10.).setLatestArrival(60.)
                .setMaxOperationTime(30.).build();
        assertTrue(v1.getVehicleTypeIdentifier().equals(v2.getVehicleTypeIdentifier()));
    }
}
