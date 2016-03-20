
package org.usfirst.frc.team3663.robot;

import org.usfirst.frc.team3663.robot.commands.CG_AutoOverBasicDefence;
import org.usfirst.frc.team3663.robot.commands.C_DriveControllerDPad;
import org.usfirst.frc.team3663.robot.commands.C_UpdateGui;
import org.usfirst.frc.team3663.robot.subsystems.SS_AutoChooser;
import org.usfirst.frc.team3663.robot.subsystems.SS_Camera;
import org.usfirst.frc.team3663.robot.subsystems.SS_ConfigReader;
import org.usfirst.frc.team3663.robot.subsystems.SS_Dart;
import org.usfirst.frc.team3663.robot.subsystems.SS_DriveTrain;
import org.usfirst.frc.team3663.robot.subsystems.SS_Hook;
import org.usfirst.frc.team3663.robot.subsystems.SS_Gui;
import org.usfirst.frc.team3663.robot.subsystems.SS_PickupArm;
import org.usfirst.frc.team3663.robot.subsystems.SS_PowerDistributionBoard;
import org.usfirst.frc.team3663.robot.subsystems.SS_Shooter;
import org.usfirst.frc.team3663.robot.subsystems.SS_Test;
import org.usfirst.frc.team3663.robot.subsystems.SS_WheelyBar;
import org.usfirst.frc.team3663.robot.subsystems.SS_Winch;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.xxxcvxds
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	public static RobotMap robotMap;
	public static SS_DriveTrain ss_DriveTrain;
	public static SS_Shooter ss_Shooter;
	public static SS_PickupArm ss_PickupArm;
	public static SS_Dart ss_Dart;
	public static SS_Winch ss_Winch;
	public static SS_WheelyBar ss_WheelyBar;
	public static SS_AutoChooser ss_AutoChooser;
	public static SS_Hook ss_Hook;
	public static SS_PowerDistributionBoard ss_PDB;
	public static SS_Gui gui;
	public static SS_ConfigReader ss_config;
	
	
	public static SS_Test ss_Test;
	public static SS_Camera ss_Camera;


	public static NetworkTable visionTable;// = NetworkTable.getTable("Dog-NT");

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		robotMap = new RobotMap();
    	ss_config = new SS_ConfigReader();
    	
		visionTable = NetworkTable.getTable("Dog-NT");
		
		ss_DriveTrain = new SS_DriveTrain();
		ss_Shooter = new SS_Shooter();
		ss_PickupArm = new SS_PickupArm();
		ss_Dart = new SS_Dart();
		ss_Winch = new SS_Winch();
		ss_WheelyBar = new SS_WheelyBar();
		ss_Camera = new SS_Camera();
		ss_AutoChooser = new SS_AutoChooser();
		ss_Hook = new SS_Hook();
		ss_PDB = new SS_PowerDistributionBoard();
		gui = new SS_Gui();
		ss_Test = new SS_Test();
		oi = new OI();
    	LiveWindow.setEnabled(false);
        gui.sendNumber("operation/Time", Timer.getMatchTime());
        new C_UpdateGui(true);
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    	//oi.canTest(false);
        gui.sendBoolean("operation/Enabled", false);
        gui.sendString("operation/Mode", "Disabled");
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
    public void autonomousInit() {
    	
    	CG_AutoOverBasicDefence auto = new CG_AutoOverBasicDefence();
    	//auto.start();
    	ss_AutoChooser.autoStart();
        gui.sendBoolean("operation/Enabled", true);
        gui.sendString("operation/Mode", "Autonomous");
    }
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        gui.sendNumber("operation/Time",Math.round(100.0*Timer.getMatchTime())/100.0);
    }

    public void teleopInit() {
    	C_DriveControllerDPad dPadControls = new C_DriveControllerDPad();
    	dPadControls.start();
    	ss_DriveTrain.resetGyro();
    	ss_Hook.resetEnc();
		ss_Camera.setLight(true);
        gui.sendBoolean("operation/Enabled", true);
        gui.sendString("operation/Mode", "Teleop");
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        gui.sendNumber("operation/Time",Math.round(100.0*Timer.getMatchTime())/100.0);
        updateDONTREMOVE();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testInit(){
    	//SmartDashboard.putBoolean("TestModeEnabled", true);
    	LiveWindow.setEnabled(false);
    	//oi.canTest(true);
        gui.sendBoolean("operation/Enabled", true);
        gui.sendString("operation/Mode", "Test");
    }
    public void testPeriodic() {
//    	LiveWindow.setEnabled(false);
    	//oi.canTest(true);
    }
    
    public void updateDONTREMOVE(){
    	ss_Shooter.updateDashboard();
    	ss_AutoChooser.updateDashboard();
    }
    
}
