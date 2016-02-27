package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class CG_AutoVisionShooting extends CommandGroup {
    
	NetworkTable table = Robot.visionTable;
	
    public  CG_AutoVisionShooting() {
    	
    	double angle;// = table.getNumber("Moving/MoveAngle: ",0);
    	double dist;// = table.getNumber("Moving/MoveDistance: ",0);
    	//fix tomorrow
    	addSequential(new C_DriveVisionCenterGoal());
    	if (table.getBoolean("Moving/MoveSideways: ",false))
    	{
        	angle = table.getNumber("Moving/MoveAngle: ",0);
        	dist = table.getNumber("Moving/MoveDistance: ",0);
	    	addSequential(new TC_TurnByGyro(angle/Math.abs(angle)*90));//+-1*90
	    	addSequential(new C_DriveEncoderCurve(angle,dist));
	    	addSequential(new TC_TurnByGyro(-angle/Math.abs(angle)*90));
	    	addSequential(new C_DriveVisionCenterGoal());
    	}
    	if (table.getBoolean("ShooterArm/moveShooterArm: ", false))
    	{
    		addSequential(new C_DriveVisionFineAdjust());
    	}
		
		
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
