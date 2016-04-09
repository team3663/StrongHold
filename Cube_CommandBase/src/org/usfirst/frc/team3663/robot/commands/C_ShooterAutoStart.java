package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_ShooterAutoStart extends Command {

	private int speed;
	int state = 0;
	boolean stop = false;
	double startTime;
	
    public C_ShooterAutoStart(int pSpeed) {
    	requires(Robot.ss_Shooter);
    	speed = pSpeed;
    }

    protected void initialize() {
    	//Robot.ss_Shooter.resetHoldSpeedVars();
		startTime = Timer.getFPGATimestamp();
		stop = false;
		state = 0;
    }

    protected void execute() {
    	if (Robot.visionTable.getBoolean("foundObject: ",false))
    	{
	    	//Robot.ss_Shooter.holdSpeed(speed);
	    	Robot.ss_Shooter.setShooterTopMotorSpeed(1.0);//out both pos
	    	Robot.ss_Shooter.setShooterBottomMotorSpeed(1.0);
	    	switch (state)
	    	{
	    	case 0:
	    		if (Timer.getFPGATimestamp()-startTime > 2.2)
	    		{
	    			state++;
	    		}
	    		break;
	    	case 1:
		    	stop = Robot.ss_Shooter.autoFire;
		    	Robot.gui.sendString("shooter/stop", "autoFire true");
	    		break;
	    	}
    	}
    	else
    	{
    		Robot.gui.sendString("shooter/stop", "not found object");
    		stop = true;
    	}
    }

    protected boolean isFinished() {
    	/*if (Robot.ss_Shooter.fireAnyways)
    	{
    		return (Robot.ss_Shooter.aboveWantedSpeed(speed-600));
    	}*/
        return (stop);// || Robot.ss_Shooter.atSpeed(speed) || Robot.ss_Shooter.fireAnyways);
    }//| | Robot.visionTable.getBoolean("foundObject: ",false)

    protected void end() {
    	if (Robot.visionTable.getBoolean("foundObject: ",false))
    	{
    		Robot.ss_Shooter.fireShooterSolenoid(true);
    	}
    	Robot.ss_Shooter.autoFire = false;
    }

    protected void interrupted() {
    	//end();//if interrupted, not at speed therefore ! fire!!!
    }
}
