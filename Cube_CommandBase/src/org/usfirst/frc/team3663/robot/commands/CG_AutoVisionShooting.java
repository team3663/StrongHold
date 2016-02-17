package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_AutoVisionShooting extends CommandGroup {
    
    public  CG_AutoVisionShooting() {
    	
    	//fix tomorrow
    /*	addSequential(new C_VisionCenterGoal());
    	addSequential(new C_EncoderCurveDrive());
    	addParallel(new C_VisionCenterGoal());
    	if (Math.abs(table.getNumber("Moving/MoveAngle: ",0)) > 15)
    	{
    		addSequential (C_EncoderCurveDrive());
    	}
		addSequential(new CG_VisionFineAdjust);
		*/
		
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
