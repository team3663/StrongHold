import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class Video{

    VideoCapture camera;
    boolean keyHit = false;

	public Video(){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        camera = new VideoCapture();
        //(new Thread(new Video())).start();
	}
	
	public void startCapture() {

        // need to copy C:\Users\kingb_000\Desktop\opencv\sources\3rdparty\ffmpeg\opencv_ffmpeg_64.dll
		// to C:\WINDOWS\system32\opencv_ffmpeg300_64.dll
        
    	Mat mBGR = new Mat();
    	BufferedImage image = null;
    	byte[] targetPixels = null;
    	byte[] b = null;
    	int type=BufferedImage.TYPE_3BYTE_BGR;;
    	
		Scanner sc = new Scanner(System.in);
		String key="";
		
		// get one image to get sizes
		if (!openCamera()){
			System.out.println("failed to open camera");
			sc.close();
			System.exit(0);
		}
		
    	camera.read(mBGR);

    	if (mBGR == null){
    		System.out.println("failed to read image from camera");
    		sc.close();
    		System.exit(0);
    	}
    	
    	camera.release();
    	
   	    b = new byte[mBGR.channels()*mBGR.cols()*mBGR.rows()];
   	    mBGR.get(0,0,b); // get all the pixels
   	    image = new BufferedImage(mBGR.cols(),mBGR.rows(), type);
     	    
   	    targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
   	    System.arraycopy(b, 0, targetPixels, 0, b.length);  

   	    displayImage(image);
        System.out.println("Captured Frame Width " + mBGR.width());
        System.out.println("Captured Frame Height " + mBGR.height());
        System.out.println("Captured Frame Channels " + mBGR.channels());
        System.out.println("Captured Frame cols " + mBGR.cols());
        System.out.println("Captured Frame depth " + mBGR.depth());
        System.out.println("Captured Frame rows " + mBGR.rows());
        System.out.println("Captured Frame continuous " + mBGR.isContinuous());
        System.out.println("Captured Frame size.Height " + mBGR.size().height);
        System.out.println("Captured Frame size.width " + mBGR.size().width);
		
        int imageNum = 0;
    	System.out.println("enter key for next picture");
        key = sc.next();
		while (!key.equals("q")){
			

			if (!openCamera()){
				System.out.println("failed to open camera");
				sc.close();
				System.exit(0);
			}
			
	    	camera.read(mBGR);

	    	if (mBGR == null){
	    		System.out.println("failed to read image from camera");
	    		sc.close();
	    		System.exit(0);
	    	}
	    	
	    	camera.release();
	    	
	   	    mBGR.get(0,0,b); // get all the pixels
	   	    System.arraycopy(b, 0, targetPixels, 0, b.length);  
    	    displayImage(image);
    	    

	    	String filename = "c:\\workspace\\castle"+(imageNum++)+".png";
    	    try {
    	        // retrieve image
    	        File outputfile = new File(filename);
    	        ImageIO.write(image, "png", outputfile);
    	    } catch (IOException e) {
    	    	e.printStackTrace();

    	    }
	    	    
	    	System.out.println("displayed and saved to "+filename);
	    	
	    	System.out.println("enter key for next picture");
	    	key = sc.next();
        }
        System.out.println("OK");
        sc.close();
	}
	
	private boolean openCamera(){
		
		camera.open("http://root:password@10.36.63.100/mjpg/video.mjpg");
        //	camera.open(0); webcam
        
        for (int i = 0; i<10; i++){
	        if (camera.isOpened())
	        	break;
	        
	        System.out.println("waiting for camera...");

	        try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        
        if(!camera.isOpened()){
            System.out.println("Camera Error");
            return false;
        }
        else{
            System.out.println("Camera Ready");
            return true;
        }
	}
	
//    public BufferedImage Mat2BufferedImage(Mat input){
//	// source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
//	// Fastest code
//	// The output can be assigned either to a BufferedImage or to an Image
//	
//	    int type = BufferedImage.TYPE_BYTE_GRAY;
//	    if ( input.channels() > 1 ) {
//	        type = BufferedImage.TYPE_3BYTE_BGR;
//	    }
//	    int bufferSize = input.channels()*input.cols()*input.rows();
//	    byte [] data = new byte[bufferSize];
//	    input.get(0,0,data); // get all the pixels
//	    BufferedImage image = new BufferedImage(input.cols(),input.rows(), type);
//	    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
//	    System.arraycopy(data, 0, targetPixels, 0, data.length);  
//	    return image;
//
//    }

    JFrame jframe = null;
    Graphics g=null;
    
    public void displayImage(Image img)
    {
        if (jframe == null)
        {
	        jframe=new JFrame();
	        jframe.setLayout(new FlowLayout());        
	        jframe.setSize(img.getWidth(null)+50, img.getHeight(null)+50);     
	        jframe.setVisible(true);
	        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        jframe.toFront();
	        g = jframe.getGraphics();
	        
	        try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        g.drawImage(img, 0, 0, null);
    }	
}

//for(int k=0; k<mGray.height(); k++){
////System.out.print(k+": ");
//for(int j=0; j<mGray.width(); j++){
//	mGray.get(k, j, pixel);
//
	//System.out.print(pixel[0]+","+pixel[1]+","+pixel[2]+" ");
	
	
//	if (((pixel[0]>=0) && (pixel[0]<=10))&&((pixel[1]<-40) && (pixel[1]>-90))&&((pixel[2]>=0) && (pixel[2]<=10))){
//	//if ((pixel[1]<-100)){
////		System.out.println(pixel[0]+", "+pixel[1]+", "+pixel[2]);
//		pixel[0]=0;
//		pixel[1]=-127;
//		pixel[2]=0;
//		
//	}
//	else
//	{
//		pixel[0]=0;
//		pixel[2]=0;
//		pixel[1]=0;
//	}

//	if (pixel[1]<50)
//		
//	//if ((k&1) == 0)
//		mGray.put(k, j, 0);
//	else if (pixel[1]<100)
//		mGray.put(k, j, 100);
//	else if (pixel[1]<150)
//		mGray.put(k, j, 150);
//	else if (pixel[1]<200)
//		mGray.put(k, j, 200);
//	else
		
//	mGray.put(k, j, pixel);
//}
////System.out.println();
//}
