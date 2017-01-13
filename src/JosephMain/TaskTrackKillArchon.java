package JosephMain;
import battlecode.common.*;

public strictfp class TaskTrackKillArchon extends Task {
    private Team enemy = rc.getTeam().opponent();
    private boolean archonInRange = false;
    private boolean complete = false;
    private int buffer = 0;
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
        boolean archonFlag = false;
    	for(RobotInfo info : robots){
    		if(info.getType() == RobotType.ARCHON){
    			archonInRange = true;
    			activetrack = info;
    			archonFlag = true;
    			break;
    		}
    	}
    	if(!archonFlag && archonInRange == true) { //no archons found, but there was one last loop
    		if(buffer >= 3) {
    			archonInRange = false;
    			complete = true; //we did it!!!!!!!
    		} else {
    			buffer++; //loop for 3 turns for good measure
    		}
    		
    	} else {
    		buffer = 0; //reset buffer, archon is back
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
    	//Function complete when there was an archon, but now theres not!!!!
    	return complete;
    }
    
    /**
     * Returns a random Direction
     * @return a random Direction
     */
    static Direction randomDirection() {
        return new Direction((float)Math.random() * 2 * (float)Math.PI);
    }
}
