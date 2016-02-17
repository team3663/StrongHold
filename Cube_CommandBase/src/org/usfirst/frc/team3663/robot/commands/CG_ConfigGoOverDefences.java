package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_ConfigGoOverDefences extends CommandGroup {
    
    public  CG_ConfigGoOverDefences() {
    	addSequential(new C_DartAutoMove(Robot.ss_Dart.maxPickupSafe()));
    	addSequential(new C_PickupFirePiston(false));
    }
}
