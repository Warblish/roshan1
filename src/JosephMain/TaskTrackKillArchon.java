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

            tryMove(toEnemy);
        } else {
            // Move Randomly
            tryMove(randomDirection());
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

    /**
     * Attempts to move in a given direction, while avoiding small obstacles directly in the path.
     *
     * @param dir The intended direction of movement
     * @return true if a move was performed
     * @throws GameActionException
     */
    static boolean tryMove(Direction dir) throws GameActionException {
        return tryMove(dir,20,3);
    }

    /**
     * Attempts to move in a given direction, while avoiding small obstacles direction in the path.
     *
     * @param dir The intended direction of movement
     * @param degreeOffset Spacing between checked directions (degrees)
     * @param checksPerSide Number of extra directions checked on each side, if intended direction was unavailable
     * @return true if a move was performed
     * @throws GameActionException
     */
    static boolean tryMove(Direction dir, float degreeOffset, int checksPerSide) throws GameActionException {

        // First, try intended direction
        if (rc.canMove(dir)) {
            rc.move(dir);
            return true;
        }

        // Now try a bunch of similar angles
        boolean moved = false;
        int currentCheck = 1;

        while(currentCheck<=checksPerSide) {
            // Try the offset of the left side
            if(rc.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck));
                return true;
            }
            // Try the offset on the right side
            if(rc.canMove(dir.rotateRightDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateRightDegrees(degreeOffset*currentCheck));
                return true;
            }
            // No move performed, try slightly further
            currentCheck++;
        }

        // A move never happened, so return false.
        return false;
    }
}
