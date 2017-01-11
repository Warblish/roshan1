package JosephMain;
import battlecode.common.*;

public strictfp class Task {
    static RobotController rc;
    protected boolean complete = false;
    @SuppressWarnings("unused")
    public void runTurn() throws GameActionException {
    	
	}
    public Task() {
    	rc = RobotPlayer.rc;
    }
    public boolean isComplete() {
    	return complete;
    }
    
}
