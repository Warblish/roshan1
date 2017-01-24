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
    private boolean onGuard = true;
    private MapLocation guardPost;
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
            	Broadcast.broadcastKillRequest(enemyLocation, squad_number, RobotPlayer.rc.readBroadcast(13 + 10*squad_number)==1);
            } else{
            	Broadcast.broadcastKillRequest(enemyLocation, 1, RobotPlayer.rc.readBroadcast(13 + 10*squad_number)==1);
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
    			onGuard = rc.readBroadcast(23)==0;
    			readBroadcast = true;
    			break;
    		case(2): 
    			x_target = rc.readBroadcast(30);
				y_target = rc.readBroadcast(31);
				onGuard = rc.readBroadcast(33)==0;
    			readBroadcast = true;
    			break;
    		case(3):
    			x_target = rc.readBroadcast(40);
				y_target = rc.readBroadcast(41);
				onGuard = rc.readBroadcast(43)==0;
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
        		
        		
            // return pot
		        	if(onGuard) {
		        		if(rc.getLocation().distanceTo(guardPost) > .5) {
		        			Movement.tryMove(rc.getLocation().directionTo(guardPost));
		        		}
		        	} else {
		        		Movement.tryMove(randomDirection());
		        	}
	        		
	        		
	        	}
        	} else {
        		
                // return to post
	        	if(onGuard) {
	        		if(rc.getLocation().distanceTo(guardPost) > .5) {
	        			Movement.tryMove(rc.getLocation().directionTo(guardPost));
	        		}
	        	} else {
	        		Movement.tryMove(randomDirection());
	        	}
        		
        	}       	
        }
    	super.runTurn();
	}
    
    public TaskTrackKillLumberjack() {
    	super();
    	guardPost = rc.getLocation();
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
