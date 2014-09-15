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


import jsprit.core.problem.Capacity;

/**
 * Implementation of {@link VehicleType}.
 * 
 * <p>Two vehicle-types are equal if they have the same typeId.
 * 
 * @author schroeder
 *
 */
public class VehicleTypeImpl implements VehicleType {
	
	/**
	 * CostParameter consisting of fixed cost parameter, time-based cost parameter and distance-based cost parameter.
	 * 
	 * @author schroeder
	 *
	 */
	public static class VehicleCostParams {

        static class Builder {

            public static Builder newInstance(){ return new Builder(); }

            private Builder(){}

            private double fixedCostsParameter;

            private double transportDistanceParameter;

            private double transportTimeParameter;

            private double waitingTimeParameter;

            private double serviceTimeParameter;

            public Builder setFixedCostsParameter(double fixedCostsParameter){
                this.fixedCostsParameter = fixedCostsParameter;
                return this;
            }

            public Builder setTransportDistanceParameter(double transportDistanceParameter) {
                this.transportDistanceParameter = transportDistanceParameter;
                return this;
            }

            public Builder setTransportTimeParameter(double transportTimeParameter){
                this.transportTimeParameter = transportTimeParameter;
                return this;
            }

            public Builder setWaitingParameter(double waitingTimeParameter){
                this.waitingTimeParameter = waitingTimeParameter;
                return this;
            }

            public Builder setServiceTimeParameter(double serviceTimeParameter){
                this.serviceTimeParameter = serviceTimeParameter;
                return this;
            }

            public VehicleCostParams build(){
                return new VehicleCostParams(this);
            }

        }

        public static VehicleTypeImpl.VehicleCostParams newInstance(double fix, double perTimeUnit,double perDistanceUnit){
			return new VehicleCostParams(fix, perTimeUnit, perDistanceUnit);
		}
	
		public final double fix;

		public final double perTimeUnit;

		public final double perDistanceUnit;

        public double waitingTimeParameter = 0.;

        private double serviceTimeParameter = 0.;

		private VehicleCostParams(double fix, double perTimeUnit,double perDistanceUnit) {
			super();
			this.fix = fix;
			this.perTimeUnit = perTimeUnit;
			this.perDistanceUnit = perDistanceUnit;
		}

        private VehicleCostParams(Builder builder){
            fix = builder.fixedCostsParameter;
            perTimeUnit = builder.transportTimeParameter;
            perDistanceUnit = builder.transportDistanceParameter;
            waitingTimeParameter = builder.waitingTimeParameter;
            serviceTimeParameter = builder.serviceTimeParameter;
        }
		
		@Override
		public String toString() {
			return "[fixed="+fix+"][perTime="+perTimeUnit+"][perDistance="+perDistanceUnit+"]";
		}

        public double getWaitingTimeParameter() {
            return waitingTimeParameter;
        }

        public double getTransportDistanceParameter() {
            return perDistanceUnit;
        }

        public double getTransportTimeParameter() {
            return perTimeUnit;
        }

        public double getServiceTimeParameter() {
            return serviceTimeParameter;
        }
    }

	/**
	 * Builder that builds the vehicle-type.
	 * 
	 * @author schroeder
	 *
	 */
	public static class Builder{

        public static VehicleTypeImpl.Builder newInstance(String id) {
			if(id==null) throw new IllegalStateException();
			return new Builder(id);
		}
		
		private String id;

//        private int capacity = 0;

        private double maxVelo = Double.MAX_VALUE;

		private Capacity.Builder capacityBuilder = Capacity.Builder.newInstance();
		
		private Capacity capacityDimensions = null;
		
		private boolean dimensionAdded = false;

        private VehicleCostParams.Builder costParameterBuilder = VehicleCostParams.Builder.newInstance();

        private VehicleCostParams costParameter;

		private Builder(String id) {
            this.id = id;
            costParameterBuilder.setTransportDistanceParameter(1.);
		}

		/**
		 * Sets the maximum velocity this vehicle-type can go [in meter per seconds].
		 * 
		 * @param inMeterPerSeconds meter / second
		 * @return this builder
		 * @throws IllegalStateException if velocity is smaller than zero
		 */
		public VehicleTypeImpl.Builder setMaxVelocity(double inMeterPerSeconds){ 
			if(inMeterPerSeconds < 0.0) throw new IllegalStateException("velocity cannot be smaller than zero");
			this.maxVelo = inMeterPerSeconds; return this; 
		}
		
		/**
		 * Sets the fixed costs of the vehicle-type.
		 * 
		 * <p>by default it is 0.
		 * 
		 * @param fixedCost fixed costs
		 * @return this builder
		 * @throws IllegalStateException if fixedCost is smaller than zero
		 */
		public VehicleTypeImpl.Builder setFixedCost(double fixedCost) { 
			if(fixedCost < 0.0) throw new IllegalStateException("fixed costs cannot be smaller than zero");
			costParameterBuilder.setFixedCostsParameter(fixedCost);
//            this.fixedCost = fixedCost;
			return this; 
		}

		/**
		 * Sets the cost per distance unit, for instance € per meter.
		 * 
		 * <p>by default it is 1.0
		 * 
		 * @param perDistance distance costs
		 * @return this builder
		 * @throws IllegalStateException if perDistance is smaller than zero
		 */
		public VehicleTypeImpl.Builder setCostPerDistance(double perDistance){ 
			if(perDistance < 0.0) throw new IllegalStateException("cost per distance must not be smaller than zero");
			costParameterBuilder.setTransportDistanceParameter(perDistance);
//            this.perDistance = perDistance;
			return this; 
		}

