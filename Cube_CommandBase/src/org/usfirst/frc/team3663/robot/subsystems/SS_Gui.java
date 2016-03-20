package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.commands.C_UpdateGui;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SS_Gui extends Subsystem{
	NetworkTable table;
	public SS_Gui(){
		table = NetworkTable.getTable("Gui");
	}
	public void sendNumber(String label, double value){
		table.putNumber(label, value);
	}
	public void sendString(String label, String value){
		table.putString(label, value);
	}
	public void sendBoolean(String label, boolean value){
		table.putBoolean(label, value);
	}
	public double getNumber(String label){
		return table.getNumber(label,3663);
	}
	public String getString(String label){
		return table.getString(label,"3663");
	}
	public boolean getBoolean(String label){
		return table.getBoolean(label,false);
	}
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new C_UpdateGui(true));
		
	}
}