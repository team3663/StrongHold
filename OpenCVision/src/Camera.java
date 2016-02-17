package src;

import java.awt.Component;
import java.awt.Image;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;
public class Camera {
	
	static CameraRun camRun;
	static GreenMass greenMass;
	
	public static void main(String[] args)
	{
		camRun = new CameraRun();
		camRun.CameraInit();
//		while (true)
		{
			camRun.run();
		}
		/*
		int[] testArray = {1,1,0,0,1};
		greenMass = new GreenMass(3,0,testArray,(3+testArray.length-1));
		int[] testArray2 = {1,0,1,1,1,1,1,1,1,1,1};
		greenMass.addToMass(2,1,testArray2,(2+testArray2.length-1));
		int[] testArray3 = {1,1,1,1};
		greenMass.addToMass(2,1,testArray3,(2+testArray3.length-1));
		*/
	}
	
}
