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
    private ArrayList<Direction> existing_directions = new ArrayList<Direction>();
    
    @Override
    public void runTurn() throws GameActionException {
    	recompute();
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
    
    //Plants new trees if there is newfound space
    public void recompute(){
    	if(!existing_directions.contains(Direction.getNorth())){
    		if(rc.canPlantTree(Direction.getNorth())){
    			//Add the new direction that is now available
    			Direction[] new_targets = new Direction[treeAmt+1];
    			for(int a = 0; a<treeAmt; a++){
    				new_targets[a] = targets[a];
    			}
    			new_targets[treeAmt] = Direction.getNorth(); 
    			this.targets = new_targets;
    			
    			//Add another boolean (false) to trees already planted boolean
    			boolean[] treesAlreadyPlantedNew = new boolean[treesAlreadyPlanted.length + 1];
    	    	for(int c = 0; c < treeAmt; c++){
    	    		treesAlreadyPlantedNew[c] = treesAlreadyPlanted[c];
    	    	}
    	    	treesAlreadyPlantedNew[treeAmt] = false;
    	    	
    			//Increment tree amount
    			treeAmt++;
    			
    	    	//treeLocation adding
    	    	MapLocation[] treeLocationsNew = new MapLocation[treeAmt];
    	    	for(int h = 0; h<treeAmt; h++){
    	    		treeLocationsNew[h] = rc.getLocation().add(targets[h], 1.0f);
    	    	}
    	    	treeLocations = treeLocationsNew;
    	    	existing_directions.add(Direction.getNorth());
    		}
    	}
    	if(!existing_directions.contains(Direction.getSouth())){
    		if(rc.canPlantTree(Direction.getSouth())){
    			//Add the new direction that is now available
    			Direction[] new_targets = new Direction[treeAmt+1];
    			for(int a = 0; a<treeAmt; a++){
    				new_targets[a] = targets[a];
    			}
    			new_targets[treeAmt] = Direction.getSouth(); 
    			this.targets = new_targets;
    			
    			//Add another boolean (false) to trees already planted boolean
    			boolean[] treesAlreadyPlantedNew = new boolean[treesAlreadyPlanted.length + 1];
    	    	for(int c = 0; c < treeAmt; c++){
    	    		treesAlreadyPlantedNew[c] = treesAlreadyPlanted[c];
    	    	}
    	    	treesAlreadyPlantedNew[treeAmt] = false;
    	    	
    			//Increment tree amount
    			treeAmt++;
    			
    	    	//treeLocation adding
    	    	MapLocation[] treeLocationsNew = new MapLocation[treeAmt];
    	    	for(int h = 0; h<treeAmt; h++){
    	    		treeLocationsNew[h] = rc.getLocation().add(targets[h], 1.0f);
    	    	}
    	    	treeLocations = treeLocationsNew;
    	    	existing_directions.add(Direction.getSouth());
    		}
    	}
    	if(!existing_directions.contains(Direction.getEast())){
    		if(rc.canPlantTree(Direction.getEast())){
    			//Add the new direction that is now available
    			Direction[] new_targets = new Direction[treeAmt+1];
    			for(int a = 0; a<treeAmt; a++){
    				new_targets[a] = targets[a];
    			}
    			new_targets[treeAmt] = Direction.getEast(); 
    			this.targets = new_targets;
    			
    			//Add another boolean (false) to trees already planted boolean
    			boolean[] treesAlreadyPlantedNew = new boolean[treesAlreadyPlanted.length + 1];
    	    	for(int c = 0; c < treeAmt; c++){
    	    		treesAlreadyPlantedNew[c] = treesAlreadyPlanted[c];
    	    	}
    	    	treesAlreadyPlantedNew[treeAmt] = false;
    	    	
    			//Increment tree amount
    			treeAmt++;
    			
    	    	//treeLocation adding
    	    	MapLocation[] treeLocationsNew = new MapLocation[treeAmt];
    	    	for(int h = 0; h<treeAmt; h++){
    	    		treeLocationsNew[h] = rc.getLocation().add(targets[h], 1.0f);
    	    	}
    	    	treeLocations = treeLocationsNew;
    	    	existing_directions.add(Direction.getEast());
    		}
    	}
    	if(!existing_directions.contains(Direction.getWest())){
    		if(rc.canPlantTree(Direction.getWest())){
    			//Add the new direction that is now available
    			Direction[] new_targets = new Direction[treeAmt+1];
    			for(int a = 0; a<treeAmt; a++){
    				new_targets[a] = targets[a];
    			}
    			new_targets[treeAmt] = Direction.getWest(); 
    			this.targets = new_targets;
    			
    			//Add another boolean (false) to trees already planted boolean
    			boolean[] treesAlreadyPlantedNew = new boolean[treesAlreadyPlanted.length + 1];
    	    	for(int c = 0; c < treeAmt; c++){
    	    		treesAlreadyPlantedNew[c] = treesAlreadyPlanted[c];
    	    	}
    	    	treesAlreadyPlantedNew[treeAmt] = false;
    	    	
    			//Increment tree amount
    			treeAmt++;
    			
    	    	//treeLocation adding
    	    	MapLocation[] treeLocationsNew = new MapLocation[treeAmt];
    	    	for(int h = 0; h<treeAmt; h++){
    	    		treeLocationsNew[h] = rc.getLocation().add(targets[h], 1.0f);
    	    	}
    	    	treeLocations = treeLocationsNew;
    	    	existing_directions.add(Direction.getWest());
    		}
    	}
    	
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
        	existing_directions.add(remaining_directions.get(chosen));
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
