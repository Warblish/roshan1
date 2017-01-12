package JosephMain;
import battlecode.common.*;



//STATIC CLASS TO HANDLE DODGE CODE
public strictfp class Movement {
    static RobotController rc;
    @SuppressWarnings("unused")

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
        boolean moved = false;
        int currentCheck = 0;

        while(currentCheck<=checksPerSide) {
            // Try the offset of the left side
            if(rc.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
            	if(currentCheck > 3 && !(swerveRight&&isSwerving)) {
            		swerveRight = false;
            		isSwerving = true;
                    rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck));
                    prevDir = dir.rotateLeftDegrees(degreeOffset*currentCheck);
                    return true;
            	} else {
            		if(isSwerving) {
            			if(dir.rotateLeftDegrees(degreeOffset*currentCheck).getAngleDegrees() - prevDir.opposite().getAngleDegrees() < resetThreshold) {
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
            			if(dir.rotateRightDegrees(degreeOffset*currentCheck).getAngleDegrees() - prevDir.opposite().getAngleDegrees() < resetThreshold) {
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
}
