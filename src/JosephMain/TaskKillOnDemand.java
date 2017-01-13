package JosephMain;
import battlecode.common.*;

public strictfp class TaskKillOnDemand extends Task {
    private MapLocation target;
    private Team enemy = rc.getTeam().opponent();
    private float threshold = 1;
    @Override
    public void runTurn() throws GameActionException {
    	if(rc.readBroadcast(7)==1) {
    		if(isComplete()) {
    			rc.broadcast(10, 1);
    		}
    		setTargetLocation(new MapLocation(rc.readBroadcast(8),rc.readBroadcast(9)));
    		//code copied from kill
    		if(rc.getLocation().distanceTo(target) > threshold){
        		Movement.tryMoveSwerve(new Direction(rc.getLocation(), target));
    		}
        	RobotInfo[] nearbyRobots = rc.senseNearbyRobots(-1, enemy);
        	// If there are some...
            if (nearbyRobots.length > 0) {
                // And we have enough bullets, and haven't attacked yet this turn...
                if (rc.canFireSingleShot()) {
                    // ...Then fire a bullet in the direction of the enemy.
                    rc.fireSingleShot(rc.getLocation().directionTo(nearbyRobots[0].location));
                }
            }
    	}
    	super.runTurn();
	}
    public TaskKillOnDemand() {
    	super();
    }
    public void setTargetLocation(MapLocation m) {
    	target = m;
    }
    @Override
    public boolean isComplete() {
    	if(target == null) return true;
    	if (rc.getLocation().distanceTo(target) <= threshold && rc.senseNearbyRobots(-1, enemy).length == 0) return true;
    	else return false;
    }
}
