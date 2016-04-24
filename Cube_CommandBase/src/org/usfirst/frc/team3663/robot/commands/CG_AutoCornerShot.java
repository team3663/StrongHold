package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_AutoCornerShot extends CommandGroup {
    
    public  CG_AutoCornerShot() {
    	addParallel(new C_ShooterRunMotors(1)); //spin up
    	addParallel(new C_WheelyBarAutoMove(Robot.robotMap.wbMinEnc, 0.6));
    	addSequential(new CG_ConfigLongShot()); //adjust dart angle
    	addSequential(new C_WaitSecs(1));		//wait for spin up
    	addSequential(new C_ShooterFirePiston());	//fire
    	addSequential(new C_DriveBasedTime(1.5,0.6));
    	addParallel(new C_DartAutoMove(Robot.ss_Dart.minDistance()));
    	addSequential(new C_DriveToAngle(90.0, 0.0, 0.7));
    	addSequential(new C_DriveBasedTime(3.5,0.7));
    	
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
