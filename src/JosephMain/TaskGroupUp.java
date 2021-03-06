package JosephMain;
import battlecode.common.*;

public strictfp class TaskGroupUp extends Task {
    private MapLocation target;
    
    private float threshold = 1; //DISTANCE TO TARGET LOCATION THAT IS ACCEPTABLE
    @Override
    public void runTurn() throws GameActionException {
    	if(!isComplete()) {
    		Movement.tryMoveSwerve(new Direction(rc.getLocation(), target));
    	}
    	super.runTurn();
	}
    public void setTarget(MapLocation m) {
    	target = m;
    }
    public void setThreshold(float t) {
    	threshold = t;
    }
    public float getThreshold() {
    	return threshold;
    }
    public TaskGroupUp() {
    	super();
    }
    @Override
    public boolean isComplete() {
    	if(target == null) return true;
    	if (rc.getLocation().distanceTo(target) <= threshold) return true;
    	
    	return false;
    }
}
