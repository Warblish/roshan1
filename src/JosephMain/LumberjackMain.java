package JosephMain;
import battlecode.common.*;

public strictfp class LumberjackMain {
    static RobotController rc;
    static Task task1;
    static Task task2;
    @SuppressWarnings("unused")
    public static void run() throws GameActionException {
    	rc = RobotPlayer.rc;
    	task1 = new TaskTravelTo();
    	MapLocation[] archon_locs = rc.getInitialArchonLocations(rc.getTeam().opponent());
    	while(true){
    		try {
            	if(archon_locs.length > 0){
                	((TaskKill) task1).setTargetLocation(archon_locs[0]);	
            	}
            	task1.runTurn();
            	if(task1.isComplete()){
            		break;
            	}
                Clock.yield();
            } catch (Exception e) {
                System.out.println("Lumberjack Exception");
                e.printStackTrace();
            }
    	}
    	
    	task2 = new TaskTrackKillArchon();
    	while(true){
    		try{
        		task2.runTurn();
        		Clock.yield();
    		} catch (Exception e){
    			System.out.println("Lumberjack exception");
                e.printStackTrace();
    		}
    	}
    	
	}
}
