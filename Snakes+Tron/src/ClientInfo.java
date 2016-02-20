import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class ClientInfo {
	
	public static Graphics g;
	public static ObjectOutputStream[] oos = new ObjectOutputStream[0];
	public static int location;
	public static int numberOP;
	public static Object[] DataPacket;
	
	public void updateThings(String a, Color c){
		g.copyArea(650, 0, 800, 620, 0, -15);
		g.setColor(Color.BLACK);
		g.fillRect(650, 620-15, 150, 15);
		g.setColor(Color.WHITE);
		g.drawString(": " + a, 665, 620-15);	
		if(c == null){
			g.setColor(Color.RED);
			g.fillOval(652, 595, 10, 10);
		}
		else{
			g.setColor(c);
			g.fillRect(652, 595, 10, 10);
		}
	}
	
	public static void setGrapics(Graphics G){
		g = G;
	}
	
	public static void setoos(ObjectOutputStream pOOS){
		ObjectOutputStream[] temp = new ObjectOutputStream[oos.length];
		for(int i = 0; i < oos.length; i++){
			temp[i] = oos[i];
		}
		oos = new ObjectOutputStream[oos.length + 1];
		for(int i = 0; i < temp.length; i++){
			oos[i] = temp[i];
		}
		oos[location] = pOOS;
		location++;
	}
	
	public static void incrementDP(int size){
		DataPacket = new Object[size];
	}
	
	public static void ServerSend(String msg, Color c){
		Object[] a = new Object[3];
		a[0] = "SERVER";
		a[1] = msg;
		a[2] = c;
		try {
			for(int i = 0; i < oos.length; i++){
				oos[i].writeObject(a);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static int a;
	public void sendtoClient(int[][] one, int[] two, Color three, int[] four, boolean five, int six, String seven){
		Object[] placing = new Object[7];
		placing[0] = one;
		placing[1] = two;
		placing[2] = three;
		placing[3] = four;
		placing[4] = five;
		placing[5] = six;
		placing[6] = seven;
		DataPacket[six-1] = placing;
	}
	
	public void sendToAll(){
		System.out.println("update");
		for(int i = 0; i < oos.length; i++){
			try {
				Object[] t = (Object[]) DataPacket[i];
				if(oos[i] != null){
					oos[i].reset();
					oos[i].writeObject(DataPacket);
					}
				a++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
	}
}
