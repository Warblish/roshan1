package JosephMain;
import battlecode.common.*;






//STATIC CLASS TO HANDLE BROADCASTING
public strictfp class Broadcast {
    static RobotController rc;
    @SuppressWarnings("unused")
    public static void run() throws GameActionException {
    	rc = RobotPlayer.rc;
	}
}
