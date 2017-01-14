package JosephMain;
import battlecode.common.*;

public strictfp class GardenerMain {
    static RobotController rc;
    static Task task;
    static MapLocation spawnloc = null;
    static Direction init_direction = null;
    static boolean tryToMoveOnStart = true;
    static float spawnthreshold = 3.0f;
    
    public static void run() throws GameActionException {
    	rc = RobotPlayer.rc;
    	task = new TaskGarden2();
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
    	
    	//Create limits on what the initial direction must fulfill before allowing it to be executed fully
    	MapLocation plantation_request = rc.getLocation().add(init_direction, spawnthreshold);
    	for(int i=0; i<7; i++){
    		if(!rc.onTheMap(plantation_request, GameConstants.BULLET_TREE_RADIUS*2+GameConstants.GENERAL_SPAWN_OFFSET+rc.getType().bodyRadius)){
    			init_direction.rotateLeftDegrees(45);
    			plantation_request = rc.getLocation().add(init_direction, spawnthreshold);
    			System.out.println(init_direction.radians);
    		}
    	}
        while (true) {
            try {
            	if(rc.canMove(init_direction) && rc.getLocation().distanceTo(spawnloc) < spawnthreshold && tryToMoveOnStart){
            		Movement.tryMove(init_direction);
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
