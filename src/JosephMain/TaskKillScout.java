package JosephMain;
import battlecode.common.*;

/*
 * Task for scouts to find gardeners and kill them, report strategies and enemy unit positions
 */
public strictfp class TaskKillScout extends Task {
    private MapLocation target;
    private Team enemy = rc.getTeam().opponent();
    private float threshold = 1;
    @Override
    public void runTurn() throws GameActionException {
    	RobotInfo[] nearbyRobots = rc.senseNearbyRobots(-1, enemy);
    	MapLocation robotattacklocation = null;
    	boolean hasAlreadyChased = false;
    	// If there are some...
        if (nearbyRobots.length > 0) {
        	//Prioritize killing gardeners
        	for(int i=0; i<nearbyRobots.length; i++){
        		if(nearbyRobots[i].getType() == RobotType.GARDENER){
        			Movement.tryMove(new Direction(rc.getLocation(), nearbyRobots[i].getLocation()));
        			robotattacklocation = nearbyRobots[i].getLocation();
        			hasAlreadyChased = true;
        			break;
        		}
        	}
        	if(!hasAlreadyChased){
        		//Found a non-gardener
        		//For now, do nothing
        	} else{
        		if(robotattacklocation != null){
           		 // And we have enough bullets, and haven't attacked yet this turn...
            		if(rc.getLocation().isWithinDistance(robotattacklocation, 3.0f) && rc.canFireSingleShot()){
            			 // ...Then fire a bullet in the direction of the enemy.
                        rc.fireSingleShot(rc.getLocation().directionTo(nearbyRobots[0].location));
            		}
        		}
        	}
        } else{
        	//No robots detected around the scout
        	if(rc.getLocation().distanceTo(target) > threshold){
        		Movement.tryMove(new Direction(rc.getLocation(), target));
    		}
        }
    	super.runTurn();
	}
    public void setTargetLocation(MapLocation m) {
    	target = m;
    }
    public TaskKillScout() {
    	super();
    }
    @Override
    public boolean isComplete() {
    	if(target == null) return true;
    	if (rc.getLocation().distanceTo(target) <= threshold && rc.senseNearbyRobots(-1, enemy).length == 0) return true;
    	else return false;
    }
}
