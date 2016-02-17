package org.usfirst.frc.team3663.robot.subsystems;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Gui {
	NetworkTable table;
	public Gui(){
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
}
