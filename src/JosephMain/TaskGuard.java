package JosephMain;
import battlecode.common.*;

public strictfp class TaskGuard extends Task {
    private MapLocation target;
    private Team enemy = rc.getTeam().opponent();
    private float threshold = 1;
    //The gardener you are guarding
    private RobotInfo gardener;
    @Override
    public void runTurn() throws GameActionException {
		
    	super.runTurn();
	}
    public void setGardener(RobotInfo g) {
    	gardener = g;
    }
    public TaskGuard() {
    	super();
    }
    @Override
    public boolean isComplete() {
    	return false;
    }
}
