//package src;

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import edu.wpi.first.wpilibj.networktables.*;

public class CameraRun {
	
	MassObjectHolder massObjectPointer;
	VideoCapture camera;
	Mat mat;
	JFrame frame;
	Component com;
	String videoStreamAddress = "http://root:password@10.36.63.100/mjpg/video.mjpg";
	String videoStreamAddress2 = "http://root:password@10.36.63.101/mjpg/video.mjpg";
	ImageIcon image;//, imageTest;
	BufferedImage buffImg;
	JLabel label;
	NetworkTable table;
	
	//int[] rows, colmns;
	int[][] pic;
	int gPieceKey = -1;
	int bestPieceKey;
	int[][] U = new int[640][480];//size[196][149];
	double maxDistortedAngle = 20;
	int goalCenterX = -1,goalCenterY;
	int fixWidth = 640;
	int fixHeight = 480;
	double angle, distance;
	
//	boolean foundObject = false;
	
	boolean inAutonomous,autoFindLeft,alreadyInCommand,centeringGoal/*,movingWithRadius*/,okayToShoot;
	
	public void CameraInit()
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		camera = new VideoCapture(videoStreamAddress);
		mat = new Mat();
		frame = new JFrame("Dog's Eyes <o> . <o>");
		label = new JLabel();
		buffImg = null;
		
		massObjectPointer = new MassObjectHolder();
		
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("10.36.63.20");//78");
		table = NetworkTable.getTable("Dog-NT");

		setRedU();
		
