package JosephMain;
import battlecode.common.*;

public strictfp class TaskTrackKill extends Task {
    private Team enemy = rc.getTeam().opponent();
    private boolean archonInRange = false;
    private boolean complete = false;
    private int buffer = 0;
    @Override
    public void runTurn() throws GameActionException {
    	System.out.println("Running tack kill");
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
            Broadcast.broadcastKillRequest(enemyLocation);
        } 
        if(rc.readBroadcast(7) == 1) {
        	System.out.println("killing " + new MapLocation(rc.readBroadcast(8),rc.readBroadcast(9)));
        	Movement.tryMove(new Direction(rc.getLocation(), new MapLocation(rc.readBroadcast(8),rc.readBroadcast(9))));
        } else {
            // Move Randomly
            Movement.tryMove(randomDirection());
        }
    	super.runTurn();
	}
    
    public TaskTrackKill() {
    	super();
    }
    
    @Override
    public boolean isComplete() {
    	//Function complete when there was an archon, but now theres not!!!!
    	return complete;
    }
    static Direction randomDirection() {
        return new Direction((float)Math.random() * 2 * (float)Math.PI);
    }
}
