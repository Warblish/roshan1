package JosephMain;
import battlecode.common.*;

public strictfp class SpawnerMain {
    static RobotController rc;

    public static void run() throws GameActionException {
        System.out.println("I'm a Spawner!");
    	rc = RobotPlayer.rc;
        while (true) {
        	try {
                // Generate a random direction
                Direction dir = RobotPlayer.randomDirection();

                // Randomly attempt to build a soldier or lumberjack in this direction
                if (rc.getTeamBullets() >= 250.0 && rc.canBuildRobot(RobotType.LUMBERJACK, dir)) {
                    rc.buildRobot(RobotType.LUMBERJACK, dir);
                }

                // Move randomly
                Movement.tryMove(RobotPlayer.randomDirection());

                DataMain.victorypointwin();
                
                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Gardener Exception");
                e.printStackTrace();
            }
        }
	}
}
