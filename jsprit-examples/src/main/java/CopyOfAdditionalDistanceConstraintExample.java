
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



import jsprit.analysis.toolbox.AlgorithmEventsRecorder;
import jsprit.analysis.toolbox.AlgorithmEventsViewer;
import jsprit.analysis.toolbox.GraphStreamViewer;
import jsprit.analysis.toolbox.Plotter;
import jsprit.core.algorithm.VehicleRoutingAlgorithm;
import jsprit.core.algorithm.VehicleRoutingAlgorithmBuilder;
import jsprit.core.algorithm.io.VehicleRoutingAlgorithms;
import jsprit.core.algorithm.state.StateId;
import jsprit.core.algorithm.state.StateManager;
import jsprit.core.algorithm.state.StateUpdater;
import jsprit.core.problem.VehicleRoutingProblem;
import jsprit.core.problem.VehicleRoutingProblem.FleetSize;
import jsprit.core.problem.constraint.ConstraintManager;
import jsprit.core.problem.constraint.HardActivityConstraint;
import jsprit.core.problem.constraint.SoftActivityConstraint;
import jsprit.core.problem.cost.VehicleRoutingTransportCosts;
import jsprit.core.problem.io.VrpXMLReader;
import jsprit.core.problem.job.Service;
import jsprit.core.problem.misc.JobInsertionContext;
import jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import jsprit.core.problem.solution.route.VehicleRoute;
import jsprit.core.problem.solution.route.activity.ActivityVisitor;
import jsprit.core.problem.solution.route.activity.TimeWindow;
import jsprit.core.problem.solution.route.activity.TourActivity;
import jsprit.core.problem.vehicle.VehicleImpl;
import jsprit.core.problem.vehicle.VehicleType;
import jsprit.core.problem.vehicle.VehicleTypeImpl;
import jsprit.core.reporting.SolutionPrinter;
import jsprit.core.util.Coordinate;
import jsprit.core.util.EuclideanDistanceCalculator;
import jsprit.core.util.Solutions;
import jsprit.core.util.VehicleRoutingTransportCostsMatrix;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

//import jsprit.core.problem.solution.route.state.StateFactory; //v1.3.1

public class CopyOfAdditionalDistanceConstraintExample {

    static class DistanceUpdater implements StateUpdater, ActivityVisitor {

        private final StateManager stateManager;

        private final VehicleRoutingTransportCostsMatrix costMatrix;

//        private final StateFactory.StateId distanceStateId;    //v1.3.1
        private final StateId distanceStateId; //head of development - upcoming release

        private VehicleRoute vehicleRoute;

        private double distance = 0.;

        private TourActivity prevAct;

//        public DistanceUpdater(StateFactory.StateId distanceStateId, StateManager stateManager, VehicleRoutingTransportCostsMatrix costMatrix) { //v1.3.1
            public DistanceUpdater(StateId distanceStateId, StateManager stateManager, VehicleRoutingTransportCostsMatrix transportCosts) { //head of development - upcoming release (v1.4)
            this.costMatrix = transportCosts;
            this.stateManager = stateManager;
            this.distanceStateId = distanceStateId;
        }

        @Override
        public void begin(VehicleRoute vehicleRoute) {
            distance = 0.;
            prevAct = vehicleRoute.getStart();
            this.vehicleRoute = vehicleRoute;
        }

        @Override
        public void visit(TourActivity tourActivity) {
            distance += getDistance(prevAct,tourActivity);
            prevAct = tourActivity;
        }

        @Override
        public void finish() {
            distance += getDistance(prevAct,vehicleRoute.getEnd());
//            stateManager.putTypedRouteState(vehicleRoute,distanceStateId,Double.class,distance); //v1.3.1
            stateManager.putRouteState(vehicleRoute, distanceStateId, distance); //head of development - upcoming release (v1.4)
        }

        double getDistance(TourActivity from, TourActivity to){
            return costMatrix.getDistance(from.getLocationId(), to.getLocationId());
        }
    }

    static class DistanceConstraint implements HardActivityConstraint {

        private final StateManager stateManager;

        private final VehicleRoutingTransportCostsMatrix costsMatrix;

        private final double maxDistance;

//        private final StateFactory.StateId distanceStateId; //v1.3.1
        private final StateId distanceStateId; //head of development - upcoming release (v1.4)

//        DistanceConstraint(double maxDistance, StateFactory.StateId distanceStateId, StateManager stateManager, VehicleRoutingTransportCostsMatrix costsMatrix) { //v1.3.1
        DistanceConstraint(double maxDistance, StateId distanceStateId, StateManager stateManager, VehicleRoutingTransportCostsMatrix transportCosts) { //head of development - upcoming release (v1.4)
            this.costsMatrix = transportCosts;
            this.maxDistance = maxDistance;
            this.stateManager = stateManager;
            this.distanceStateId = distanceStateId;
        }

