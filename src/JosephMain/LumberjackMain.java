package JosephMain;
import battlecode.common.*;

public strictfp class LumberjackMain {
    static RobotController rc;
    static Task task1;
    static Task task2;
    public static void run() throws GameActionException {
    	rc = RobotPlayer.rc;
    	int x = 0;
		task1 = new TaskKillOnDemand();
    	while(true) {
    		if(x != DataMain.archon_locs.length) {
	    		eliminateArchon(x);
	    		x++;
    		} else {
    			Clock.yield();
    		}
    		//eliminateArchon(0);
    		//task1.runTurn();
    		//Clock.yield();
    	}
	} //fuck u commit
    public static void eliminateArchon(int id) throws GameActionException {
    	if(rc.readBroadcast(7) != 1) {
	    	task1 = new TaskTravelTo();
		   	((TaskTravelTo) task1).setThreshold(2);
		
	    	task1.setTrigger(new String[] {"ENEMYROBOTS"});
	    	while(!task1.isTriggered()){
	    		try {
	            	if(DataMain.archon_locs.length > id){
	                	((TaskTravelTo) task1).setTarget(DataMain.archon_locs[id]);	
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
    	}
    	if(rc.readBroadcast(74) == 1) {
        	task2 = new TaskTrackKillArchon();
        	while(!task2.isComplete()){
        		try{
            		task2.runTurn();
            		Clock.yield();
        		} catch (Exception e){
        			System.out.println("Lumberjack exception");
                    e.printStackTrace();
        		}
        	}
        	rc.broadcast(74, 1);
    	}
    	task2 = new TaskTrackKill();
    	while(!task2.isComplete()){
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
