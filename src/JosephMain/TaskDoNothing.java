package JosephMain;
import battlecode.common.*;

public strictfp class TaskDoNothing extends Task {
    @Override
    public void runTurn() throws GameActionException {
    	super.runTurn();
	}
    public TaskDoNothing() {
    	super();
    	complete = true;
    }
}
