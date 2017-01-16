package JosephMain;
import battlecode.common.*;

//current broadcasts
//0 -> Archon x location
//1 -> archon y location
//7 -> enemy to kill (0 for false, 1 for true, 2 for complex reading) we should rework this later to account for multiple targets (purpose of 2/complex thingy)
//8 -> target x  //we can rework this later to save broadcast space, merge all to single frequency
//9 -> target y
//10 -> will broadcast 1 when target eliminated
//11 -> archon will broadcast the hiregardener() order request for type (1 is farmer, 2 is spawner, 3 is guard)
//20-22 -> channel for broadcasting kill request for team 1: 20=x, 21=y, 22=round num reference of created command
//30-32 -> channel for broadcasting kill request for team 1
//40-42 -> channel for broadcasting kill request for team 1

//STATIC CLASS TO HANDLE BROADCASTING
public strictfp class Broadcast {
    static RobotController rc;
    //priority will be used later to override less important tasks
    public static void broadcastKillRequest(MapLocation m, int team) throws GameActionException {
    	//Only allow 3 teams to be created
    	switch(team){
    		case 1: 
    			RobotPlayer.rc.broadcast(20,(int)m.x);
    	        RobotPlayer.rc.broadcast(21,(int)m.y);
    	        RobotPlayer.rc.broadcast(22, RobotPlayer.rc.getRoundNum());
    	        break;
    		case 2:
    			RobotPlayer.rc.broadcast(30,(int)m.x);
    	        RobotPlayer.rc.broadcast(31,(int)m.y);
    	        RobotPlayer.rc.broadcast(32, RobotPlayer.rc.getRoundNum());
    	        break;
    		case 3:
    			RobotPlayer.rc.broadcast(40,(int)m.x);
    	        RobotPlayer.rc.broadcast(41,(int)m.y);
    	        RobotPlayer.rc.broadcast(42, RobotPlayer.rc.getRoundNum());
    	        break;
    		default:
    			System.out.println("Error, wrong team selected in Broadcast: " + team);
    			break;
    	}
    }
}
