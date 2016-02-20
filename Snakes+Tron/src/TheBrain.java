import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;


public class TheBrain implements Runnable{
	public int numOfPlayers = 1;

	public Thread playerOneThread;
	public Thread playerTwoThread;
	public Thread playerThreeThread;
	public Thread playerFourThread;
	public wormAndTail playerOne;
	public wormAndTail playerTwo;
	public wormAndTail playerThree;
	public wormAndTail playerFour;
	
	private JPanel contentPane;
	private Graphics g;
	private boolean eaten = true;
	private boolean crashed = false;
	private int foodX;
	private int foodY;
	private PlayerConnection[] a;
	
	public TheBrain(JPanel jp, PlayerConnection[] A){
		a = A;
		contentPane = jp;
		g = contentPane.getGraphics();
	}

	@Override
	public void run() {
		while(a[0] == null){
		}
		tillDeath();
		// TODO Auto-generated method stub
	}
	
	private void tillDeath(){
		Random r = new Random();
		while(!crashed){
			if(eaten){
				foodX = (r.nextInt(57) + 5) *10;
				foodY = (r.nextInt(57) + 5) *10;
				for(int i = 0; i < a.length;i++){
					foodAssing(a[i].wat);
				}
				eaten = false;
			}
			g.setColor(Color.gray);
			g.drawRect(39, 39, 601, 601);
			g.setColor(Color.YELLOW);
			g.fillRect(foodX, foodY, 10, 10);
			eatenA();
			crashedA();
			setUpCrash();
			updateWorms();
			new ClientInfo().sendToAll();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		setCrashed();
		System.out.println("ended");
	}
	
	public void updateWorms(){
		for(int i = 0; i < a.length; i++){
			a[i].wat.loop();
		}
	}
	
	private void setCrashed(){
		System.out.println("all have died: Setting crash state...");
		for(int i = 0; i < a.length; i++){
			a[i].wat.crash = true;
		}
	}
	
	private void crashedA(){
		for(int i = 0; i < a.length; i++){
			crashed = crashed && a[i].wat.crash;
		}
	}
	
	private void eatenA(){
		for(int i = 0; i < a.length; i++){
			if(a[i].wat.eaten == true){
				eaten = true; 
			}
		}
	}
	
	private void setUpCrash(){
		for(int i = 0; i < a.length; i++){
			for(int b = 0; b < a.length; b++){
				if(a[i] != a[b]){
					checkCrash(a[i].wat, a[b].wat);
				}
			}
		}
	}
	
	private void foodAssing(wormAndTail temp){
		if(temp != null){
			temp.xFood = foodX;
			temp.yFood = foodY;
			temp.eaten = false;
		}
	}
	
	private void checkCrash(wormAndTail wat, wormAndTail wat2){
		if(wat2 != null){
			for(int i = 0; i < wat2.tail.length; i++){
				if((wat.xLoc == wat2.tail[i][0])&&(wat.yLoc == wat2.tail[i][1])){
					wat.crash = true;
				}
			}
		}
	}
}
