package JosephMain;
import java.util.Random;

import battlecode.common.*;

public strictfp class LumberjackMain {
    static RobotController rc;
    static Task task1;
    static Task task2;
    static Random rand = new Random();
	//static int squad_number = rand.nextInt(3) + 1;
    static int squad_number = rand.nextInt(2) + 1; //for soldier testing, LJs will be T1 and T2, soldiers T3
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
	                System.out.println("Lumberjack Exception");
	                e.printStackTrace();
	            }
	    	}*/
    	//}
    	task2 = new TaskTrackKillLumberjack();
    	((TaskTrackKillLumberjack)task2).setSquadNumber(squad_number);
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
