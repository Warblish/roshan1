package JosephMain;
import battlecode.common.*;

public strictfp class ArchonMain {
    static RobotController rc;
    @SuppressWarnings("unused")
    public static void run() throws GameActionException {
    	rc = RobotPlayer.rc;
        System.out.println("I'm an archon!");

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {

                // Generate a random direction
                Direction dir = RobotPlayer.randomDirection();

                // Randomly attempt to build a gardener in this direction
                if(rc.canHireGardener(dir) && rc.getRoundNum() < 10 && rc.getTeamBullets() > 201.0f){
                	rc.hireGardener(dir);
                }
                
                if (rc.canHireGardener(dir) && Math.random() < .01) {
                    rc.hireGardener(dir);
                }

                // Move randomly
                Movement.tryMove(RobotPlayer.randomDirection());

                // Broadcast archon's location for other robots on the team to know
                MapLocation myLocation = rc.getLocation();
                rc.broadcast(0,(int)myLocation.x);
                rc.broadcast(1,(int)myLocation.y);
                Broadcast.broadcastKillRequest(DataMain.archon_locs[1]);
                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }
        }
	}
}