		table.putBoolean("autoInitFindLeft: ",true);
		table.putBoolean("Mode/commandRunning: ",false);//may not need
		table.putBoolean("Mode/inAutonomous: ",false);//may not need
		table.putBoolean("C_/centeringGoal: ",true);
		//table.putBoolean("C_/movingWithRadius: ",false);
		table.putBoolean("C_/okayToShoot: ",false);
	}

	int buffingCounter = 0;
	boolean cameraFound = false;
	
	private void checkCameraStillFound()
	{
		if (buffingCounter++%100 == 99)
		{
			if (camera.isOpened())
			{
				cameraFound = true;
			}
			else 
			{
				cameraFound = false;
			}
		}
	}
	
	public void run()
	{
	//	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	//	CameraInit();
		if (camera.isOpened())
		{
			cameraFound = true;
			System.out.println("Yay! I see something  ");
			camera.read(mat);
			updateJFrame(mat);

			frame.setSize(mat.width()+25,mat.height()+35);
			//frame.setSize(640+25, 480+25);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			while (true)
			{
				if (cameraFound)
				{
					checkCameraStillFound();
					camera.read(mat);
					updateJFrame(mat);
					
			//		table.putBoolean("foundObject: ",(gPieceKey>-1));//may not need
					//make a put in Camera Init
					autoFindLeft = table.getBoolean("autoInitFindLeft: ",true);
					alreadyInCommand = table.getBoolean("Mode/commandRunning: ",false);//may not need
					inAutonomous = table.getBoolean("Mode/inAutonomous: ",false);//may not need
					centeringGoal = table.getBoolean("C_/centeringGoal: ",false);
					//movingWithRadius = table.getBoolean("C_/movingWithRadius: ",false);
					okayToShoot = table.getBoolean("C_/okayToShoot: ",false);
					
					if (gPieceKey>-1)
					{
						findCenterGoal();
						//if (!movingWithRadius)
						{
							centeringGoal = centeringGoal();
						}
						//if (!centeringGoal)
						{
							okayToShoot = isFineAdjustedGoal();
						}
						table.putBoolean("C_/centeringGoal: ", centeringGoal);
						//table.putBoolean("C_/movingWithRadius: ", movingWithRadius);
						table.putBoolean("C_/okayToShoot: ", okayToShoot);
					}
				}
				else
				{
					checkCameraStillFound();
				}
			}
		}
		else
		{
			System.out.println("camera not found!");
		}
	}
	public void resetVariables()
	{
	//	foundObject = false;
		gPieceKey = -1;
		massObjectPointer.dumpPast();
	}
	public void updateJFrame(Mat Mat)
	{
//		frame.remove(label);//not sure this is needed?
		buffImg = getUsableImage(Mat);
/*		try {
//			buffImg = ImageIO.read(new File("C:\\Users\\angel_000\\Pictures\\Screenshots\\CutSS.png"));

//			buffImg = ImageIO.read(new File("C:\\Users\\angel_000\\Pictures\\Screenshots\\ForAngelique\\castle0.png"));
//			buffImg = ImageIO.read(new File("C:\\Users\\angel_000\\Pictures\\Screenshots\\ForAngelique\\castle1.png"));
//			buffImg = ImageIO.read(new File("C:\\Users\\angel_000\\Pictures\\Screenshots\\ForAngelique\\castle2.png"));
//			buffImg = ImageIO.read(new File("C:\\Users\\angel_000\\Pictures\\Screenshots\\ForAngelique\\castle3.png"));
//			buffImg = ImageIO.read(new File("C:\\Users\\angel_000\\Pictures\\Screenshots\\ForAngelique\\castle4.png"));
//			buffImg = ImageIO.read(new File("C:\\Users\\angel_000\\Pictures\\Screenshots\\ForAngelique\\castle4.png"));
			//castle5 will be trouble when too slanted
//			buffImg = ImageIO.read(new File("C:\\Users\\angel_000\\Pictures\\Screenshots\\ForAngelique\\castle5.png"));
//			buffImg = ImageIO.read(new File("C:\\Users\\angel_000\\Pictures\\Screenshots\\ForAngelique\\castle6.png"));
//			buffImg = ImageIO.read(new File("C:\\Users\\angel_000\\Pictures\\Screenshots\\ForAngelique\\castle7.png"));
//			buffImg = ImageIO.read(new File("C:\\Users\\angel_000\\Pictures\\Screenshots\\ForAngelique\\castle8.png"));
//			buffImg = ImageIO.read(new File("C:\\Users\\angel_000\\Pictures\\Screenshots\\ForAngelique\\castle9.png"));
//			buffImg = ImageIO.read(new File("C:\\Users\\angel_000\\Pictures\\Screenshots\\ForAngelique\\castle10.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		///*
				//cleanImageToMass(buffImg);
		//pic = newImgArray(buffImg);
		//findRectangle(buffImg);//good for if tilted with right side higher, but will need to change findU to get that (overall better)
		//findU(buffImg);
		//System.out.println("--------------------printing New Image");
		resetVariables();
		separateObjects();
		removeSmallObjects();//remember later to get rid of extra removeSmallObject() methods
	//	table.putNumber("gPieceKey: ", gPieceKey);
		table.putBoolean("foundObject: ", (gPieceKey > -1));
		if (gPieceKey>-1) ///To help Delay, lower res or slow down frames per sec!!!!!!!!!!!!!!!!!!!!!!!
		{
	//		getMostMassObject();
			getBestObjectMask();
		}
		//*/
		image = new ImageIcon(buffImg);
		label.setIcon(image);
		frame.add(label);
		frame.repaint();
	}
	private BufferedImage getUsableImage(Mat input)
	{
		BufferedImage image = null;
		image = new BufferedImage(input.width(), input.height(), BufferedImage.TYPE_3BYTE_BGR);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        input.get(0, 0, data);
        
	    return convertToBlackGreenImage(image);
	}
	private BufferedImage convertToBlackGreenImage(BufferedImage img)
	{
	/*	rows = new int[img.getHeight()];
		colmns = new int[img.getWidth()];*/
		Color c;
		int width = img.getWidth();
		int height = img.getHeight();
		pic = new int[width][height];
		int g = Color.GREEN.getRGB();
		int cy = Color.CYAN.getRGB();
		int bl = Color.BLUE.getRGB();
		int r = Color.RED.getRGB();
		int b = Color.black.getRGB();
		for(int y = 0; y<height; y++)
		{
			for(int x = 0; x<width; x++)//make it ignore left corner!!!!!!!!!!!
			{
				c = new Color(img.getRGB(x,y));
				if (c.getRed()<60 && /*c.getBlue()<210 &&*/ c.getGreen()>=210)
				{
					if (okayToShoot && !centeringGoal)
					{
						img.setRGB(x, y, g);
					}
					else if (centeringGoal)
					{
						img.setRGB(x, y, cy);
					}
					else
					{
						img.setRGB(x, y, bl);
					}
					pic[x][y] = 1;				
				}
				else if (isRedLine(x))//isRedU(x,y))
				{
					img.setRGB(x, y, r);
				}
				else
				{
			//		img.setRGB(x, y, b);
				}
			}
		//	for (double counter = -9.0; counter < 9999.0; counter+=0.1)
			{
				
			}
		}
		return img;
	}
	
	
