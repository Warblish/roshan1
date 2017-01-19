package JosephMain;
import java.util.Random;

import battlecode.common.*;

public strictfp class TaskTrackKillLumberjack extends Task {
    private Team enemy = rc.getTeam().opponent();
    private boolean archonInRange = false;
    private boolean complete = false;
    private MapLocation lastenemy;
    private boolean hasSeenEnemy = false;
    private int buffer = 0;
    private int squad_number;
    public boolean teamSoloAttack = true;
    @Override
    public void runTurn() throws GameActionException {
    	RobotInfo[] robots = rc.senseNearbyRobots(RobotType.LUMBERJACK.bodyRadius+GameConstants.LUMBERJACK_STRIKE_RADIUS, enemy);

        if(robots.length > 0 && !rc.hasAttacked()) {
        	// Use strike() to hit all nearby robots!
        	rc.strike();
        }
        
        // No close robots, so search for robots within sight radius
        robots = rc.senseNearbyRobots(-1,enemy);

        // If there is a robot, move towards it
        if(robots.length > 0) {
            MapLocation enemyLocation = robots[0].getLocation();
            if(squad_number != 0){
            	Broadcast.broadcastKillRequest(enemyLocation, squad_number);
            } else{
            	Broadcast.broadcastKillRequest(enemyLocation, 1);
            }
        } 
        //Check if the most recent broadcast was sent within the last 2 turns
        int round_to_check = 0;
        switch(squad_number){
        	case 1:
        		round_to_check = 22;
        		break;
        	case 2:
        		round_to_check = 32;
        		break;
        	case 3:
        		round_to_check = 42;
        		break;
        	default:
        		System.out.println("Error, illegal squad number");
        		break;
        }
        if(rc.getRoundNum()-rc.readBroadcast(round_to_check) <= 2) {
        	boolean readBroadcast = false;
        	int x_target = 0;
            int y_target = 0;
            switch(squad_number){
    		case(1): 
    			x_target = rc.readBroadcast(20);
    			y_target = rc.readBroadcast(21);
    			readBroadcast = true;
    			break;
    		case(2): 
    			x_target = rc.readBroadcast(30);
				y_target = rc.readBroadcast(31);
    			readBroadcast = true;
    			break;
    		case(3):
    			x_target = rc.readBroadcast(40);
				y_target = rc.readBroadcast(41);
    			readBroadcast = true;
    			break;
    		default:
    			System.out.println("Error: illegal squad number");
    			break;
            }
	        Movement.tryMoveSwerve(new Direction(rc.getLocation(), new MapLocation(x_target,y_target)));
	        hasSeenEnemy = true;
	        lastenemy = new MapLocation((x_target),(y_target));
        } else {
        	if(!teamSoloAttack) {
	        	if(rc.getRoundNum()-rc.readBroadcast(22) <= 2){
	        		Movement.tryMoveSwerve(new Direction(rc.getLocation(), new MapLocation(rc.readBroadcast(20),rc.readBroadcast(21))));
	        		hasSeenEnemy = true;
	        		lastenemy = new MapLocation(rc.readBroadcast(20),rc.readBroadcast(21));
	        	} else if(rc.getRoundNum()-rc.readBroadcast(32) <= 2){
	        		Movement.tryMoveSwerve(new Direction(rc.getLocation(), new MapLocation(rc.readBroadcast(30),rc.readBroadcast(31))));
	        		hasSeenEnemy = true;
	        		lastenemy = new MapLocation(rc.readBroadcast(30),rc.readBroadcast(31));
	        	} else if(rc.getRoundNum()-rc.readBroadcast(42) <= 2){
	        		Movement.tryMoveSwerve(new Direction(rc.getLocation(), new MapLocation(rc.readBroadcast(40),rc.readBroadcast(41))));
	        		hasSeenEnemy = true;
	        		lastenemy = new MapLocation(rc.readBroadcast(40),rc.readBroadcast(41));
	        	} else{
        		
        		
            // Move Randomly
	        	//if(hasSeenEnemy) {
	        	//	Movement.wander(lastenemy);
	        	//} else {
	        		Movement.tryMove(randomDirection());
	        	//}
	        		
	        		
	        	}
        	} else {
        		
                // Move Randomly
    	        	//if(hasSeenEnemy) {
    	        	//	Movement.wander(lastenemy);
    	        	//} else {
    	        		Movement.tryMove(randomDirection());
    	        	//}
        	}       	
        }
    	super.runTurn();
	}
    
    public TaskTrackKillLumberjack() {
    	super();
    }
    public void setSquadNumber(int n){
    	this.squad_number = n;
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