        @Override
        public ConstraintsStatus fulfilled(JobInsertionContext context, TourActivity prevAct, TourActivity newAct, TourActivity nextAct, double v) {
            double additionalDistance = getDistance(prevAct,newAct) + getDistance(newAct,nextAct) - getDistance(prevAct,nextAct);
            Double routeDistance = stateManager.getRouteState(context.getRoute(), distanceStateId, Double.class);
            if(routeDistance == null) routeDistance = 0.;
            double newRouteDistance = routeDistance + additionalDistance;
            if(newRouteDistance > maxDistance) {
                return ConstraintsStatus.NOT_FULFILLED;
            }
            else return ConstraintsStatus.FULFILLED;
        }

        double getDistance(TourActivity from, TourActivity to){
            return costsMatrix.getDistance(from.getLocationId(), to.getLocationId());
        }

    }
    public static List<Coordinate> createCoordinates(double center_x, double center_y, double radius, int steps){
        List<Coordinate> coords = new ArrayList<Coordinate>();
        for(double theta = 0; theta < 2*Math.PI; theta += 2*Math.PI /steps){
            double x = center_x + radius*Math.cos(theta);
            double y = center_y - radius*Math.sin(theta);
            coords.add(Coordinate.newInstance(x,y));
        }
        return coords;
    }
    static class ThrowAwayConstraint implements HardActivityConstraint {

    	VehicleRoutingTransportCostsMatrix matrix;
    	public ThrowAwayConstraint(VehicleRoutingTransportCostsMatrix matrix) {
    		this.matrix = matrix;
    	}
    	Random rand = new Random();
		@Override
		public ConstraintsStatus fulfilled(JobInsertionContext iFacts,
				TourActivity prevAct, TourActivity newAct,
				TourActivity nextAct, double prevActDepTime) {
			if ( matrix.getDistance(prevAct.getLocationId(), newAct.getLocationId()) +  
					matrix.getDistance(newAct.getLocationId(), nextAct.getLocationId())
							- matrix.getDistance(prevAct.getLocationId(), nextAct.getLocationId())
							 > 0 ) {
				if (  rand.nextFloat() > 0.5) {
					return ConstraintsStatus.NOT_FULFILLED_BREAK;
				} else {
					return ConstraintsStatus.FULFILLED;
				}
				
				
			}
			return ConstraintsStatus.FULFILLED;
		}
    	
    }
    
    static Map<String,Double> locationCosts = new HashMap<String,Double>() {
    	{
    		put("1", 100.0);
    		put("2", 200.0);
    		put("3", 3000.0);
    		
    	}
    };
    static class NodeCostSoftCons implements SoftActivityConstraint {

