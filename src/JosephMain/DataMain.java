package JosephMain;
import battlecode.common.*;

public strictfp class DataMain {
    static RobotController rc;
	public static MapLocation[] archon_locs;
    private final static int MAX_BULLET_COUNT = 1000;
    public static void run() throws GameActionException {
    	rc = RobotPlayer.rc;
        while (true) {
            try {

                Clock.yield();
            } catch (Exception e) {
                System.out.println("Soldier Exception");
                e.printStackTrace();
            }
        }
	}
    
    public static void victorypointwin(){
    	
    	try{
	    	//Get target amount of bullets if you can win
	    	//Runs for every robot running a task to maximum win speed
	    	float bulletamt = RobotPlayer.rc.getTeamBullets();
	    	//2010 because 2000 is the threshold and 2010 is the lowest amount possible to donate
	    	if(bulletamt > MAX_BULLET_COUNT + 10){
	    		//Donate the nearest ten bullets (as long as it is lower than the current bullet amount)
	    		//And as long as it keeps the overall bullet amount over 2000
	    		float difference = bulletamt - MAX_BULLET_COUNT;
	    		RobotPlayer.rc.donate((float) (Math.floor(difference/10)*10));
	    	} else if(RobotPlayer.rc.getTeamBullets() >= (10000 -(RobotPlayer.rc.getTeamVictoryPoints()*10))){
	    		if((RobotPlayer.rc.getTeamBullets()/10) < 10) {
	    			RobotPlayer.rc.donate(10);
	    		} else {
	    			RobotPlayer.rc.donate((RobotPlayer.rc.getTeamBullets()/10));
	    		}
	        }
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
