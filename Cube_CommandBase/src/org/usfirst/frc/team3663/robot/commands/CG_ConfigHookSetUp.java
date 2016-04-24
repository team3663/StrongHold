package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_ConfigHookSetUp extends CommandGroup {
    
    public  CG_ConfigHookSetUp() {
    	addSequential(new C_DartAutoMove(100));//TODO:Find Actual dart pos
    }
}
