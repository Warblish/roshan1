package JosephMain;
import battlecode.common.*;

public strictfp class SoldierMain {
    static RobotController rc;
    static Task task;
    public static void run() throws GameActionException {
    	rc = RobotPlayer.rc;
    	task = new TaskKill();
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
    public static void shootSingle(Direction d) throws GameActionException {
    	if(rc.canFireSingleShot()) {
    		rc.fireSingleShot(d);
    	}
    }
    public static void shootTriad(Direction d) throws GameActionException {
    	if(rc.canFireTriadShot()) {
    		rc.fireTriadShot(d);
    	}
    }
    public static void shootPentad(Direction d) throws GameActionException {
    	if(rc.canFirePentadShot()) {
    		rc.firePentadShot(d);
    	}
    }
}
