package JosephMain;
import battlecode.common.*;

public strictfp class TaskGarden2 extends Task {
	
	boolean isInDanger = false;
	boolean spawned_guard = false;
	
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
    			if(rc.canBuildRobot(RobotType.LUMBERJACK, Direction.getNorth())){
            		rc.buildRobot(RobotType.LUMBERJACK, Direction.getNorth());
            	} else if(rc.canBuildRobot(RobotType.LUMBERJACK, Direction.getNorth().rotateRightDegrees(60))){
            		rc.canBuildRobot(RobotType.LUMBERJACK, Direction.getNorth().rotateRightDegrees(60));
            	} else if(rc.canBuildRobot(RobotType.LUMBERJACK, Direction.getNorth().rotateRightDegrees(120))){
            		rc.canBuildRobot(RobotType.LUMBERJACK, Direction.getNorth().rotateRightDegrees(120));
            	} else if(rc.canBuildRobot(RobotType.LUMBERJACK, Direction.getSouth())){
            		rc.buildRobot(RobotType.LUMBERJACK, Direction.getSouth());
            	} else if(rc.canBuildRobot(RobotType.LUMBERJACK, Direction.getSouth().rotateRightDegrees(60))){
            		rc.canBuildRobot(RobotType.LUMBERJACK, Direction.getSouth().rotateRightDegrees(60));
            	} else if(rc.canBuildRobot(RobotType.LUMBERJACK, Direction.getNorth().rotateRightDegrees(120))){
            		rc.canBuildRobot(RobotType.LUMBERJACK, Direction.getNorth().rotateRightDegrees(120));
            	}
    		}
        	if(rc.getTeamBullets() >= GameConstants.BULLET_TREE_COST && !isInDanger){
        		if(rc.canPlantTree(Direction.getNorth())){
            		rc.plantTree(Direction.getNorth());
            	} else if(rc.canPlantTree(Direction.getNorth().rotateRightDegrees(60))){
            		rc.plantTree(Direction.getNorth().rotateRightDegrees(60));
            	} else if(rc.canPlantTree(Direction.getNorth().rotateRightDegrees(120))){
            		rc.plantTree(Direction.getNorth().rotateRightDegrees(120));
            	} else if(rc.canPlantTree(Direction.getSouth())){
            		rc.plantTree(Direction.getSouth());
            	} else if(rc.canPlantTree(Direction.getSouth().rotateRightDegrees(60))){
            		rc.plantTree(Direction.getSouth().rotateRightDegrees(60));
            	} else if(rc.canPlantTree(Direction.getSouth().rotateRightDegrees(120))){
            		rc.plantTree(Direction.getSouth().rotateRightDegrees(120));
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
    }
    
    @Override
    public boolean isComplete() {
    	//No reassignment
    	return false;
    }
}
