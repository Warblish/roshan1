package ScoutTest;
import JosephMain.Movement;
import battlecode.common.*;

public strictfp class RobotPlayer {
    static RobotController rc;

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
    **/
    public static void run(RobotController rc) throws GameActionException {

        // This is the RobotController object. You use it to perform actions from this robot,
        // and to get information on its current status.
        RobotPlayer.rc = rc;

        // Here, we've separated the controls into a different method for each RobotType.
        // You can add the missing ones or rewrite this into your own control structure.
        switch (rc.getType()) {
            case ARCHON:
                runArchon();
                break;
            case GARDENER:
                runGardener();
                break;
            case SOLDIER:
                runSoldier();
                break;
            case LUMBERJACK:
                runLumberjack();
                break;
			case SCOUT:
				runScout();
				break;
			case TANK:
				break;
			default:
				break;
        }
	}
    
    private static void runScout() {
		System.out.println("I'm a scout!");
		Team enemy = rc.getTeam().opponent();
		float threshold = 1.0f;
		MapLocation[] archon_locs = rc.getInitialArchonLocations(rc.getTeam().opponent());
		MapLocation target = archon_locs[0];
		boolean completed_target = false;
		
		while(true){
			try{
				RobotInfo[] nearbyRobots = rc.senseNearbyRobots(-1, enemy);
		    	MapLocation robotattacklocation = null;
		    	boolean hasAlreadyChased = false;
		    	// If there are some...
		        if (nearbyRobots.length > 0) {
		        	//Prioritize killing gardeners
		        	for(int i=0; i<nearbyRobots.length; i++){
		        		if(nearbyRobots[i].getType() == RobotType.GARDENER){
		        			tryMove(new Direction(rc.getLocation(), nearbyRobots[i].getLocation()));
		        			robotattacklocation = nearbyRobots[i].getLocation();
		        			hasAlreadyChased = true;
		        			break;
		        		}
		        	}
		        	if(!hasAlreadyChased){
		        		//Found a non-gardener
		        		//For now, attack an archon if available
		        		for(int b=0; b<nearbyRobots.length; b++){
		        			if(nearbyRobots[b].getType() == RobotType.ARCHON){
		        				tryMove(new Direction(rc.getLocation(), nearbyRobots[b].getLocation()));
			        			robotattacklocation = nearbyRobots[b].getLocation();
			        			break;
		        			}
		        		}
		        	} else{
		        		if(robotattacklocation != null){
		           		 // And we have enough bullets, and haven't attacked yet this turn...
		            		if(rc.getLocation().isWithinDistance(robotattacklocation, 3.0f) && rc.canFireSingleShot()){
		            			 // ...Then fire a bullet in the direction of the enemy.
		                        rc.fireSingleShot(rc.getLocation().directionTo(nearbyRobots[0].location));
		            		}
		        		}
		        	}
		        } else{
		        	//No robots detected around the scout
		        	if(rc.getLocation().distanceTo(target) > threshold){
		        		tryMove(new Direction(rc.getLocation(), target));
		    		} else{
		    			//Made it to the intended target location and start to wander
		    			Direction dir = RobotPlayer.randomDirection();
		    			rc.move(dir);
		    		}
		        }
			} catch(Exception e){
				System.out.println("Scout exception");
				e.printStackTrace();
			}
		}
		
	}

    static void runArchon() throws GameActionException {
        System.out.println("I'm an archon!");

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {

            	 // Generate a random direction
                Direction dir = RobotPlayer.randomDirection();

                //First, hire a gardener to place tree infrastructure then place down a wandering gardener
                RobotInfo[] info = rc.senseNearbyRobots(-1, rc.getTeam());
                
                if(rc.getRoundNum() == 1){
                	MapLocation[] archon_locs = rc.getInitialArchonLocations(rc.getTeam());
                	if(rc.getRobotCount() - archon_locs.length == 0){
                		//Spawn the first gardener
                		while(!rc.canHireGardener(dir)){
                			dir = RobotPlayer.randomDirection();
                		}
                		rc.hireGardener(dir);
                	}
                } else{
                	if(rc.canHireGardener(dir) && Math.random() < 0.01){
                		rc.hireGardener(dir);
                	}
                }

                // Move randomly
                tryMove(RobotPlayer.randomDirection());

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

	static void runGardener() throws GameActionException {
        System.out.println("I'm a gardener!");

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
                // Generate a random direction
                Direction dir = randomDirection();

                // Randomly attempt to build a soldier or lumberjack in this direction
                if (rc.canBuildRobot(RobotType.SCOUT, dir)) {
                    rc.buildRobot(RobotType.SCOUT, dir);
                } 

                // Move randomly
                tryMove(randomDirection());

                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Gardener Exception");
                e.printStackTrace();
            }
        }
    }

    static void runSoldier() throws GameActionException {
        System.out.println("I'm an soldier!");
        Team enemy = rc.getTeam().opponent();

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
                // See if there are any nearby enemy robots
                RobotInfo[] robots = rc.senseNearbyRobots(-1, enemy);

                // If there are some...
                if (robots.length > 0) {
                    // And we have enough bullets, and haven't attacked yet this turn...
                    if (rc.canFireSingleShot()) {
                        // ...Then fire a bullet in the direction of the enemy.
                        rc.fireSingleShot(rc.getLocation().directionTo(robots[0].location));
                    }
                }

                // Move randomly
                tryMove(randomDirection());

                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Soldier Exception");
                e.printStackTrace();
            }
        }
    }

    static void runLumberjack() throws GameActionException {
        System.out.println("I'm a lumberjack!");
        Team enemy = rc.getTeam().opponent();

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {

                // See if there are any enemy robots within striking range (distance 1 from lumberjack's radius)
                RobotInfo[] robots = rc.senseNearbyRobots(RobotType.LUMBERJACK.bodyRadius+GameConstants.LUMBERJACK_STRIKE_RADIUS, enemy);

                if(robots.length > 0 && !rc.hasAttacked()) {
                    // Use strike() to hit all nearby robots!
                    rc.strike();
                } else {
                    // No close robots, so search for robots within sight radius
                    robots = rc.senseNearbyRobots(-1,enemy);

                    // If there is a robot, move towards it
                    if(robots.length > 0) {
                        MapLocation myLocation = rc.getLocation();
                        MapLocation enemyLocation = robots[0].getLocation();
                        Direction toEnemy = myLocation.directionTo(enemyLocation);

                        tryMove(toEnemy);
                    } else {
                        // Move Randomly
                        tryMove(randomDirection());
                    }
                }

                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Lumberjack Exception");
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns a random Direction
     * @return a random Direction
     */
    static Direction randomDirection() {
        return new Direction((float)Math.random() * 2 * (float)Math.PI);
    }

    /**
     * Attempts to move in a given direction, while avoiding small obstacles directly in the path.
     *
     * @param dir The intended direction of movement
     * @return true if a move was performed
     * @throws GameActionException
     */
    static boolean tryMove(Direction dir) throws GameActionException {
        return tryMove(dir,20,3);
    }

    /**
     * Attempts to move in a given direction, while avoiding small obstacles direction in the path.
     *
     * @param dir The intended direction of movement
     * @param degreeOffset Spacing between checked directions (degrees)
     * @param checksPerSide Number of extra directions checked on each side, if intended direction was unavailable
     * @return true if a move was performed
     * @throws GameActionException
     */
    static boolean tryMove(Direction dir, float degreeOffset, int checksPerSide) throws GameActionException {

        // First, try intended direction
        if (rc.canMove(dir)) {
            rc.move(dir);
            return true;
        }

        // Now try a bunch of similar angles
        int currentCheck = 1;

        while(currentCheck<=checksPerSide) {
            // Try the offset of the left side
            if(rc.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck));
                return true;
            }
            // Try the offset on the right side
            if(rc.canMove(dir.rotateRightDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateRightDegrees(degreeOffset*currentCheck));
                return true;
            }
            // No move performed, try slightly further
            currentCheck++;
        }

        // A move never happened, so return false.
        return false;
    }

    /**
     * A slightly more complicated example function, this returns true if the given bullet is on a collision
     * course with the current robot. Doesn't take into account objects between the bullet and this robot.
     *
     * @param bullet The bullet in question
     * @return True if the line of the bullet's path intersects with this robot's current position.
     */
    static boolean willCollideWithMe(BulletInfo bullet) {
        MapLocation myLocation = rc.getLocation();

        // Get relevant bullet information
        Direction propagationDirection = bullet.dir;
        MapLocation bulletLocation = bullet.location;

        // Calculate bullet relations to this robot
        Direction directionToRobot = bulletLocation.directionTo(myLocation);
        float distToRobot = bulletLocation.distanceTo(myLocation);
        float theta = propagationDirection.radiansBetween(directionToRobot);

        // If theta > 90 degrees, then the bullet is traveling away from us and we can break early
        if (Math.abs(theta) > Math.PI/2) {
            return false;
        }

        // distToRobot is our hypotenuse, theta is our angle, and we want to know this length of the opposite leg.
        // This is the distance of a line that goes from myLocation and intersects perpendicularly with propagationDirection.
        // This corresponds to the smallest radius circle centered at our location that would intersect with the
        // line that is the path of the bullet.
        float perpendicularDist = (float)Math.abs(distToRobot * Math.sin(theta)); // soh cah toa :)

        return (perpendicularDist <= rc.getType().bodyRadius);
    }
}
