package JosephMain;
import battlecode.common.*;

import java.util.ArrayList;
import java.util.Random;

public strictfp class TaskGarden extends Task {
    @SuppressWarnings("unused")
    private Direction[] targets;
    private boolean[] treesAlreadyPlanted;
    private int treeAmt;
    private MapLocation[] treeLocations;
    
    @Override
    public void runTurn() throws GameActionException {
    	//Try to plant a tree
    	int treePlant = getTreeToPlant();
    	if(treePlant != -1){
    		if(rc.getTeamBullets() >= GameConstants.BULLET_TREE_COST && rc.canPlantTree(targets[treePlant])){
        		rc.plantTree(targets[treePlant]);
        		treesAlreadyPlanted[treePlant] = true;
        	}
    	}
    	
    	if(rc.canWater()){
    		TreeInfo[] info_set = rc.senseNearbyTrees(1.5f, rc.getTeam());
    		int lowest_health_id = getMinValueOfTreeIDLowestHealth(info_set);
    		if(rc.canWater(lowest_health_id)){
    			rc.water(lowest_health_id);
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
    
    //Returns however many random directions to help determine which way to plant and water trees in the long term
    public Direction[] computeRandomTrees(){
		Random randomGenerator = new Random();
    	Direction[] temp_targets = new Direction[4];
    	//Check direction validity
    	ArrayList<Direction> remaining_directions = new ArrayList<Direction>();
    	if(rc.canPlantTree(Direction.getNorth())){
    		remaining_directions.add(Direction.getNorth());
    	}
    	if(rc.canPlantTree(Direction.getSouth())){
    		remaining_directions.add(Direction.getSouth());
    	}
    	if(rc.canPlantTree(Direction.getEast())){
    		remaining_directions.add(Direction.getEast());
    	}
    	if(rc.canPlantTree(Direction.getWest())){
    		remaining_directions.add(Direction.getWest());
    	}
        int i = 0;
        while(remaining_directions.size() > 0){
        	int chosen = randomGenerator.nextInt(remaining_directions.size());
        	temp_targets[i] = remaining_directions.get(chosen);
        	remaining_directions.remove(chosen);
        	i++;
        }
    	return temp_targets;
    }
    
    //Returns if a tree needs to be planted and in what direction
    public int getTreeToPlant(){
    	for(int j = 0; j<treeAmt; j++){
    		if(treesAlreadyPlanted[j] == false){
    			return j;
    		}
    	}
    	return -1;
    }
    
    public TaskGarden() {
    	super();
    	//Temporary code, will eventually strategically set down trees
    	this.targets = computeRandomTrees();
    	for(int a = 0; a < targets.length; a++){
    		if(targets[a] != null) treeAmt++;
    	}
    	this.treesAlreadyPlanted = new boolean[treeAmt];
    	for(int c = 0; c < treeAmt; c++){
    		treesAlreadyPlanted[c] = false;
    	}
    	treeLocations = new MapLocation[treeAmt];
    	for(int h = 0; h<treeAmt; h++){
    		treeLocations[h] = rc.getLocation().add(targets[h], 1.0f);
    	}
    }
    @Override
    public boolean isComplete() {
    	//No reassignment
    	return false;
    }
}
