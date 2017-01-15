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
    	float farm_size = GameConstants.BULLET_TREE_RADIUS*2+GameConstants.GENERAL_SPAWN_OFFSET+rc.getType().bodyRadius;
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
    	//Set the MapLocation of the initial point
    	MapLocation init_location = rc.getLocation().add(init_direction, spawnthreshold);
    	
    	while(!rc.onTheMap(init_location, farm_size) && !rc.isCircleOccupied(init_location, 1.0f)){
    		init_location = init_location.add(getRandomDirection(), farm_size);
    		for(int i = 0; i<20; i++){
    			if(!rc.onTheMap(init_location, farm_size) && !rc.isCircleOccupied(init_location, 1.0f)){
        			init_location.add(getRandomDirection(), farm_size);
    			} else{
    				break;
    			}
    		}
    		farm_size += 0.5f;
    	}
        while (true) {
            try {
            	Direction dir_move = rc.getLocation().directionTo(init_location);
            	float distance_to = rc.getLocation().distanceTo(init_location);
            	if(rc.getLocation().distanceTo(spawnloc) < distance_to && tryToMoveOnStart){
            		if(rc.getLocation().distanceTo(init_location) >= 1.0f){
            			Movement.tryMove(dir_move);
            		}
            		rc.move(dir_move, distance_to);
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
    
    private static Direction getRandomDirection(){
		return new Direction((float)Math.random() * 2 * (float)Math.PI);
    }
}