/*	public BufferedImage convertToBlackGreenImage(BufferedImage img)
	{
	/*	rows = new int[img.getHeight()];
		colmns = new int[img.getWidth()];*./
		Color c;
		int width = img.getWidth();
		int height = img.getHeight();
		pic = new int[width][height];
		int g = Color.GREEN.getRGB();
		int r = Color.RED.getRGB();
	//	int b = Color.black.getRGB();
		for(int y = 0; y<height; y++)
		{
			for(int x = 0; x<width; x++)
			{
				c = new Color(img.getRGB(x,y));
				if (c.getRed()<90/* && c.getBlue()<190*./ && c.getGreen()>=170)
				//if (x == 1)
				{
					img.setRGB(x, y, g);
					pic[x][y] = 1;				
				}
				else if (isRedU(x,y))
				{
					img.setRGB(x, y, r);
				}
				else
				{
			//		img.setRGB(x, y, b);
				}
			}
		}
		return img;
	}*/

	public boolean moveWithAngleRadius(int key)
	{
		double angle = getAngleTilt(key);
		double distance = getDistanceMass(key);
		if (distance > 130)
		{
			angle*=2;
		}
		else if (distance < 65)
		{
			angle*=2/3;
		}
		
		table.putNumber("Moving/MoveAngle: ", angle);
		table.putNumber("Moving/MoveRadius: ", distance);
		
		if (angle < maxDistortedAngle && angle > -maxDistortedAngle)
		{
			table.putBoolean("Moving/MoveSideways: ", false);//may not need
			return false;
		}
		table.putBoolean("Moving/MoveSideways: ", true);//may not need
		return true;
	}
	
