package JosephMain;
import battlecode.common.*;

public strictfp class TaskKill extends Task {
    @SuppressWarnings("unused")
    private MapLocation target;
    private Team enemy = rc.getTeam().opponent();
    private float threshold = 1;
    @Override
    public void runTurn() throws GameActionException {
		if(rc.getLocation().distanceTo(target) > threshold){
    		Movement.tryMove(new Direction(rc.getLocation(), target));
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
    	super.runTurn();
	}
    public void setTargetLocation(MapLocation m) {
    	target = m;
    }
    public TaskKill() {
    	super();
    }
    @Override
    public boolean isComplete() {
    	if(target == null) return true;
    	if (rc.getLocation().distanceTo(target) <= threshold && rc.senseNearbyRobots(-1, enemy).length == 0) return true;
    	else return false;
    }
}
