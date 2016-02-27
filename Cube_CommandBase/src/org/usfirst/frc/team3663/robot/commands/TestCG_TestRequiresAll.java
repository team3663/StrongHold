package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestCG_TestRequiresAll extends CommandGroup {
    
    public  TestCG_TestRequiresAll() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        addParallel(new TestC_RequiresDart());
        addParallel(new TestC_RequiresDriveTrain());
        addSequential(new TestC_RequiresPickup());
    }
}