		/**
		 * Sets cost per time unit, for instance € per second.
		 * 
		 * <p>by default it is 0.0 
		 * 
		 * @param perTime time costs
		 * @return this builder
		 * @throws IllegalStateException if costPerTime is smaller than zero
		 */
		public VehicleTypeImpl.Builder setCostPerTime(double perTime){ 
			if(perTime < 0.0) throw new IllegalStateException();
			costParameterBuilder.setTransportTimeParameter(perTime);
			return this; 
		}
		
		/**
		 * Builds the vehicle-type.
		 * 
		 * @return VehicleTypeImpl
		 */
		public VehicleTypeImpl build(){
			if(capacityDimensions == null){
				capacityDimensions = capacityBuilder.build();
			}
            costParameter = costParameterBuilder.build();
			return new VehicleTypeImpl(this);
		}

		/**
		 * Adds a capacity dimension.
		 * 
		 * @param dimIndex dimension index
		 * @param dimVal dimension value
		 * @return the builder
		 * @throws IllegalArgumentException if dimVal < 0
		 * @throws IllegalStateException if capacity dimension is already set
		 */
		public Builder addCapacityDimension(int dimIndex, int dimVal) {
			if(dimVal<0) throw new IllegalArgumentException("capacity value cannot be negative");
			if(capacityDimensions != null) throw new IllegalStateException("either build your dimension with build your dimensions with " +
					"addCapacityDimension(int dimIndex, int dimVal) or set the already built dimensions with .setCapacityDimensions(Capacity capacity)." +
					"You used both methods.");
			dimensionAdded = true;
			capacityBuilder.addDimension(dimIndex,dimVal);
			return this;
		}

		/**
		 * Sets capacity dimensions.
		 * 
		 * <p>Note if you use this you cannot use <code>addCapacityDimension(int dimIndex, int dimVal)</code> anymore. Thus either build
		 * your dimensions with <code>addCapacityDimension(int dimIndex, int dimVal)</code> or set the already built dimensions with
		 * this method.
		 * 
		 * @param capacity capacity
		 * @return this builder
		 * @throws IllegalStateException if capacityDimension has already been added
		 */
		public Builder setCapacityDimensions(Capacity capacity){
			if(dimensionAdded) throw new IllegalStateException("either build your dimension with build your dimensions with " +
					"addCapacityDimension(int dimIndex, int dimVal) or set the already built dimensions with .setCapacityDimensions(Capacity capacity)." +
					"You used both methods.");
			this.capacityDimensions = capacity;
			return this;
		}

        /**
         * Sets parameter for waiting time.
         *
         * @param waitingTimeParameter waiting time parameter
         * @return the builder
         * @throws java.lang.IllegalStateException if waitingTimeParameter < 0.
         */
        public Builder setWaitingTimeParameter(double waitingTimeParameter) {
            if(waitingTimeParameter < 0.0) throw new IllegalStateException();
            costParameterBuilder.setWaitingParameter(waitingTimeParameter);
            return this;
        }

        /**
         * Sets transportDistanceParameter.
         * @param transportDistanceParameter transport distance parameter
         * @return the builder
         * @throws java.lang.IllegalStateException if transportDistanceParameter < 0.
         */
        public Builder setTransportDistanceParameter(double transportDistanceParameter) {
            if(transportDistanceParameter < 0.0) throw new IllegalStateException();
            costParameterBuilder.setTransportDistanceParameter(transportDistanceParameter);
            return this;
        }

        /**
         * Sets transportTimeParameter.
         * @param transportTimeParameter transport time parameter
         * @return the builder
         * @throws java.lang.IllegalStateException if transportTimeParameter < 0.
         */
        public Builder setTransportTimeParameter(double transportTimeParameter) {
            if(transportTimeParameter < 0.0) throw new IllegalStateException();
            costParameterBuilder.setTransportTimeParameter(transportTimeParameter);
            return this;
        }

        /**
         * Sets serviceTimeParameter.
         *
         * @param serviceTimeParameter serviceTimeParameter
         * @return the builder
         * @throws java.lang.IllegalStateException if serviceTimeParameter < 0.
         */
        public Builder setServiceTimeParameter(double serviceTimeParameter) {
            if(serviceTimeParameter < 0.0) throw new IllegalStateException();
            costParameterBuilder.setServiceTimeParameter(serviceTimeParameter);
            return this;
        }
    }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((typeId == null) ? 0 : typeId.hashCode());
		return result;
	}

	/**
	 * Two vehicle-types are equal if they have the same vehicleId.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleTypeImpl other = (VehicleTypeImpl) obj;
		if (typeId == null) {
			if (other.typeId != null)
				return false;
		} else if (!typeId.equals(other.typeId))
			return false;
		return true;
	}

	private final String typeId;

	private final VehicleTypeImpl.VehicleCostParams vehicleCostParams;
	
	private final Capacity capacityDimensions;

	private final double maxVelocity;

	private VehicleTypeImpl(VehicleTypeImpl.Builder builder){
		typeId = builder.id;
		maxVelocity = builder.maxVelo;
		vehicleCostParams = builder.costParameter;
		capacityDimensions = builder.capacityDimensions;
	}

	/* (non-Javadoc)
	 * @see basics.route.VehicleType#getTypeId()
	 */
	@Override
	public String getTypeId() {
		return typeId;
	}

	/* (non-Javadoc)
	 * @see basics.route.VehicleType#getVehicleCostParams()
	 */
	@Override
	public VehicleTypeImpl.VehicleCostParams getVehicleCostParams() {
		return vehicleCostParams;
	}

	@Override
	public String toString() {
		return "[typeId="+typeId+"][capacity="+capacityDimensions+"][costParameter=" + vehicleCostParams + "]";
	}

	@Override
	public double getMaxVelocity() {
		return maxVelocity;
	}

	@Override
	public Capacity getCapacityDimensions() {
		return capacityDimensions;
	}
}
