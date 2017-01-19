package JosephMain;
import java.util.Random;

import battlecode.common.*;

public strictfp class TaskGarden2 extends Task {
	
	boolean isInDanger = false;
	boolean spawned_guard = false;
	Direction guard_spawn_direction;
	Random rand = new Random();
	int guard_spawn_id;
	
    @Override
    public void runTurn() throws GameActionException {
    	//Wait until the initial gardeners spawn before planting trees
    	if(rc.getRoundNum() > 11){
    		//Only plant trees if it is safe around the gardener
    		RobotInfo[] info = rc.senseNearbyRobots(3, rc.getTeam().opponent());
    		for(int i = 0; i<info.length; i++){
    			if(info[i].getType() == RobotType.LUMBERJACK ||
    					info[i].getType() == RobotType.SOLDIER ||
    					info[i].getType() == RobotType.TANK){
    				isInDanger = true;
    				break;
    			}
    		}
    		if(!spawned_guard && rc.getTeamBullets() >= 100.0f){    		    
    			int random = rand.nextInt(3) + 1;
    			//for now random with 2:1 LJ to soldier ratio
    			if(random != 3) { //1 and 2
	    			if(rc.canBuildRobot(RobotType.LUMBERJACK, guard_spawn_direction)){
	    				rc.buildRobot(RobotType.LUMBERJACK, guard_spawn_direction);
	    			}
    			} else { //3
	    			if(rc.canBuildRobot(RobotType.SOLDIER, guard_spawn_direction)){
	    				rc.buildRobot(RobotType.SOLDIER, guard_spawn_direction);
	    			}    				
    			}
    		}
        	if(rc.getTeamBullets() >= GameConstants.BULLET_TREE_COST && !isInDanger){
        		for(int id = 0; id < 6 ; id++) {
        			if(rc.canPlantTree(getDirectionById(id))&& id != guard_spawn_id) {
        				rc.plantTree(getDirectionById(id));
        			}
        		}
        	}
    	}
    	
    	//Code to water the surrounding tree in plus formation with the lowest health, ensures max health at all times
    	if(rc.canWater()){
    		TreeInfo[] info_set = rc.senseNearbyTrees(1.2f, rc.getTeam());
    		if(info_set.length > 0){
    			int lowest_health_id = getMinValueOfTreeIDLowestHealth(info_set);
        		if(rc.canWater(lowest_health_id)){
        			rc.water(lowest_health_id);
        		}
    		}
    	}
    	
    	super.runTurn();
	}
    
    private int getMinValueOfTreeIDLowestHealth(TreeInfo[] info_set) {
        TreeInfo minValue = info_set[0];
        for (int i = 1; i < info_set.length; i++) {
            if (info_set[i].health < minValue.health) {
                minValue = info_set[i];
            }
        }
        return minValue.ID;
    }
    
    
    
    public TaskGarden2() {
    	super();
    	//Choose the direction closest to the direction towards the enemy archon location as the guard spawn direction
    	MapLocation[] archon_info = rc.getInitialArchonLocations(rc.getTeam().opponent());
    	MapLocation archon = archon_info[0];
    	Direction d = rc.getLocation().directionTo(archon);
    	Direction closest = Direction.getNorth();
    	int id = 0;
    	//Get the nearest 60 degree angle to direction "d"
    	System.out.println(d + " distance to archon");
    	for(int i=0; i<6; i++){
    		if(Math.abs(d.degreesBetween(closest)) > Math.abs(d.degreesBetween(getDirectionById(i)))){
    			closest = getDirectionById(i);
    			id = i;
    		}
    	}
    	guard_spawn_direction = closest;
    	guard_spawn_id = id;
    }
    
    private Direction getDirectionById(int id){
    	if(id == 0) return Direction.getNorth();
    	else if(id==1) return Direction.getNorth().rotateRightDegrees(60);
    	else if(id==2) return Direction.getNorth().rotateRightDegrees(120);
    	else if(id==3) return Direction.getSouth();
    	else if(id==4) return Direction.getSouth().rotateRightDegrees(60);
    	else if(id==5) return Direction.getSouth().rotateRightDegrees(120);
    	else{
    		System.out.println("OH NOOOO");
    		return Direction.getNorth();
    	}
    }
    
    @Override
    public boolean isComplete() {
    	//No reassignment
    	return false;
    }
}