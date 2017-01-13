package Macro_base;
import battlecode.common.*;

public strictfp class RobotPlayer {
    static RobotController rc;

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
    **/
    @SuppressWarnings("unused")
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
				break;
			case TANK:
				break;
			default:
				break;
        }
	}

    static void runArchon() throws GameActionException {
        System.out.println("I'm an archon!");

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {

            	Direction dir = randomDirection();
                if(rc.canHireGardener(dir)){
                	rc.hireGardener(dir);
                }

                // Move randomly
                tryMove(randomDirection());

                // Broadcast archon's location for other robots on the team to know
                MapLocation myLocation = rc.getLocation();
                rc.broadcast(0,(int)myLocation.x);
                rc.broadcast(1,(int)myLocation.y);

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

        try{
	        MapLocation[] farm_points = new MapLocation[4];
	        /*scoutMap();
	        MapLocation[] archon_locs = rc.getInitialArchonLocations(rc.getTeam());
	        int amt_archons = archon_locs.length;
	        float[] distance_to_corners = new float[3];
	        
	        //Averages the distances from all archons to a specific corner to find nearest corner on the map
	        //Top-Left corner
	        MapLocation top_left = new MapLocation(left, top);
	        for(MapLocation mp : archon_locs){
	        	distance_to_corners[0] += mp.distanceTo(top_left);
	        }
	        
	        //Top-Right corner
	        distance_to_corners[0] /= amt_archons;
	        MapLocation top_right = new MapLocation(right, top);
	        for(MapLocation mp : archon_locs){
	        	distance_to_corners[1] += mp.distanceTo(top_right);
	        }
	        
	        //Bottom-Left corner
	        distance_to_corners[1] /= amt_archons;
	        MapLocation bottom_left = new MapLocation(left, bottom);
	        for(MapLocation mp : archon_locs){
	        	distance_to_corners[2] += mp.distanceTo(bottom_left);
	        }
	        
	        //Bottom-Right corner
	        distance_to_corners[2] /= amt_archons;
	        MapLocation bottom_right = new MapLocation(right, bottom);
	        for(MapLocation mp : archon_locs){
	        	distance_to_corners[0] += mp.distanceTo(bottom_right);
	        }
	        distance_to_corners[3] /= amt_archons;
	        
	        //Algorithm chooses the smallest of the 4 different corners by comparing the distances
	        MapLocation best_corner = null;
	        if(distance_to_corners[0] <= distance_to_corners[1] &&
	        		distance_to_corners[0] <= distance_to_corners[2] &&
	        		distance_to_corners[0] <= distance_to_corners[3]){
	        	best_corner = top_left;
	        } else if(distance_to_corners[1] <= distance_to_corners[2] &&
	        		distance_to_corners[1] <= distance_to_corners[3]){
	        	best_corner = top_right;
	        } else if(distance_to_corners[2] <= distance_to_corners[3]){
	        	best_corner = bottom_left;
	        } else{
	        	best_corner = bottom_right;
	        }
	        
	        

	    	//Generate 10 new farm points for the gardener to tend over
	        //Test program: Stack 5 trees in an X-row and then rotate around and stack on the other side
	    	//Check if the corner is the top or the bottom of the map, will affect which way the code stacks (will always go away from the center)
	    	if(best_corner.y == top){
	    		//The corner is on the top
	    		if(best_corner.x == left){
	    			//The corner is on the left
	    			for(int i = 0; i<5; i++){
	        			farm_points[i] = new MapLocation((float)(best_corner.x+(i+1)-0.5),(float)(best_corner.y-0.5));
	        		}
	    		}
	    		else{
	    			//The corner is on the right
	    			for(int i = 0; i<5; i++){
	        			farm_points[i] = new MapLocation((float)(best_corner.x+(i-1)+0.5),(float)(best_corner.y-0.5));
	        		}
	    		}
	    	} else{
	    		//On the bottom
	    		if(best_corner.x == left){
	    			//The corner is on the left
	    			for(int i = 0; i<5; i++){
	        			farm_points[i] = new MapLocation((float)(best_corner.x+(i+1)-0.5),(float)(best_corner.y+0.5));
	        		}
	    		}
	    		else{
	    			//The corner is on the right
	    			for(int i = 0; i<5; i++){
	        			farm_points[i] = new MapLocation((float)(best_corner.x+(i-1)+0.5),(float)(best_corner.y+0.5));
	        		}
	    		}
	    	}
	        */
        	farm_points[0] = new MapLocation(rc.readBroadcast(0), rc.readBroadcast(1));
        	farm_points[1] = new MapLocation(rc.readBroadcast(0), rc.readBroadcast(1));
        	farm_points[2] = new MapLocation(rc.readBroadcast(0), rc.readBroadcast(1));
        	farm_points[3] = new MapLocation(rc.readBroadcast(0), rc.readBroadcast(1));
	    	int next_farm_point = 0;
	        // The code you want your robot to perform every round should be in this loop
	        while (true) {
	            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
	            	if(rc.getTeamBullets() > 100.0f && next_farm_point < 4){
	            		//Navigate to the designated MapLocation to plant tree if there is not already tree in location
	            		//And there are enough team bullets, and there are less than 5 trees planted already
	                	float distance_to_target;
	                	/*if(best_corner.y == top){
	                		//Corner spawns on the top
	                		distance_to_target = rc.getLocation().distanceTo(new MapLocation(farm_points[next_farm_point].x, farm_points[next_farm_point].y-1));
	                	} else{
	                		//Corner spawns on the bottom
	                		distance_to_target = rc.getLocation().distanceTo(new MapLocation(farm_points[next_farm_point].x, farm_points[next_farm_point].y+1));
	                	} */
	                	distance_to_target = rc.getLocation().distanceTo(new MapLocation(farm_points[next_farm_point].x, farm_points[next_farm_point].y));
	                	if(distance_to_target > 1f){
	                		tryMove(new Direction(rc.getLocation(), farm_points[next_farm_point]));
	                	} else{
	                		if(rc.canPlantTree(Direction.getNorth())){
	                			//If valid location, plants new tree and increments next location to plant a tree in
	                			rc.plantTree(Direction.getNorth());
	                			next_farm_point++;
	                		} else{
	                			System.out.println("Error, gardener cannot plant tree here in designated area");
	                		}
	                	}
	            	} else if(next_farm_point > 0 && rc.canWater()){
	            		//Check nearby tree HP values of designated trees
	            		//percentageDamaged gives the percent of a tree's health before the gardener should water the tree
	            		float percentageDamaged = 0.8f;
	            		for(int j = 0; j<next_farm_point; j++){
	            			TreeInfo tree_info = rc.senseTreeAtLocation(farm_points[j]);
	            			if(tree_info != null) {
		            			if(tree_info.health <= (percentageDamaged*tree_info.getMaxHealth())){
		        					Direction treeToRobot = new Direction(rc.getLocation(), tree_info.getLocation());
		        					float distance = rc.getLocation().distanceTo(tree_info.getLocation());
		            				if(distance <= GameConstants.BULLET_TREE_RADIUS+1){
		            					rc.water(farm_points[j]);
		            					Clock.yield();
		            				}else{
		            					//Move to the left if the tree is to the left
		            					float x_component = treeToRobot.getDeltaX(distance);
		            					//If x_component is negative, then go west, else go east
		            					if(x_component < 0){
		            						if(x_component < -1){
		            							//Move maximum amount if the robot cannot make it
		            							rc.move(Direction.getWest(), 1);
		            						} else{
		                						rc.move(Direction.getWest(), x_component);
		            						}
		            					} else{
		            						if(x_component > 1){
		            							//Move maximum amount if the robot cannot make it
		            							rc.move(Direction.getEast(), 1);
		            						} else{
		                						rc.move(Direction.getEast(), x_component);
		            						}
		            					}
		            					Clock.yield();
		            				}
		            					//Requires movement closer to the tree, move closer then yield to end turn if still too far away
		            			}
	            			}
	            		}
	            	}
	            	
	                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
	                Clock.yield();
	            }
            } catch (Exception e) {
                System.out.println("Gardener Exception");
                e.printStackTrace();
            }
        }
	
	static float left;
    static float right;
    static float top;
    static float bottom;
    static int height;
    static int width;
    public static void scoutMap() throws GameActionException {
	    float x = rc.getLocation().x;
	    float y = rc.getLocation().y;
	    float tempx = x;
	    float tempy = y;
	    while(rc.onTheMap(new MapLocation(tempx, tempy))) {
	    	tempx++; //GET HIGHEST X, WHICH IS RIGHT
	    }
	    right = tempx; //TEMPX IS AT THE BORDER, THE RIGHT BORDER
	    tempx = x; //RESET VARIABLES
	    while(rc.onTheMap(new MapLocation(tempx, tempy))) {
	    	tempx--; //GET HIGHEST X, WHICH IS LEFT
	    }
	    left = tempx;
	    tempx = x;
	    while(rc.onTheMap(new MapLocation(tempx, tempy))) {
	    	tempy++; //GET HIGHEST Y, WHICH IS TOP
	    }
	    top = tempy;
	    tempy = y;
	    while(rc.onTheMap(new MapLocation(tempx, tempy))) {
	    	tempy--; //GET HIGHEST Y, WHICH IS BOTTOM
	    }
	    bottom = tempy;
	    tempy = y;
	    height = (int) (top - bottom); //NOW THAT WE KNOW TOP AND BOTTOM, THE DIFFERENCE IS THE HEIGHT
	    width = (int) (right - left);
	    //TO GET A CORNER, DO new MapLocation(left, top);
    }

    static void runSoldier() throws GameActionException {
        System.out.println("I'm an soldier!");
        Team enemy = rc.getTeam().opponent();

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
                MapLocation myLocation = rc.getLocation();

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
        boolean moved = false;
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
