package JosephMain;
import java.util.Random;

import battlecode.common.*;

public strictfp class SoldierMain {
    static RobotController rc;
    static Task task1;
    static Task task2;
    static Random rand = new Random();
	//static int squad_number = rand.nextInt(2) + 2; //either 2 or 3
    static int squad_number = 3; //for testing, team 3 is soldier only
	static boolean killflag = true;
    public static void run() throws GameActionException {
    	rc = RobotPlayer.rc;
	    main();
	}
    public static void main() throws GameActionException {
    	//if(rc.readBroadcast(7) != 1) {
	    	/*task1 = new TaskTravelTo();
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
	                System.out.println("Soldier Exception");
	                e.printStackTrace();
	            }
	    	}*/
    	//}
    	task2 = new TaskTrackKillSoldier();
    	((TaskTrackKillSoldier)task2).setSquadNumber(squad_number);
    	while(!task2.isComplete()){
    		try{
    			if(rc.getHealth() <= 5 && killflag) {
    				int deathfrequency = 90 + squad_number;
    				killflag = false;
    				rc.broadcast(deathfrequency, rc.readBroadcast(deathfrequency)+1);
    			}
        		task2.runTurn();
        		Clock.yield();
    		} catch (Exception e){
    			System.out.println("Lumberjack exception");
                e.printStackTrace();
    		}
    	}
    }
}