/*	public void findU(BufferedImage img)//in progress...
	{
		int xCornerT = 1, xCornerB = 638;
		int yCornerT = 1, yCornerB = 478;
		boolean firstGreen = true;
		boolean sawGreen = false;
		for (int y = 1; y < pic[0].length-1; y++)//img.getHeight()-1
		{
			for (int x = xCornerT; x < pic.length-1; x++)//img.getWidth()-1
			{
				boolean alreadyGreenStreak = false;
				if (pic[x][y] > 0)//(img.getRGB(x,y) == Color.green.getRGB())
				{
					alreadyGreenStreak = true;
					if (firstGreen)
					{
						xCornerT = x;
						yCornerT = y;
						firstGreen=!firstGreen;
						sawGreen = true;
					}
				//	else if (x>xCornerB)
					{
						xCornerB = x;
					}
					yCornerB = y;
				}
				else if (alreadyGreenStreak)
				{
					break;
				}
			}
		}
	//	if (Math.abs(xCornerT-xCornerB)<)//adding looking <---o for box that has right side higher than left
		colorSquare(img,xCornerT, yCornerT, Color.ORANGE);
		colorSquare(img,xCornerB, yCornerB, Color.ORANGE);
		int avgX = (xCornerB+xCornerT)/2;
		int avgY = (yCornerB+yCornerT)/2;
		if (!sawGreen)
		{
			avgX = 1;
		}
		colorSquare(img, avgX, avgY, Color.MAGENTA);
		table.putNumber("gMass.Middle X: ", avgX);
		table.putNumber("gMass.Middle Y: ", avgY);
	}*/
	
	/*	public void findRectangle(BufferedImage img)
	{
		int xCornerT = 1, xCornerB = 638;
		int yCornerT = 1, yCornerB = 478;
		boolean firstGreen = true;
		for (int y = 1; y < img.getHeight()-1; y++)
		{
			for (int x = 1; x < img.getWidth()-1; x++)
			{
				boolean alreadyGreenStreak = false;
				if (img.getRGB(x,y) == Color.green.getRGB())
				{
					alreadyGreenStreak = true;
					if (firstGreen)
					{
						xCornerT = x;
						yCornerT = y;
						firstGreen=!firstGreen;
					}
				//	else if (x>xCornerB)
					{
						xCornerB = x;
					}
					yCornerB = y;
				}
				else if (alreadyGreenStreak)
				{
					break;
				}
			}
		}
		colorSquare(img,xCornerT, yCornerT, Color.YELLOW);
		colorSquare(img,xCornerT, yCornerB, Color.YELLOW);
		colorSquare(img,xCornerB, yCornerT, Color.YELLOW);
		colorSquare(img,xCornerB, yCornerB, Color.YELLOW);
	//	System.out.println("Top: " + xCornerT + ", " + yCornerT);
	//	System.out.println("Bottom: " + xCornerB + ", " + yCornerB);
		int avgX = (xCornerB+xCornerT)/2;
		int avgY = (yCornerB+yCornerT)/2;
		colorSquare(img, avgX, avgY, Color.RED);
		table.putNumber("gMass.Middle X: ", avgX);
		table.putNumber("gMass.Middle Y: ", avgY);
	}*/
	
	public void separateObjects()//BufferedImage img)//if 5 or 3 pixels of black, then keep scanning
	{
		int lineLength = 0;
		int startX = -1,endX = startX, startY = 0, compStartX, compStartY, compEndX;
		boolean beganLine = false;
		boolean alreadyAdded;
		int alreadyAddedKey;
		
		for (int y = 0; y < pic[0].length; y++)
		{
			for (int x = 0; x < pic.length; x++)
			{
				if (pic[x][y] > 0)
				{
					if (!beganLine)
					{
						beganLine = true;
						startX = x;
						startY = y;
					}
					endX = x;
					lineLength++;
				}
				if(beganLine && (x == 639 || (pic[x][y] == 0)))
				{ 
					alreadyAdded = false;
					alreadyAddedKey = -1;
					if (gPieceKey>-1)
					{
						for (int i = 0; i <= gPieceKey; i++)
						{
							compStartX = massObjectPointer.getGPiece(i).xStart;
							compStartY = massObjectPointer.getGPiece(i).yStart + massObjectPointer.getGPiece(i).height;
//							System.out.println("startY: " + startY);
//							System.out.println("compStartY: yStart " + massObjectPointer.getGPiece(i).yStart  + " + height " + massObjectPointer.getGPiece(i).height  + "= "+ compStartY);
							compEndX = massObjectPointer.getGPiece(i).xEnd;
//							System.out.println((boolean)(startY == compStartY) + "		" + (boolean)((startX <= compStartX && endX >= compStartX) || (endX >= compEndX && startX <= compEndX)) + "          " + alreadyAdded);
							if ((startY == compStartY)
									&& ((startX >= compStartX && endX <= compEndX) ||
										 (startX <= compStartX && endX >= compStartX) || 
										 (endX >= compEndX && startX <= compEndX)))
							{
								if (!alreadyAdded)
								{
									//....
	//								System.out.println("=====================addToMass Called");
									massObjectPointer.gPieceAddToMass(i,startX,startY,lineLength,endX);
									alreadyAdded = true;
									alreadyAddedKey = i;
								}
								else
								{
									//need to finish code to add two masses
//									System.out.println("--------------------=========combining Masses");
									massObjectPointer.gPieceCombineMass(alreadyAddedKey, i);
									gPieceKey--;
									i--;
								}
							}
							else if (i == gPieceKey)//if not && gone through whole array of objects
							{
		//						System.out.println("========" + gPieceKey + "===============newGreenMassInit");
								massObjectPointer.GreenMassInit(++gPieceKey, startX, startY,lineLength, endX);
								break;//need to break because if not, increased gPieceKey meaning when back at beginning, for loop is true and continue again
							}
						}
					}
					else
					{
	//					System.out.println("===========================================firstObject=======================" + gPieceKey + "===============================");
						massObjectPointer.GreenMassInit(++gPieceKey, startX, startY, lineLength, endX);
					}
					beganLine = false;//then add or make greenmass
					lineLength = 0;
				}
			}
		}
	}
	
	public void removeSmallObjects()
	{
		for (int o = 0; o <= gPieceKey; o++)
		{
			if (massObjectPointer.getGPiece(o).mass < 1000)
			{
				massObjectPointer.removeMass(o);
		//		System.out.println("removing object " + o);
				gPieceKey--;
				o--;
			}
		}
	}
	
	public void checkWithObjectRatio(int keyNum)
	{
		getAngleTilt(keyNum);
		if (massObjectPointer.getGPiece(keyNum).width/massObjectPointer.getGPiece(keyNum).height > 1.67)
		{
			//good thing
			table.putBoolean("ShootingMin: ", true);
			table.putNumber("x: ", massObjectPointer.getGPiece(keyNum).xStart+(massObjectPointer.getGPiece(keyNum).width/2));
			table.putNumber("y: ", massObjectPointer.getGPiece(keyNum).yStart+(massObjectPointer.getGPiece(keyNum).height/8));
		}
		
	}
	private double getAngleTilt(int keyNum)
	{
		double angle = 1;
		int w = massObjectPointer.getGPiece(keyNum).width, h = 0;
		boolean leftSeen = false, rightSeen = false;
		int xstart = massObjectPointer.getGPiece(keyNum).xStart;
		int ystart = massObjectPointer.getGPiece(keyNum).yStart;
		
		for (int y = massObjectPointer.getGPiece(keyNum).height-1; y > 0 && !(leftSeen && rightSeen); y--)
		{
			for (int x = 0; x < w; x++)
			{
				if (pic[xstart+x][ystart+y] > 0)
				{
					if (x > massObjectPointer.getGPiece(keyNum).width-15)
					{
						rightSeen = true;
					}
					if (x < 15)
					{
						if (!rightSeen)
						{
							angle = -1;
						}
						leftSeen = true;
					}
				}
			}
			if (leftSeen || rightSeen)
			{
				h++;
			}
		}
		
		angle*=((Math.atan2(h,w))*180/(3.141593));
		
		//System.out.println("angle: " + angle);//max should be 60 degrees typically...for best goal
		
		return angle;
	}
	boolean firstTime;
	double[] massArr = new double[5];
	int counter = 0;
	private double getDistanceMass(int keyNum)
	{
		double d = 0;
		
		massArr[counter%5] = (double)massObjectPointer.getGPiece(keyNum).mass;
		table.putNumber("massB: ",massArr[counter%5]);
		if (firstTime)
		{
			for (int i = 0; i < 5; i++)
			{
				massArr[i] = massArr[0];
			}
		}counter++;
		
		table.putNumber("0mass: ", massArr[0]);
		table.putNumber("1mass: ", massArr[1]);
		table.putNumber("2mass: ", massArr[2]);
		table.putNumber("3mass: ", massArr[3]);
		table.putNumber("4mass: ", massArr[4]);
		double mass = ((massArr[0]+massArr[1]+massArr[2]+massArr[3]+massArr[4])/5)*27.5/21.0;
		table.putNumber("mass: ",mass);
		
		d = (-93.5*Math.log(mass)) + 858.41;
		//distance dog d = ((-0.067*mass)+351.24)*mass/4000;//4048.36;
		//distance direct real time d = 0.000003*mass^2 - 0.0374*mass + 201.84
		
		return d;
	}
	
