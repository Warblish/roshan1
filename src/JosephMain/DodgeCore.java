package JosephMain;
import battlecode.common.*;



//STATIC CLASS TO HANDLE DODGE CODE
public strictfp class DodgeCore {
    static RobotController rc;
    @SuppressWarnings("unused")
    public static void tryDodgeTravel(MapLocation target) throws GameActionException { //DODGE BULLETS WHILE TRYING TO GET SOMEWHERE
    	rc = RobotPlayer.rc;
    	BulletInfo[] bullets = rc.senseNearbyBullets();
    	for(int i = 0; i < bullets.length; i++) {
    		
    	}
    	
	}
    public static void tryDodgeAll() throws GameActionException { //JUST BE SAFE 
    	rc = RobotPlayer.rc;
    	BulletInfo[] bullets = rc.senseNearbyBullets();
    	for(int i = 0; i < bullets.length; i++) {
    		
    	}
    	
	}
    public MapLocation[] getFutureLocations(BulletInfo[] bullets, int turns) {
    	return null;
    }
    public boolean isSafeInFuture(MapLocation spot, int turns) {
    	return false;
    }
    public Maneuver createDodgePlan() {
    	return null;
    }
}
