package JosephMain;
import battlecode.common.*;

import java.util.ArrayList;
import java.util.Random;

public strictfp class TaskGarden2 extends Task {
    
    @Override
    public void runTurn() throws GameActionException {
    	if(rc.getRoundNum() > 11){
        	if(rc.getTeamBullets() >= GameConstants.BULLET_TREE_COST){
        		if(rc.canPlantTree(Direction.getNorth())){
            		rc.plantTree(Direction.getNorth());
            	} else if(rc.canPlantTree(Direction.getSouth())){
            		rc.plantTree(Direction.getSouth());
            	} else if(rc.canPlantTree(Direction.getEast())){
            		rc.plantTree(Direction.getEast());
            	} else if(rc.canPlantTree(Direction.getWest())){
            		rc.plantTree(Direction.getWest());
            	}
        	}
    	}
    	
    	if(rc.canWater()){
    		TreeInfo[] info_set = rc.senseNearbyTrees(1.5f, rc.getTeam());
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
