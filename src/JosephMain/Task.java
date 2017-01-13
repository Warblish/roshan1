package JosephMain;
import battlecode.common.*;

public strictfp class Task {
    static RobotController rc;
    protected boolean complete = false;
    public static RobotInfo[] TriggeredRobots;
    public static TreeInfo[] TriggeredTrees;
    public static BulletInfo[] TriggeredBullets;
    private boolean triggered = false;
    private boolean hasTrigger = false;
    private String[] triggers;
    public void runTurn() throws GameActionException {
    	if(hasTrigger) {
    		triggered = senseTrigger(triggers);
    	}
    	//Get target amount of bullets if you can win
    	//Runs for every robot running a task to maximum win speed
    	if(rc.getTeamBullets() >= (1000-rc.getTeamVictoryPoints())*10){
        	rc.donate(100);
        }
	}
    public Task() {
    	rc = RobotPlayer.rc;
    }
    public boolean isComplete() {
    	return complete;
    }
    public void setTrigger(String[] t) {
    	triggers = t;
    	hasTrigger = true;
    }
    public String[] getTriggers() {
    	return triggers;
    }
    public void resetTrigger() {
    	//triggers = new Trigger[0];
    	hasTrigger = false;
    }
    public boolean isTriggered() {
    	return triggered;
    }
    public boolean hasTrigger() {
    	return hasTrigger;
    }
    
    public static boolean senseTrigger(String[] t) {  //WARNING, 2 TRIGGERS OF SAME TYPE WILL BREAK TRIGGER ARRAY
    	boolean flag = false;
    	float rad = rc.getType().sensorRadius;
    	Team enemy = rc.getTeam().opponent();
    	RobotInfo[] robots;
    	TreeInfo[] trees;
    	BulletInfo[] bullets;
    	try{
	    	for(int i=0; i<t.length; i++) {
	    		switch(t[i]) {
		    		case "ALLTREES":
		    			trees = rc.senseNearbyTrees(rad);
		    			if(trees.length>0) {
		    				TriggeredTrees = trees;
		    				flag = true;
		    			}
		    			break;
		    		case "NEUTRALTREES":
		    			trees = rc.senseNearbyTrees(rad, Team.NEUTRAL);
		    			if(trees.length>0) {
		    				TriggeredTrees = trees;
		    				flag = true;
		    			}    
		    			break;
		    		case "FRIENDLYTREES":
		    			trees = rc.senseNearbyTrees(rad, rc.getTeam());
		    			if(trees.length>0) {
		    				TriggeredTrees = trees;
		    				flag = true;
		    			}
		    			break;
		    		case "ENEMYTREES":
		    			trees = rc.senseNearbyTrees(rad, enemy);
		    			if(trees.length>0) {
		    				TriggeredTrees = trees;
		    				flag = true;
		    			}
		    			break;
		    		case "ALLROBOTS":
		    			robots = rc.senseNearbyRobots(rad);
		    			if(robots.length>0) {
		    				TriggeredRobots = robots;
		    				flag = true;
		    			}
		    			break;
		    		case "FRIENDLYROBOTS":
		    			robots = rc.senseNearbyRobots(rad, rc.getTeam());
		    			if(robots.length>0) {
		    				TriggeredRobots = robots;
		    				flag = true;
		    			}
		    			break;
		    		case "ENEMYROBOTS":
		    			robots = rc.senseNearbyRobots(rad, enemy);
		    			if(robots.length>0) {
		    				TriggeredRobots = robots;
		    				flag = true;
		    			}
		    			break;
		    		case "BULLET":
		    			bullets = rc.senseNearbyBullets();
		    			if(bullets.length>0) {
		    				TriggeredBullets = bullets;
		    				flag = true;
		    			}
		    			break;
					default:
						break;
	    		}
	    		
	    		
	    	}
    	} catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    	
    	return flag;
    }
    
    
}
