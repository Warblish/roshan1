package JosephMain;
import battlecode.common.*;

public strictfp class TaskAttackOnDemand extends Task {
    @SuppressWarnings("unused")
    private Team enemy = rc.getTeam().opponent();
    private float threshold = 1;
    private boolean archonInRange = false;
    private boolean complete = false;
    private int buffer = 0;
    private TaskTravelTo task;
    @Override
    public void runTurn() throws GameActionException {
    	if(rc.readBroadcast(7)==1) {
    		if(isComplete()) {
    			rc.broadcast(10, 1);
    		}
    		MapLocation target = new MapLocation(rc.readBroadcast(8),rc.readBroadcast(9));
    		task.setTarget(target);
    		if(!task.isComplete()) {
    			task.runTurn();
    		} else {
		    	RobotInfo[] robots = rc.senseNearbyRobots(RobotType.LUMBERJACK.bodyRadius+GameConstants.LUMBERJACK_STRIKE_RADIUS, enemy);
		
		        if(robots.length > 0 && !rc.hasAttacked()) {
		        	// Use strike() to hit all nearby robots!
		        	rc.strike();
		        }
		        // No close robots, so search for robots within sight radius
		        robots = rc.senseNearbyRobots(-1,enemy);
		
		        // If there is a robot, move towards it
		        if(robots.length > 0) {
		            MapLocation myLocation = rc.getLocation();
		            MapLocation enemyLocation = robots[0].getLocation();
		            Direction toEnemy = myLocation.directionTo(enemyLocation);
		
		            Movement.tryMoveSwerve(toEnemy);
		        } else {
		            // Move Randomly
		            Movement.tryMove(randomDirection());
		        }
    		}
    	}
    	super.runTurn();
	}
    
    public TaskAttackOnDemand() {

    	super();
    	task = new TaskTravelTo();
    }
    
    @Override
    public boolean isComplete() {
    	try {
			return rc.readBroadcast(7)==1;
		} catch (GameActionException e) {
			e.printStackTrace();
		}
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
