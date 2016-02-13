import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Start implements Runnable{

	public static void main(String[] args) {
		//LogReader lr = new LogReader();
		//lr.readWordFile();
		
		//new Start().run();
		Video v = new Video();
		//v.run();
		v.startCapture();
	}
	
	public void run(){
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("10.36.63.78");
		NetworkTable table = NetworkTable.getTable("datatable");
		
		while (true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try 
			{
				double value;
				for (int i=0; i<16; i++){
					value = table.getNumber("pdpC"+i,100);
					System.out.print(value+",");
		        }
				value = table.getNumber("pdpTotalC",200);
				System.out.print(value+",");
				value = table.getNumber("pdpInputV",300);
				System.out.print(value+",");
				value = table.getNumber("encoder1",400);
				System.out.print(value+",");
				value = table.getNumber("encoder2",500);
				System.out.print(value+",");
				value = table.getNumber("LIDAR",600);
				System.out.println(value);
			}
			catch  ( edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException e) 
			{
				System.out.println(e.getMessage());
			}
		}
	}
}
