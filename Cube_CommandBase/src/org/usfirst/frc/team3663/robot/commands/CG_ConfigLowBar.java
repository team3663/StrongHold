package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_ConfigLowBar extends CommandGroup {
    
    public  CG_ConfigLowBar() {
    	addSequential(new C_PickupFirePiston(true));
    	addParallel(new C_WheelyBarAutoMove(Robot.ss_WheelyBar.maxDistance(), 1));
    	addSequential(new C_DartAutoMove(Robot.ss_Dart.minDistance()));
    }
}
