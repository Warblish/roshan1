package JosephMain;
import battlecode.common.*;

public strictfp class TaskTrackKillArchon extends Task {
    @SuppressWarnings("unused")
    private Team enemy = rc.getTeam().opponent();
    private float threshold = 1;
    @Override
    public void runTurn() throws GameActionException {
    	RobotInfo[] robots = rc.senseNearbyRobots(RobotType.LUMBERJACK.bodyRadius+GameConstants.LUMBERJACK_STRIKE_RADIUS, enemy);

        if(robots.length > 0 && !rc.hasAttacked()) {
        	// Use strike() to hit all nearby robots!
        	rc.strike();
        }
        
        // No close robots, so search for robots within sight radius
        robots = rc.senseNearbyRobots(-1,enemy);
        //Set the active tracking to an Archon, or just a random robot if there is no archon
        RobotInfo activetrack = null;
    	for(RobotInfo info : robots){
    		if(info.getType() == RobotType.ARCHON){
    			activetrack = info;
    			break;
    		}
    	}
    	if(activetrack == null && robots.length > 0){
    		activetrack = robots[0];
    	}

        // If there is a robot, move towards it
        if(robots.length > 0) {
            MapLocation myLocation = rc.getLocation();
            MapLocation enemyLocation = activetrack.getLocation();
            Direction toEnemy = myLocation.directionTo(enemyLocation);

            Movement.tryMove(toEnemy);
        } else {
            // Move Randomly
            Movement.tryMove(randomDirection());
        }
    	super.runTurn();
	}
    
    public TaskTrackKillArchon() {
    	super();
    }
    
    @Override
    public boolean isComplete() {
    	//Function is never complete, keeps tracking until there are no archons, then attack units randomly
    	return false;
    }
    
    /**
     * Returns a random Direction
     * @return a random Direction
     */
    static Direction randomDirection() {
        return new Direction((float)Math.random() * 2 * (float)Math.PI);
    }
}