/*	private double getDistanceTape(int keyNum)
	{
		double d = 0;
		
		int xstart = massObjectPointer.getGPiece(keyNum).xStart;
		int ystart = massObjectPointer.getGPiece(keyNum).yStart;
		int h = massObjectPointer.getGPiece(keyNum).height;
		int w = massObjectPointer.getGPiece(keyNum).width;
		for (int x = 0; x < w/10; x++)
		{
			if (pic[(xstart+x)][(ystart+h/2)] > 0)
			{
				d++;
			}
		}
		
		switch ((int)d)
		{
			case 10:
				d = 190;
				break;
			case 17:
				d = 59;
				break;
			case 20:
				d = 54;
				break;
			default:
				d = (-14.6*d)+336;
				break;
		}
		if (d < 0)
		{
			d = -1;
		}
		table.putNumber("d: ",d);
		
		return d;
	}*/
	
/*	public void getMostMassObject()
	{
		int bestPiece = 0;

		//removeSmallObjects();

		if (gPieceKey > 0)
		{
			for (int o = 0; o <= gPieceKey; o++)
			{
				if (massObjectPointer.getGPiece(bestPiece).mass < massObjectPointer.getGPiece(o).mass)
				{
					bestPiece =o;
				}
			}
		}	
		int xCenter = (massObjectPointer.getGPiece(bestPiece).xEnd+massObjectPointer.getGPiece(bestPiece).xStart)/2;
		int ycenter = ((massObjectPointer.getGPiece(bestPiece).yStart)+(massObjectPointer.getGPiece(bestPiece).height)/2);
		colorSquare(buffImg,xCenter,ycenter,Color.RED);
		
		table.putNumber("gMass.Middle X: ", xCenter);
		table.putNumber("gMass.Middle Y: ", ycenter);//yCenter);
	}*/
	
	public void getBestObjectMask()//actual ratio 1.42 (20x14) and general average ratio 1.63 (w x h)
	{
		int bestPiece = 0;

	//	removeSmallObjects();

		if (gPieceKey > 0)
		{
			boolean bestPieceChanged = false;
			
			int x = massObjectPointer.getGPiece(bestPiece).xStart,y = massObjectPointer.getGPiece(bestPiece).yStart,
				w = massObjectPointer.getGPiece(bestPiece).width,h = massObjectPointer.getGPiece(bestPiece).height;
			for (int o = 1; o <= gPieceKey; o++)
			{
	//			System.out.println("checking mass #: " + o);
				if (bestPieceChanged)
				{
					x = massObjectPointer.getGPiece(bestPiece).xStart;
					y = massObjectPointer.getGPiece(bestPiece).yStart;
					w = massObjectPointer.getGPiece(bestPiece).width;
					h = massObjectPointer.getGPiece(bestPiece).height;
				}
				
				int cX = massObjectPointer.getGPiece(o).xStart,cY = massObjectPointer.getGPiece(o).yStart,
						cW = massObjectPointer.getGPiece(o).width,cH = massObjectPointer.getGPiece(o).height;
				
				if (massObjectPointer.getGPiece(o).mass > 50 || cW > 5 || cH > 5)
				{
					double cMaskOverlap = percentMaskOverlap(cX,cY,cW,cH);
					double maskOverlap = percentMaskOverlap(x,y,w,h);
//					System.out.println("cPercentOverlap: " + cMaskOverlap);
	//				System.out.println("percentOverlap: " + maskOverlap);
		//			System.out.println("x: " + cX + ", y: " + cY + ", w: " + cW + ", h: " + cH);
					//		System.out.println("pO: " + maskOverlap);
					table.putNumber("cMaskOverlap: ", cMaskOverlap);
					table.putNumber("MaskOverlap: ", maskOverlap);
				//	if (Math.abs(a))
					if (cMaskOverlap > maskOverlap)
					{
						bestPiece = o;
						bestPieceChanged = true;
					}
				/*	else if (((cMaskOverlap <= maskOverlap+10)&&(cMaskOverlap >= maskOverlap-10) || (maskOverlap <= cMaskOverlap+10)&&(maskOverlap >= cMaskOverlap-10)) && (massObjectPointer.getGPiece(bestPiece).mass < massObjectPointer.getGPiece(o).mass))
					{
						bestPiece = o;
						bestPieceChanged = true;
					}*/
					else
					{
						bestPieceChanged = false;
					}
				}
			}

		}

		if (gPieceKey > -1)
		{
	//		checkWithObjectRatio(bestPiece);
			bestPieceKey = bestPiece;
			
			int xCenter = (massObjectPointer.getGPiece(bestPiece).xEnd+massObjectPointer.getGPiece(bestPiece).xStart)/2;
	//		int ycenter = ((massObjectPointer.getGPiece(bestPiece).yStart)+(massObjectPointer.getGPiece(bestPiece).height/2));
			int yCenter = ((massObjectPointer.getGPiece(bestPiece).yStart)+(massObjectPointer.getGPiece(bestPiece).height/8));
		//	int yCenter = ((massObjectPointer.getGPiece(bestPiece).yStart)+(massObjectPointer.getGPiece(bestPiece).height/2));
			//use coordinates of best object and get center
			
			colorSquare(buffImg,massObjectPointer.getGPiece(bestPiece).xStart,massObjectPointer.getGPiece(bestPiece).yStart, Color.blue);
			colorSquare(buffImg,massObjectPointer.getGPiece(bestPiece).xStart+massObjectPointer.getGPiece(bestPiece).width,massObjectPointer.getGPiece(bestPiece).yStart+massObjectPointer.getGPiece(bestPiece).height,Color.blue);
			
			colorSquare(buffImg, xCenter, yCenter, Color.RED);
			
			table.putNumber("bestGoal X: ", xCenter);
			table.putNumber("bestGoal Y: ", yCenter);
		//	massObjectPointer.dumpPast();
			//	gPieceKey = -1;
		}
	}

	private double percentMaskOverlap(int xStart, int yStart, int width, int height)
	{
		double percentOverlap;
		double tapeDepth = ((height/6.0)+4*(width/10.0))/5.0;
		
		table.putNumber("tapeDepth: ", tapeDepth);
		
	//	int[][] mask = createMask(width, height);//index: 0-width; 1-height; 2-tapeDepth
		double overlapMask = 0, totalGreen = 1;
		
		for (int y = yStart; y < yStart + height; y++)
		{
			for (int x = xStart; x < xStart + width; x++)
			{
				if ((x <= xStart+tapeDepth) //left vertical block
						|| (x >= xStart+width-tapeDepth)//right vertical block
						|| (y >= yStart+height-tapeDepth))
				{
					if (pic[x][y] > 0)
					{
						overlapMask++;
						totalGreen++;
					}
					else
					{
						//overlapMask-=2;
					}
				}
				else if (pic[x][y] > 0)
				{
					totalGreen++;
					overlapMask-=0.05;
				}
		/*		if (mask[x-xStart][y-yStart] == pic[x][y])
				{
					overlapMask++;
					totalGreen++;
				}
				else if (pic[x][y] > 0)
				{
					totalGreen++;
					overlapMask-=0.5;
				}
				else if (mask[x-xStart][y-yStart] == 1 && pic[x][y] == 0)
				{
					overlapMask-=0.5;
				}*/
			}
		}
		percentOverlap = overlapMask/totalGreen*100.0;//for reading. may remove variable later
		
		return percentOverlap;
	}

	private void findCenterGoal()//range of x: 308/310-361/360 through 30"(2'6")-204"(17')
	{
		angle = getAngleTilt(bestPieceKey);
		distance = getDistanceMass(bestPieceKey);
		table.putNumber("distanceByMass: ", distance);
		
		if (distance < 64)
		{
			goalCenterX = 356;
			//goalCenterY = 
		}
		else if (distance < 78)
		{
			goalCenterX = 347;
			//goalCenterY = 
		}
		else if (distance < 97)
		{
			goalCenterX = 340;
			//goalCenterY = 
		}
		else if (distance < 160)
		{
			goalCenterX = 330;
			//goalCenterY = 
		}
		else if (distance < 186)
		{
			goalCenterX = 325;
			//goalCenterY = 
		}
		else//321//322
		{
			goalCenterX = 315;
			//goalCenterY = 
		}
		/*//for final bot
		 * if (distance < 64)
		{
			goalCenterX = 640-356;
		}
		else if (distance < 78)
		{
			goalCenterX = 640-347;
		}
		else if (distance < 97)
		{
			goalCenterX = 640-340;
		}
		else if (distance < 160)
		{
			goalCenterX = 640-330;
		}
		else if (distance < 186)
		{
			goalCenterX = 640-325;
		}
		else//321//322
		{
			goalCenterX = 640-315;
		}
		 */
	}
	
	private boolean centeringGoal()
	{//can compress alot more later
		int xCenter = massObjectPointer.getGPiece(bestPieceKey).xStart + (massObjectPointer.getGPiece(bestPieceKey).width/2);
		//int yCenter = massObjectPointer.getGPiece(bestPieceKey).yStart + (massObjectPointer.getGPiece(bestPieceKey).height/8);
		
		double moveAngle = 45.0*((double)xCenter-320.0)/320.0;//*6;
		
		if (gPieceKey > -1)
		{
			if (xCenter < goalCenterX-10)
			{
				table.putNumber("cameraMoveAngle: ", moveAngle);
				table.putBoolean("turnLeft: ", true);
				table.putString("needsTurning: ", "TurnLeft");
			}
			else if (xCenter > goalCenterX+10)
			{
				table.putNumber("cameraMoveAngle: ", moveAngle);
				table.putBoolean("turnLeft: ", false);
				table.putString("needsTurning: ", "TurnRight");
				return true;
			}
			else
			{
				table.putNumber("cameraMoveAngle: ", 0);
				table.putString("needsTurning: ", "StopTurning");
				return false;
			}
		}
		else
		{
			if (autoFindLeft)
			{
				table.putNumber("cameraMoveAngle: ", -360);
			}
			else
			{
				table.putNumber("cameraMoveAngle: ", 360);
			}
		}/*
		else if (autoFindLeft)//maybe put something that saved if seen object and which way it disappeared
		{
			table.putBoolean("leftTurn: ", true);
			return true;
		}
		else
		{
			table.putBoolean("leftTurn: ", false);
			return true;
		}*/
		return table.getBoolean("turnLeft: ",true);
	}
	
	private boolean isFineAdjustedGoal()
	{
/*		boolean raiseShooterArm = true;
		boolean moveShooterArm = false;
	//	int xCenter = massObjectPointer.getGPiece(bestPieceKey).xStart + (massObjectPointer.getGPiece(bestPieceKey).width/2);
		int yCenter = massObjectPointer.getGPiece(bestPieceKey).yStart + (massObjectPointer.getGPiece(bestPieceKey).height/8);

		if (yCenter < goalCenterY-15)
		{
			moveShooterArm = true;
			raiseShooterArm = true;
		}
		else if (yCenter > goalCenterY+15)
		{
			moveShooterArm = true;
			raiseShooterArm = false;
		}

		if (moveShooterArm)
		{
			table.putBoolean("ShooterArm/raiseShooterArm: ", raiseShooterArm);
		}
		table.putBoolean("ShooterArm/moveShooterArm: ", moveShooterArm);
		*/
		/*double dist = getDistanceMass(bestPieceKey);
		double angle = getAngleTilt(bestPieceKey);*/
		return (/*(Math.abs(angle) < maxDistortedAngle) && !moveShooterArm &&*/table.getBoolean("finishedMovingPot: ",false) && !centeringGoal && distance < 15*12);
	}
	
	private void colorSquare(BufferedImage img, int x, int y, Color color)
	{
		if (x > 639)
		{
			x = 639;
		}
		else if (x < 0)
		{
			x = 0;
		}
		if (y > 479)
		{
			y = 479;
		}
		else if (y < 0)
		{
			y = 0;
		}
								    	img.setRGB(x, y, color.getRGB());
		if (x < 639)			   		img.setRGB(x+1, y, color.getRGB());
		if (x > 0)			   			img.setRGB(x-1, y, color.getRGB());
		if (y < 479)			   		img.setRGB(x, y+1, color.getRGB());//out of bounds exception
		if (x < 639 && y < 479)		img.setRGB(x+1, y+1, color.getRGB());
		if (x > 0 && y < 479)  		img.setRGB(x-1, y+1, color.getRGB());
		if (y > 0)			   			img.setRGB(x, y-1, color.getRGB());
		if (x < 639 && y > 0)  		img.setRGB(x+1, y-1, color.getRGB());
		if (x > 0 && y > 0)    		img.setRGB(x-1, y-1, color.getRGB());
	}
	//(226,171) (395,171)
	//x: 196, y: 149, 22
	private boolean isRedLine(int x)
	{
		return (x == goalCenterX);
	}
	private boolean isRedU(int X, int Y)//overlapOnly? or all mask red?
	{
		return (U[X][Y]==1);
	}
	private void setRedU()
	{
		//for (int y = 170; y < 170+149; y++)
		{
			for (int x = 225; x < 225+22; x++)
			{
				U[x][170] = 1;
				U[x][171] = 1;
			//	img.setRGB(x+225, y+170, Color.RED.getRGB());
				
			}
			for (int x = 421; x > 421-22; x--)
			{
				U[x][170] = 1;
				U[x][171] = 1;
			//	img.setRGB(x+225, y+170, Color.RED.getRGB());
			}
		//	if (y > 297)
			{
				for (int x = 225; x < 422; x++)
				{
					U[x][318] = 1;
					U[x][319] = 1;
					U[x][297] = 1;
					U[x][298] = 1;
				//	img.setRGB(x+225, y+170, Color.RED.getRGB());
				}
			}
			for (int y = 171; y < 318; y++)
			{
				U[225][y] = 1;
				U[226][y] = 1;
				U[421][y] = 1;
				U[420][y] = 1;
				U[247][y] = 1;
				U[246][y] = 1;
				U[398][y] = 1;
				U[399][y] = 1;
			}
		}
	}
}
