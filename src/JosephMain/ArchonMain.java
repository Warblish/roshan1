package JosephMain;
import battlecode.common.*;

public strictfp class ArchonMain {
    static RobotController rc;
    @SuppressWarnings("unused")
    public static void run() throws GameActionException {
    	rc = RobotPlayer.rc;
        System.out.println("I'm an archon!");
        final int retry_dir_amt = 50;
    	MapLocation[] archon_locs = rc.getInitialArchonLocations(rc.getTeam());
    	
        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {

                // Generate a random direction
                Direction dir = RobotPlayer.randomDirection();

                //First, hire a gardener to place tree infrastructure then place down a wandering gardener
                RobotInfo[] info = rc.senseNearbyRobots(-1, rc.getTeam());
                
                if(rc.getRoundNum() == 1){
                	//Get the amount of farmers on the field
                	int archon_amt = 0;
                	for(MapLocation loc : archon_locs) archon_amt++;
                	if(rc.getRobotCount() - archon_amt == 0){
                		//If the amount of total robots - total archons is zero, there are no farmers yet so hire a farmer
                		//Try 50 times to hire the garden in random directions until the hire is accepted, else randomize direction
                		for(int i = 0; i<retry_dir_amt; i++){
                			if(rc.canHireGardener(dir)){
                            	//Broadcast the farmer request signal to gardeners
                            	rc.broadcast(21, 1);
                				rc.hireGardener(dir);
                				break;
                			} else{
                				dir = RobotPlayer.randomDirection();
                			}
                		}
                		if(rc.getRobotCount()-archon_amt == 0){
                			System.out.println("ARCHON FAILED TO SPAWN FARMER: NO SUITABLE LOCATION");
                			//Try to spawn gardener in 4 random cardinal directions as a last ditch effort
                		}
                	}
                } else if(rc.canHireGardener(dir) && rc.getRoundNum() == 11){
                	//Get the amount of spawners (gardeners) on the field
                	int archon_amt = 0;
                	for(MapLocation loc : archon_locs) archon_amt++;
                	if(rc.getRobotCount() - archon_amt == 1){
                		//If the amount of total robots - total archons is one, there are no spawners yet so hire a spawner
                		//Try 50 times to hire the garden in random directions until the hire is accepted, else randomize direction
                		for(int i = 0; i<retry_dir_amt; i++){
                			if(rc.canHireGardener(dir)){
                            	//Broadcast the spawner request signal to gardeners
                            	rc.broadcast(21, 2);
                				rc.hireGardener(dir);
                				break;
                			} else{
                				dir = RobotPlayer.randomDirection();
                			}
                		}
                		if(rc.getRobotCount()-archon_amt == 0){
                			System.out.println("ARCHON FAILED TO SPAWN FARMER: NO SUITABLE LOCATION");
                			//Try to spawn gardener in 4 random cardinal directions as a last ditch effort
                		}
                	}
                } else if (rc.getRoundNum() > 21 && Math.random() < .01) {
                	for(int i = 0; i<retry_dir_amt; i++){
                		if(rc.canHireGardener(dir)){
                			double selector = Math.random();
                    		if(selector <= 0.60d){
                    			//Ask for a farmer and hire one
                        		rc.broadcast(21, 1);
                        		rc.hireGardener(dir);
                        	} else{
                        		//Ask for a gardener and hire one
                        		rc.broadcast(21, 2);
                        		rc.hireGardener(dir);
                        	}
            				break;
            			} else{
            				dir = RobotPlayer.randomDirection();
            			}
                	}

                }

                // Move randomly
                Movement.tryMove(RobotPlayer.randomDirection());

                // Broadcast archon's location for other robots on the team to know
                MapLocation myLocation = rc.getLocation();
                rc.broadcast(0,(int)myLocation.x);
                rc.broadcast(1,(int)myLocation.y);
                Broadcast.broadcastKillRequest(DataMain.archon_locs[0]);
                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }
        }
	}
}
