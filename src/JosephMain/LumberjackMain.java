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
    	((TaskTravelTo) task1).setThreshold(2);
    	MapLocation[] archon_locs = rc.getInitialArchonLocations(rc.getTeam().opponent());
    	task1.setTrigger(new String[] {"ENEMYROBOTS"});
    	while(!task1.isTriggered()){
    		try {
            	if(archon_locs.length > 0){
                	((TaskTravelTo) task1).setTarget(archon_locs[0]);	
            	}
            	task1.runTurn();
            	if(task1.isComplete()){
            		Clock.yield();
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
