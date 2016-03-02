package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_WinchAndHookCombine extends CommandGroup {
    
    public  CG_WinchAndHookCombine() {
    	double pSpeed = Robot.oi.buttonJoystick.getRawAxis(1);
        addParallel(new C_HookMove2(pSpeed));
        addParallel(new C_WinchMove2 (pSpeed));
    }
}
