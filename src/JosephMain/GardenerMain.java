package JosephMain;
import battlecode.common.*;

public strictfp class GardenerMain {
    static RobotController rc;
    static Task task;
    static MapLocation spawnloc = null;
    static Direction init_direction = null;
    static boolean tryToMoveOnStart = true;
    static float spawnthreshold = 1.0f;
    
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
    	//Set the MapLocation of the now used point to a location where the farming will not be blocked by map walls
    	MapLocation init_location = rc.getLocation().add(init_direction, spawnthreshold);
    	MapLocation additional_location = init_location;
	    boolean is_confirmed_available = false;
	    float devience_radius = .5f; //How much the additional_location is place outside of the init_location
		additional_location = init_location.add(getRandomDirection(), devience_radius);
		while(is_confirmed_available == false && devience_radius <= 2.0f) {
			for(int i = 0; i<20; i++){
				if(rc.canSenseAllOfCircle(additional_location, farm_size)){
	    			if(!rc.onTheMap(additional_location, farm_size)){
	        			additional_location = init_location.add(getRandomDirection(), devience_radius);
	    			} else{
	    				is_confirmed_available = true;
	    				break;
	    			}
				}
			}
			if(!is_confirmed_available){
				devience_radius += 0.5f;
			}
		}
        while (true) {
            try {
            	Direction dir_move = rc.getLocation().directionTo(additional_location);
            	float distance_to = rc.getLocation().distanceTo(additional_location);
            	if(rc.getLocation().distanceTo(spawnloc) < distance_to && tryToMoveOnStart){
            		if(distance_to >= 1.0f){
            			if(rc.canMove(dir_move)){
            				Movement.tryMove(dir_move);
            			}
            		}
            		if(rc.canMove(dir_move, distance_to)){
            			rc.move(dir_move, distance_to);
            		} else{
            			if(rc.canMove(dir_move)){
            				Movement.tryMove(dir_move);
            			}
            		}
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
