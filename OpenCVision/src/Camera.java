//package src;

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
	}
	
}
