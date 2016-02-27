package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_ConfigGoOverDefenses extends CommandGroup {
    
    public  CG_ConfigGoOverDefenses() {
    	addSequential(new C_DartAutoMove(1000));
    	addParallel(new C_WheelyBarAutoMove(0, 1));
    	addSequential(new C_PickupFirePiston(false));
    }
}