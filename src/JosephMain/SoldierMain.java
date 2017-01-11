package JosephMain;
import battlecode.common.*;

public strictfp class SoldierMain {
    static RobotController rc;
    static Task task;
    @SuppressWarnings("unused")
    public static void run() throws GameActionException {
    	rc = RobotPlayer.rc;
    	task = new TaskTravelTo();
        while (true) {      	
            try {
            	((TaskTravelTo) task).setTarget(new MapLocation(rc.readBroadcast(0), rc.readBroadcast(1)));
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
