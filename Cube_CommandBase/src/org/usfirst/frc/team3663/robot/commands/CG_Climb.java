package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_Climb extends CommandGroup {
    
    public  CG_Climb() {
    	addParallel(new C_DriveBasedTime(30.0, 0.3, 0)); //drive backwards continually
    	addSequential(new C_WaitSecs(4.0));
//    	addParallel(new C_HookSetPiston());
//    	addSequential(new C_DartAutoMove()); //hook out + dart up (kick out the hook)
//    	addSequential(new C_HookAutoMove()); //hook extend (get the hook up behind the the bar)
//    	addSequential(new C_DartAutoMove());//dart down (a little, to make the hook make contact with the bar)
////    	---attached---
//    	addParallel(new C_HookAutoMove()); //hook retract + quick dart up/down (pull the extender away from the hook)
//    	addSequential(new C_DartAutoMove());
//    	addSequential(new C_DartAutoMove());
//    	addParallel(new C_HookSetPiston());//hook in + dart down a little, to bring the hook arm down
//    	addSequential(new C_DartAutoMove());
//    	winch up
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
