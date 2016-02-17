package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_PickupSafteyArm extends Command {

    public C_PickupSafteyArm() {
    	requires(Robot.ss_PickupArm);
    }

    protected void initialize() {
    }
    
    protected void execute() {
    	if(Robot.ss_Dart.inPickupZone() && !Robot.ss_PickupArm.isDown()){
    		Robot.ss_PickupArm.firePickupSolenoid(true);
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

   protected void interrupted() {
	   end();
    }
}
