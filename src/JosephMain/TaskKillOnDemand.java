package JosephMain;
import battlecode.common.*;

public strictfp class TaskKillOnDemand extends Task {
    private MapLocation target;
    private Team enemy = rc.getTeam().opponent();
    private float threshold = 1;
    private int squad_number;
    @Override
    public void runTurn() throws GameActionException {
		if(isComplete()) {
			//rc.broadcast(10, 1);
		}
		switch(squad_number){
			case(1): 
				setTargetLocation(new MapLocation(rc.readBroadcast(20),rc.readBroadcast(21)));
				break;
			case(2): 
				setTargetLocation(new MapLocation(rc.readBroadcast(30),rc.readBroadcast(31)));
				break;
			case(3): 
				setTargetLocation(new MapLocation(rc.readBroadcast(40),rc.readBroadcast(41)));
				break;
			default:
				System.out.println("Error: illegal squad number");
				break;
		}
		//code copied from kill
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
    public TaskKillOnDemand() {
    	super();
    }
    public void setSquadNumber(int n){
    	this.squad_number = n;
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
