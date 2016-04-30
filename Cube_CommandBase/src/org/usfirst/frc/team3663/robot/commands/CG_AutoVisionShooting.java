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
    	
    	/////////addParallel(new C_HoldSpeedToFlag(-25000));
    	/////////addSequential(new C_WaitSecs(0.25));//may see old flag before C_HoldSpeedToFlag resets it
		
    	///////addParallel(new CG_VisionCenterGoal());
    	addSequential(new CG_VisionSeeAnyGoal());
    	addParallel(new C_DriveVisionCenterGoal2());
    	//-->addSequential(new C_VisionDartCenterGoal());
    	///////addSequential(new C_DriveVisionCenterGoal());
		
		addSequential(new C_WaitSecs(0.6));
		////////addSequential(new C_DartAutoMove(1761));
		addSequential(new C_DartPrepareForShot());
	
		addSequential(new CG_AutoShoot(-25000));//-24000
		
		//----------------------------------------------
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
