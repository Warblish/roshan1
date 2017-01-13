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
                
                if(rc.getTeamBullets() > 3000){
                	rc.donate(100);
                }

                //First, hire a gardener to place tree infrastructure then place down a wandering gardener
                if(rc.canHireGardener(dir) && rc.getRoundNum() == 1){
                	rc.hireGardener(dir);
                } else if(rc.canHireGardener(dir) && rc.getRoundNum() == 11){
                	rc.hireGardener(dir);
                } else if (rc.canHireGardener(dir) && Math.random() < .01) {
                    rc.hireGardener(dir);
                }

                // Move randomly
                Movement.tryMove(RobotPlayer.randomDirection());

                // Broadcast archon's location for other robots on the team to know
                MapLocation myLocation = rc.getLocation();
                rc.broadcast(0,(int)myLocation.x);
                rc.broadcast(1,(int)myLocation.y);
                //Broadcast.broadcastKillRequest(DataMain.archon_locs[1]);
                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }
        }
	}
}
