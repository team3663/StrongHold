package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_ConfigNormalShot extends CommandGroup {
    
    public  CG_ConfigNormalShot() {
    	addSequential(new C_PickupFirePiston(true));
    	//addParallel(new C_WheelyBarAutoMove(10,-1.0));
    	addSequential(new C_DartAutoMove(2170));
    	addSequential(new C_PickupFirePiston(false));
    	
    }
}