		@Override
		public double getCosts(JobInsertionContext iFacts,
				TourActivity prevAct, TourActivity newAct,
				TourActivity nextAct, double prevActDepTime) {
			return locationCosts.get(newAct.getLocationId());
//			return 0;
		}
    	
    }
    public static void main(String[] args) {

		VehicleType type = VehicleTypeImpl.Builder.newInstance("type").addCapacityDimension(0, 20000).setCostPerDistance(1).setCostPerTime(0).build();
		VehicleImpl vehicle = VehicleImpl.Builder.newInstance("vehicle").setStartLocationId("0").setStartLocationCoordinate(Coordinate.newInstance(0,0)).setType(type).build();
        int N_TARGET = 30	; // 30 nodes plus starting point
        Service [] services = new Service[N_TARGET];
        List<Coordinate> coords = createCoordinates(0,0, 20, N_TARGET );

        
        for(int i=0;i<N_TARGET;i++)
        	
		 services[i] = Service.Builder.newInstance("" + (i+1)).addSizeDimension(0, 1).setLocationId(""+(i+1)).setCoord(coords.get(i))
		 .setTimeWindow(TimeWindow.newInstance(0, 181000))
		 .build();

		
		
		/*
		 * Assume the following symmetric distance-matrix
		 * from,to,distance
		 * 0,1,10.0
		 * 0,2,20.0
		 * 0,3,5.0
		 * 1,2,4.0
		 * 1,3,1.0
		 * 2,3,2.0
		 * 
		 * and this time-matrix
		 * 0,1,5.0
		 * 0,2,10.0
		 * 0,3,2.5
		 * 1,2,2.0
		 * 1,3,0.5
		 * 2,3,1.0
		 */
		//define a matrix-builder building a symmetric matrix
        VehicleRoutingTransportCostsMatrix.Builder costMatrixBuilder = VehicleRoutingTransportCostsMatrix.Builder.newInstance(false);
        double BIG = 509990009;// Double.MAX_VALUE;
        double NOGO = BIG;
        double OK_COST= 100;
        double [][] matrix = new double[N_TARGET+1][N_TARGET+1];
        for(int i=0;i<=N_TARGET;i++)
        	for(int j=0;j<=N_TARGET;j++)
        		if (i == j)
        			matrix[i][j] = 0;
        		else if (i==0 || j==0 || i==25 )
        			matrix[i][j] = NOGO;
        		else
        			matrix[i][j] =  BIG;
        
        
        for(int i=0;i<=N_TARGET;i++) {
        	matrix[i][(i+1)%(N_TARGET+1)] = OK_COST;
//        	matrix[i][(i-1 + N_TARGET+1)%(N_TARGET+1)] = OK_COST;
        }
        

        // provide a direct shortcut from 7 to 23
        matrix[7][23] = 1;//OK_COST;
        
        // since this is a tour, I have let the algorithm account for 8--22, so going from 30 to 8, then from 8 to 22, then to 0
        matrix[30][8] = OK_COST;
        matrix[22][0] = OK_COST;

        // many times the algorithm fails to find the shortest path, so I give it more hint to make the "traditional route" 30-->0 impossible
        matrix[30][0] = 100000;



        for(int i=0;i<=N_TARGET;i++)
        	for(int j=0;j<=N_TARGET;j++)
        		if ( true || j!=i ) {
        			costMatrixBuilder.addTransportDistance("" +i, "" + j, matrix[i][j]);
        			costMatrixBuilder.addTransportTime("" +i, "" + j, 10);// matrix[i][j]);
        		}
        

		
		

		
        VehicleRoutingTransportCostsMatrix costMatrix = costMatrixBuilder.build() ;

        VehicleRoutingProblem.Builder builder = VehicleRoutingProblem.Builder.newInstance().setFleetSize(FleetSize.FINITE/*FleetSize.INFINITE*/).setRoutingCost(costMatrix)
		.addVehicle(vehicle);
        for(int i=0;i<N_TARGET;i++)
        	builder.addJob(services[i]);
		VehicleRoutingProblem vrp = builder.build();
		
        AlgorithmEventsRecorder eventsRecorder = new AlgorithmEventsRecorder(vrp,("output/events.dgs.gz"));
        eventsRecorder.setRecordingRange(0,50);

        
        VehicleRoutingAlgorithmBuilder vraBuilder = new VehicleRoutingAlgorithmBuilder(vrp, "input/algorithmConfig_fix_schrimpf.xml");

//        StateManager stateManager = new StateManager(vrp.getTransportCosts());  //v1.3.1
        StateManager stateManager = new StateManager(vrp); //head of development - upcoming release (v1.4)

//        StateFactory.StateId distanceStateId = StateFactory.createId("distance"); //v1.3.1
        StateId distanceStateId = stateManager.createStateId("distance"); //head of development - upcoming release (v1.4)
        stateManager.addStateUpdater(new DistanceUpdater(distanceStateId, stateManager, costMatrix));
        stateManager.updateLoadStates();
        stateManager.updateTimeWindowStates();


        ConstraintManager constraintManager = new ConstraintManager(vrp,stateManager);
//        constraintManager.addConstraint(new ThrowAwayConstraint(costMatrix), ConstraintManager.Priority.CRITICAL);
//        constraintManager.addConstraint(new DistanceConstraint(120.,distanceStateId,stateManager,costMatrix), ConstraintManager.Priority.CRITICAL);
//        constraintManager.addConstraint(new NodeCostSoftCons());
        constraintManager.addLoadConstraint();
        constraintManager.addTimeWindowConstraint();


        vraBuilder.addDefaultCostCalculators();
        vraBuilder.setStateAndConstraintManager(stateManager,constraintManager);

        VehicleRoutingAlgorithm vra = vraBuilder.build();
//		VehicleRoutingAlgorithm vra = VehicleRoutingAlgorithms.readAndCreateAlgorithm(vrp, "input/fastAlgo.xml");

//        vra.setNuOfIterations(250); //v1.3.1
        vra.setMaxIterations(2500); //head of development - upcoming release (v1.4)
        vra.addListener(eventsRecorder);

        Collection<VehicleRoutingProblemSolution> solutions = vra.searchSolutions();

        VehicleRoutingProblemSolution solution = Solutions.bestOf(solutions);
        SolutionPrinter.print(vrp, solution , SolutionPrinter.Print.VERBOSE);

//        new Plotter(vrp, Solutions.bestOf(solutions)).plot("output/plot","plot");
        
        
        new Plotter(vrp,solution).plot("output/circle.png","circleProblem");
        new GraphStreamViewer(vrp,solution).display();

        //only works with latest snapshot: 1.4.3
        AlgorithmEventsViewer viewer = new AlgorithmEventsViewer();
        viewer.setRuinDelay(160);
        viewer.setRecreationDelay(80);
        viewer.display("output/events.dgs.gz");
    }

    private static VehicleRoutingTransportCostsMatrix createMatrix(VehicleRoutingProblem.Builder vrpBuilder) {
        VehicleRoutingTransportCostsMatrix.Builder matrixBuilder = VehicleRoutingTransportCostsMatrix.Builder.newInstance(true);
        for(String from : vrpBuilder.getLocationMap().keySet()){
            for(String to : vrpBuilder.getLocationMap().keySet()){
                Coordinate fromCoord = vrpBuilder.getLocationMap().get(from);
                Coordinate toCoord = vrpBuilder.getLocationMap().get(to);
                double distance = EuclideanDistanceCalculator.calculateDistance(fromCoord, toCoord);
                matrixBuilder.addTransportDistance(from,to,distance);
                matrixBuilder.addTransportTime(from,to,(distance / 2.));
            }
        }
        return matrixBuilder.build();
    }


}
