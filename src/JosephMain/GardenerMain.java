package JosephMain;
import battlecode.common.*;

public strictfp class GardenerMain {
    static RobotController rc;
    static Task task;
    static MapLocation spawnloc = null;
    static Direction init_direction = null;
    static boolean tryToMoveOnStart = true;
    @SuppressWarnings("unused")
    public static void run() throws GameActionException {
    	rc = RobotPlayer.rc;
    	task = new TaskGarden();
    	spawnloc = rc.getLocation();
    	//Get the nearby Archon
    	RobotInfo info[] = rc.senseNearbyRobots(1.5f, rc.getTeam());
    	for(RobotInfo RI : info){
    		if(RI.getType() == RobotType.ARCHON){
    			init_direction = new Direction(RI.getLocation(), rc.getLocation());
    			System.out.println("Gardener located Archon");
    		}
    	}
    	if(init_direction == null){
    		init_direction = new Direction((float)Math.random() * 2 * (float)Math.PI);
    	}
        while (true) {
            try {
            	if(rc.canMove(init_direction) && rc.getLocation().distanceTo(spawnloc) < 2.0f && tryToMoveOnStart){
            		rc.move(init_direction);
            	} else{
                	task.runTurn();
                	tryToMoveOnStart = false;
            	}
                Clock.yield();
            } catch (Exception e) {
                System.out.println("Soldier Exception");
                e.printStackTrace();
            }
        }
	}
}
