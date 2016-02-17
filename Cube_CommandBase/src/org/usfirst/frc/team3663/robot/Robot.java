
package org.usfirst.frc.team3663.robot;

import org.usfirst.frc.team3663.robot.commands.C_DriveControllerDPad;
import org.usfirst.frc.team3663.robot.subsystems.SS_Camera;
import org.usfirst.frc.team3663.robot.subsystems.SS_Dart;
import org.usfirst.frc.team3663.robot.subsystems.SS_DriveTrain;
import org.usfirst.frc.team3663.robot.subsystems.SS_PickupArm;
import org.usfirst.frc.team3663.robot.subsystems.SS_Shooter;
import org.usfirst.frc.team3663.robot.subsystems.SS_Test;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	public static RobotMap robotMap;
	public static SS_DriveTrain ss_DriveTrain;
	public static SS_Shooter ss_Shooter;
	public static SS_PickupArm ss_PickupArm;
	public static SS_Dart ss_Dart;
	public static SS_Test test;
	public static NetworkTable visionTable;
	public static SS_Camera ss_Camera;


    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		robotMap = new RobotMap();
		ss_DriveTrain = new SS_DriveTrain();
		ss_Shooter = new SS_Shooter();
		ss_PickupArm = new SS_PickupArm();
		ss_Dart = new SS_Dart();
		ss_Camera = new SS_Camera();
		oi = new OI();
		test = new SS_Test();
    	LiveWindow.setEnabled(false);
		visionTable = NetworkTable.getTable("Dog-NT");
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    	SmartDashboard.putBoolean("TestModeEnabled", false);
    	//oi.canTest(false);
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
//    public void autonomousInit() {
//    	
//    }
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	C_DriveControllerDPad test = new C_DriveControllerDPad();
    	test.start();
		ss_Camera.setLight(true);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	updateDashboard();
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testInit(){
    	//SmartDashboard.putBoolean("TestModeEnabled", true);
    	LiveWindow.setEnabled(false);
    	//oi.canTest(true);
    }
    public void testPeriodic() {
//    	SmartDashboard.putBoolean("TestModeEnabled", true);
//    	LiveWindow.setEnabled(false);
    	//oi.canTest(true);
    }
    
    private void updateDashboard(){					//responsible for updating the dash board
    	SmartDashboard.putString("Update Status", "Running");
    	ss_Dart.updateDashboard();
    	ss_DriveTrain.updateDashboard();
    	ss_PickupArm.updateDashboard();
    	ss_Shooter.updateDashboard();
    }
}
