package JosephMain;
import battlecode.common.*;



//STATIC CLASS TO HANDLE DODGE CODE
public strictfp class Movement {
    static RobotController rc;
    private static boolean chopping = true;
    public static float chopDegreeThreshold = 100;
    public static void setChopping(boolean c) {
    	chopping = c;
    }
    public static void enableChopping() { 
    	chopping = true;
    }
    public static void disableChopping() {
    	chopping = false;
    }
    static Direction randomDirection() {
        return new Direction((float)Math.random() * 2 * (float)Math.PI);
    }
    static void wander(MapLocation center) throws GameActionException {
    	Direction dir = new Direction(center, rc.getLocation());
    	System.out.println("Wandering away from " + center);
    	System.out.println("Wander direction " + dir);
    	tryMove(dir);
    }
    static boolean tryMove(Direction dir) throws GameActionException {
        return tryMove(dir,20,3);
    }
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
        	boolean chopFlag = true;
            if(chopping && currentCheck > 2 && rc.getType() == RobotType.LUMBERJACK && chopFlag) {
            	chopFlag = false; //only do this once per function call
            	TreeInfo[] trees = rc.senseNearbyTrees();
            	System.out.println(trees.length);
            	for(int i = 0; i < trees.length; i++) {
            		TreeInfo tree = trees[i];
            		if(tree.getTeam() != rc.getTeam()) { //not friendly tree
            			if(Math.abs(dir.degreesBetween(new Direction(rc.getLocation(), tree.getLocation()))) < chopDegreeThreshold) {
            			//if the trees direction is within 40 (or threshold) degrees of our direction
            				if(rc.canChop(tree.getLocation())) {
            					rc.chop(tree.getLocation());
            					return true;
            				}
            			}
            		}
            	}
            }
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
    public static boolean swerveRight = false;
    public static boolean isSwerving = false;
    public static Direction prevDir;
    static boolean tryMoveSwerve(Direction dir) throws GameActionException {
    	return tryMoveSwerve(dir, 20, 7, 40);
    }
    static boolean tryMoveSwerve(Direction dir, float degreeOffset, int checksPerSide, float resetThreshold) throws GameActionException {
        // First, try intended direction
        /*if (rc.canMove(dir)) { //WE DONT WANT THIS, I CHANGED CUURENT CHECK TO START AT 0 TO ACCOUNT THIS WITH THE SWERVE ALGORITHM
            rc.move(dir);
            return true;
        }*/

        // Now try a bunch of similar angles
        int currentCheck = 0;
        while(currentCheck<=checksPerSide) {
            // Try the offset of the left side
        	boolean chopFlag = true;
            if(chopping && currentCheck > 2 && rc.getType() == RobotType.LUMBERJACK && chopFlag) {
            	chopFlag = false; //only do this once per function call
            	TreeInfo[] trees = rc.senseNearbyTrees();
            	System.out.println(trees.length);
            	for(int i = 0; i < trees.length; i++) {
            		TreeInfo tree = trees[i];
            		if(tree.getTeam() != rc.getTeam()) { //not friendly tree
            			if(Math.abs(dir.degreesBetween(new Direction(rc.getLocation(), tree.getLocation()))) < chopDegreeThreshold) {
            			//if the trees direction is within 40 (or threshold) degrees of our direction
            				if(rc.canChop(tree.getLocation())) {
            					rc.chop(tree.getLocation());
            					return true;
            				}
            			}
            		}
            	}
            }
            if(rc.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
            	if(currentCheck > 3 && !(swerveRight&&isSwerving)) {
            		swerveRight = false;
            		isSwerving = true;
                    rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck));
                    prevDir = dir.rotateLeftDegrees(degreeOffset*currentCheck);
                    return true;
            	} else {
            		if(isSwerving) {
            			if(dir.rotateLeftDegrees(degreeOffset*currentCheck).getAngleDegrees() - prevDir.opposite().getAngleDegrees() < resetThreshold && swerveRight) {
            				//DONT GO BACK TO WHERE WE JUST WERE
            				
            			} else {
            				//this is ok, carry on
                			isSwerving = false;
                            rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck));
                            //prevDir = dir.rotateLeftDegrees(degreeOffset*currentCheck);
                            return true;           				
            			}
            		} else {
            			isSwerving = false;
                        rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck));
                        //prevDir = dir.rotateLeftDegrees(degreeOffset*currentCheck);
                        return true;
            		}
            	}
            }
            // Try the offset on the right side
            if(rc.canMove(dir.rotateRightDegrees(degreeOffset*currentCheck))) {
            	if(currentCheck > 3 && !(!swerveRight && isSwerving)) {//not swerving left already
            		swerveRight = true;
            		isSwerving = true;
                    rc.move(dir.rotateRightDegrees(degreeOffset*currentCheck));
                    prevDir = dir.rotateRightDegrees(degreeOffset*currentCheck);
                    return true;
            	} else {
            		if(isSwerving) {
            			if(dir.rotateRightDegrees(degreeOffset*currentCheck).getAngleDegrees() - prevDir.opposite().getAngleDegrees() < resetThreshold && !swerveRight) {
            				//DONT GO BACK TO WHERE WE JUST WERE
            				
            			} else {
            				//this is ok, carry on
                			isSwerving = false;
                            rc.move(dir.rotateRightDegrees(degreeOffset*currentCheck));
                            //prevDir = dir.rotateRightDegrees(degreeOffset*currentCheck);
                            return true;           				
            			}
            		} else {
            			isSwerving = false;
                        rc.move(dir.rotateRightDegrees(degreeOffset*currentCheck));
                        //prevDir = dir.rotateRightDegrees(degreeOffset*currentCheck);
                        return true;
            		}
            	}
            }
            // No move performed, try slightly further
            currentCheck++;
        }

        // A move never happened, so return false.
        return false;
    }
    
    static boolean tryMoveSwerveAmount(Direction dir, float degreeOffset, int checksPerSide, float resetThreshold, float distanceToMove) throws GameActionException {
        // First, try intended direction
        /*if (rc.canMove(dir)) { //WE DONT WANT THIS, I CHANGED CUURENT CHECK TO START AT 0 TO ACCOUNT THIS WITH THE SWERVE ALGORITHM
            rc.move(dir);
            return true;
        }*/

        // Now try a bunch of similar angles
        int currentCheck = 0;
        while(currentCheck<=checksPerSide) {
            // Try the offset of the left side
        	boolean chopFlag = true;
            if(chopping && currentCheck > 2 && rc.getType() == RobotType.LUMBERJACK && chopFlag) {
            	chopFlag = false; //only do this once per function call
            	TreeInfo[] trees = rc.senseNearbyTrees();
            	System.out.println(trees.length);
            	for(int i = 0; i < trees.length; i++) {
            		TreeInfo tree = trees[i];
            		if(tree.getTeam() != rc.getTeam()) { //not friendly tree
            			if(Math.abs(dir.degreesBetween(new Direction(rc.getLocation(), tree.getLocation()))) < chopDegreeThreshold) {
            			//if the trees direction is within 40 (or threshold) degrees of our direction
            				if(rc.canChop(tree.getLocation())) {
            					rc.chop(tree.getLocation());
            					return true;
            				}
            			}
            		}
            	}
            }
            if(rc.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
            	if(currentCheck > 3 && !(swerveRight&&isSwerving)) {
            		swerveRight = false;
            		isSwerving = true;
                    rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck), distanceToMove);
                    prevDir = dir.rotateLeftDegrees(degreeOffset*currentCheck);
                    return true;
            	} else {
            		if(isSwerving) {
            			if(dir.rotateLeftDegrees(degreeOffset*currentCheck).getAngleDegrees() - prevDir.opposite().getAngleDegrees() < resetThreshold && swerveRight) {
            				//DONT GO BACK TO WHERE WE JUST WERE
            				
            			} else {
            				//this is ok, carry on
                			isSwerving = false;
                            rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck), distanceToMove);
                            //prevDir = dir.rotateLeftDegrees(degreeOffset*currentCheck);
                            return true;           				
            			}
            		} else {
            			isSwerving = false;
                        rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck), distanceToMove);
                        //prevDir = dir.rotateLeftDegrees(degreeOffset*currentCheck);
                        return true;
            		}
            	}
            }
            // Try the offset on the right side
            if(rc.canMove(dir.rotateRightDegrees(degreeOffset*currentCheck))) {
            	if(currentCheck > 3 && !(!swerveRight && isSwerving)) {//not swerving left already
            		swerveRight = true;
            		isSwerving = true;
                    rc.move(dir.rotateRightDegrees(degreeOffset*currentCheck), distanceToMove);
                    prevDir = dir.rotateRightDegrees(degreeOffset*currentCheck);
                    return true;
            	} else {
            		if(isSwerving) {
            			if(dir.rotateRightDegrees(degreeOffset*currentCheck).getAngleDegrees() - prevDir.opposite().getAngleDegrees() < resetThreshold && !swerveRight) {
            				//DONT GO BACK TO WHERE WE JUST WERE
            				
            			} else {
            				//this is ok, carry on
                			isSwerving = false;
                            rc.move(dir.rotateRightDegrees(degreeOffset*currentCheck), distanceToMove);
                            //prevDir = dir.rotateRightDegrees(degreeOffset*currentCheck);
                            return true;           				
            			}
            		} else {
            			isSwerving = false;
                        rc.move(dir.rotateRightDegrees(degreeOffset*currentCheck), distanceToMove);
                        //prevDir = dir.rotateRightDegrees(degreeOffset*currentCheck);
                        return true;
            		}
            	}
            }
            // No move performed, try slightly further
            currentCheck++;
        }

        // A move never happened, so return false.
        return false;
    }
}
