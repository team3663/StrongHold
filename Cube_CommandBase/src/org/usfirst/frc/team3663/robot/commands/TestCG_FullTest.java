package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestCG_FullTest extends CommandGroup {
    
    public  TestCG_FullTest() {
    	addSequential(new TestC_EnableTestMode());
    	//addSequential(new C_WaitSecs(20));
    	addSequential(new TestC_TestShooter());
    	addSequential(new TestC_TestPickup());
//    	addSequential(new TestC_TestWheelyBar());
//    	addSequential(new TestC_TestDriveTrain());
    	//addSequential(new TestC_TestDart());
    	addSequential(new TestC_TestHook());
    	addSequential(new TestC_TestWinch());
    	addSequential(new TestC_TestDartNoPot());
    	addSequential(new C_WaitSecs(5));
    	addSequential(new TestC_DisableTestMode());
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
