package JosephMain;
import battlecode.common.*;

public strictfp class ArchonMain {
    static RobotController rc;
    static int gardenersHired = 0;
    static int lastHire = 0;
    final static int retry_dir_amt = 50;
    @SuppressWarnings("unused")
    public static void run() throws GameActionException {
    	rc = RobotPlayer.rc;
        System.out.println("I'm an archon!");
    	MapLocation[] archon_locs = rc.getInitialArchonLocations(rc.getTeam());
    	MapLocation[] enemy_archon_locs = rc.getInitialArchonLocations(rc.getTeam().opponent());  	
        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
        		RobotInfo[] enemies = rc.senseNearbyRobots(3, rc.getTeam().opponent());
        		for(int i = 0; i<enemies.length; i++){
        			if(enemies[i].getType() == RobotType.LUMBERJACK ||
        					enemies[i].getType() == RobotType.SOLDIER ||
        					enemies[i].getType() == RobotType.TANK||
        					enemies[i].getType() == RobotType.SCOUT){
        				Broadcast.broadcastThreat(enemies[i]);
        				break;
        			}
        		}
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
                		//Try unlimited times to hire the garden in random directions until the hire is accepted, else randomize direction
                		if(rc.getTeamBullets() > 200){
                			while(!rc.canHireGardener(dir)){
                    			dir = RobotPlayer.randomDirection();
                    		}
                			//Broadcast the farmer request signal to gardeners
                        	rc.broadcast(11, 1);
            				rc.hireGardener(dir);
            				lastHire = rc.getRoundNum();
                		} else{
                			System.out.println("NOT ENOUGH BULLETS TO SPAWN GARDENER (FARMER)");
                		}
                	}
            		if(rc.getRobotCount()-archon_amt == 0){
            			System.out.println("ARCHON FAILED TO SPAWN FARMER: NO SUITABLE LOCATION");
            			//Try to spawn gardener in 4 random cardinal directions as a last ditch effort
            		} 
                } else if (rc.getRoundNum() > 21 && rc.readBroadcast(421) == 0) { //no emeies spotted SPAM GARDENERS
                	tryHire();
                } else if (rc.getRoundNum() > 21 && rc.readBroadcast(420) < 3) {
                	//some enemies, can macro this later
                	if(rc.getRoundNum() - lastHire >= 20) { //hire every 20 turns? need to improve this
                		tryHire();
                	}
                } //otherwise dont spend on gardener, buy units instead
            
                //
                if(rc.getRoundNum() > 700 && (rc.readBroadcast(91)+rc.readBroadcast(92)+rc.readBroadcast(93)) < 5) { //less than 5 lumberjacks have died
                	System.out.println("ATTTTAAAAACCCCKKKKKKK " + (rc.readBroadcast(91)+rc.readBroadcast(92)+rc.readBroadcast(93)) + "have died");
                	Broadcast.broadcastKillRequest(enemy_archon_locs[0], 1);
                }

                // Move randomly
                Movement.tryMove(RobotPlayer.randomDirection());

                // Broadcast archon's location for other robots on the team to know
                //Broadcast.broadcastKillRequest(DataMain.archon_locs[1]);
                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }
        }
	}
    public static void tryHire() throws GameActionException {
    	Direction dir = RobotPlayer.randomDirection();
    	for(int i = 0; i<retry_dir_amt; i++){
    		if(rc.canHireGardener(dir)){
    			rc.broadcast(11, 1);
    			rc.hireGardener(dir);
    			lastHire = rc.getRoundNum();
			} else{
				dir = RobotPlayer.randomDirection();
			}
    	}
    }
}
