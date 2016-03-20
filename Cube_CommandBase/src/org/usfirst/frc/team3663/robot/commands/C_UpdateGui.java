package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_UpdateGui extends Command {
	boolean run = false;
	long startTime;
    public C_UpdateGui(boolean run) {
        requires(Robot.gui);
        this.run = run;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	long currentTime = System.currentTimeMillis();
    	if((currentTime - startTime) > 20){
    		Robot.ss_Camera.updateDashboard();
    		Robot.ss_Dart.updateDashboard();
    		Robot.ss_DriveTrain.updateDashboard();
    		Robot.ss_Hook.updateDashboard();
    		Robot.ss_PDB.updateDashboard();
    		Robot.ss_PickupArm.updateDashboard();
    		Robot.ss_Shooter.updateDashboard();
    		Robot.ss_WheelyBar.updateDashboard();
    		Robot.ss_Winch.updateDashboard();
    		startTime = currentTime;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return run;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
