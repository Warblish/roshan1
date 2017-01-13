package JosephMain;
import battlecode.common.*;

public strictfp class DataMain {
    static RobotController rc;
	public static MapLocation[] archon_locs;
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
}
