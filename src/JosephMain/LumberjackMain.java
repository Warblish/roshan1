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
    	int starttime = RobotPlayer.rc.getRoundNum();
        RobotInfo[] robots = rc.senseNearbyRobots(-1);

        // If there is a robot, move towards it
        boolean gardenFlag = false;
        boolean enemySpotted = false;
        Direction guardDir = RobotPlayer.randomDirection();
        for(RobotInfo r : robots) {
        	if(r.getTeam() == rc.getTeam().opponent()) {
        		enemySpotted = true;
        		break;
        	} else if(r.getTeam() == rc.getTeam() && r.getType() == RobotType.GARDENER && !gardenFlag) {
        		gardenFlag = true;
        		guardDir = rc.getLocation().directionTo(r.getLocation()).opposite();
        	}
        }
    	while(rc.getRoundNum() - starttime <= 5 && gardenFlag){
    		Movement.tryMoveSwerve(guardDir);
    		Clock.yield();
    	}
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
