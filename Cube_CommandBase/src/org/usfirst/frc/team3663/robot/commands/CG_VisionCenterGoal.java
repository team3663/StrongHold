package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_VisionCenterGoal extends CommandGroup {
    
    public  CG_VisionCenterGoal() {
    	addSequential(new CG_VisionSeeAnyGoal());
    	addSequential(new C_DriveVisionCenterGoal2());
    	//addSequential(new C_DriveVisionCenterGoal());
    }
}
