package JosephMain;
import battlecode.common.*;

//current broadcasts
//0 -> Archon x location
//1 -> archon y location
//7 -> enemy to kill (0 for false, 1 for true, 2 for complex reading) we should rework this later to account for multiple targets (purpose of 2/complex thingy)
//8 -> target x  //we can rework this later to save broadcast space, merge all to single frequency
//9 -> target y
//10 -> will broadcast 1 when target eliminated

//STATIC CLASS TO HANDLE BROADCASTING
public strictfp class Broadcast {
    static RobotController rc;
    @SuppressWarnings("unused")
    //priority will be used later to override less important tasks
    public static boolean broadcastKillRequest(MapLocation m) throws GameActionException {
        RobotPlayer.rc.broadcast(7,1);
        RobotPlayer.rc.broadcast(8,(int)m.x);
        RobotPlayer.rc.broadcast(9,(int)m.y);
        return RobotPlayer.rc.readBroadcast(10) == 1;
    }
}
