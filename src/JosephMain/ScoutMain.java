package JosephMain;
import battlecode.common.*;

public strictfp class ScoutMain {
    static RobotController rc;
    static Task task;
    public static void run() throws GameActionException {
    	rc = RobotPlayer.rc;
    	task = new TaskKillScout();
    	MapLocation[] archon_locs = rc.getInitialArchonLocations(rc.getTeam().opponent());
        while (true) {      	
            try {
            	if(archon_locs.length > 0){
                	((TaskKill) task).setTargetLocation(archon_locs[0]);	
            	}
            	task.runTurn();
                Clock.yield();
            } catch (Exception e) {
                System.out.println("Soldier Exception");
                e.printStackTrace();
            }
        }
	}
    public static void shoot(Direction d) throws GameActionException {
    	if(rc.canFireSingleShot()) {
    		rc.fireSingleShot(d);
    	}
    }
}
