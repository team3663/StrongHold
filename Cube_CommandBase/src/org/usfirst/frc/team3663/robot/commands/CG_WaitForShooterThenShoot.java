package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_WaitForShooterThenShoot extends CommandGroup {
    
    public  CG_WaitForShooterThenShoot() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
    	addSequential(new C_ShooterWaitForSpeed(10));
    	addSequential(new C_ShooterFirePiston());
    }
}
