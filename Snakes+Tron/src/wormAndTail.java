import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;


public class wormAndTail{
	public boolean crash = false;
	public boolean tailChange = false;
	public boolean eaten = false;
	public boolean start = false;
	public int tailLenght = 20;
	public int[][] tail = new int[tailLenght][2];
	public int newLenght = 20;
	public int xSpeed = 5;
	public int ySpeed = 0;
	public int head = 0;
	public int speed = 10;
	public int deadZone = 5;
	public int screenHeight = 630;
	public int screenWidth = 630;	
	public int yLoc = 40;
	public int xLoc = 40;
	public int xFood;
	public int yFood;
	public Color c;
	private boolean moving = false;
	
	private Graphics g;
	private int player;
	private Random r = new Random();
	private int death = 0;
	
	
	public void loop(){
		moving = false;
		if(!crash){
			move();
			change();
			eaten();
			for(int i = 0; i < tail.length; i++){
				g.setColor(c);
				g.fillRect(tail[i][0], tail[i][1], 10, 10);
				chrased(i);
			}
			if(head+1 >= tailLenght){
				head = 0;
			}
			else{
				head++;
			}
			screenCheck();
			sendToClient();
			clear();
		}
		else{
			if(death == 0){
				new ClientInfo().updateThings("died", c);
				msg = "Has died an unheroic death";
				for(int i = 0; i < tail.length; i++){
					g.setColor(Color.BLACK);
					g.fillRect(tail[i][0], tail[i][1], 10, 10);
					g.setColor(c);
					g.drawRect(tail[i][0], tail[i][1], 10, 10);
					tail[i][0] = 0;
					tail[i][1] = 0;
					death++;
					System.out.println("Worm " + player + " has died");
				}
			}
		}
		sendToClient();
		msg = null;
	}
	
	private void eaten(){
		if(xLoc == xFood && yLoc == yFood){
			new ClientInfo().updateThings("Became more powerfull", c);
			msg = "has eaten the feast";
			eaten = true;
			newLenght += 5;
			tailChange = true;
		}
	}
	
	private void change(){
		if(tailChange){
			growArray();
			tailLenght = newLenght;
			tailChange = false;
			System.out.println("array was grown " + head + "  " + /*tail[head][0]*/ tailLenght + "  " /*tail[head][1]*/);
		}
	}
	
	private void chrased(int i){
		if(tail[head][0] == tail[i][0] && tail[head][1] == tail[i][1] && head != i && start){
			System.out.println(" " + head + " " + i + "  " + tail[head][1] + " " + tail[head][0] + "  " + tail[i][1] + " " + tail[i][0]);
			crash = true;
		}
	}

	private void move(){
		xLoc += xSpeed;
		yLoc += ySpeed;
	}
	
	private void screenCheck(){
		if(xLoc > screenWidth){
			xLoc = 40;
		}
		else if(xLoc < 40){
			xLoc = screenWidth;
		}
		else if(yLoc > screenHeight){
			yLoc = 40;
		}
		else if(yLoc < 40){
			yLoc = screenHeight;
		}
		
	}
	
	private void clear(){
		//System.out.println("cleared some" + tail[head][0] + "  " + tail[head][1]);
		g.setColor(Color.BLACK);
		g.fillRect(tail[head][0], tail[head][1], 10, 10);
		tail[head][0] = xLoc;
		tail[head][1] = yLoc;
	}
	
	private void growArray(){
		int[][] temp = new int[newLenght][2];
		for(int i = 0; i < tailLenght; i++){
			for(int a = 0; a < 2; a++){
				temp[i][a] = tail[i][a];
			}
		}
		tail = new int[newLenght][2];
		for(int i = 0; i < newLenght; i++){
			for(int a = 0; a < 2; a++){
				tail[i][a] = temp[i][a];
			}
		}				
	}

	public void init() {
		int something = r.nextInt(4);
		if(something == 0){
			xSpeed = 10;
			ySpeed = 0;
			assignThing(50 + r.nextInt(57)*10 , 50 + r.nextInt(57)*10);
		}
		else if(something == 1){
			xSpeed =-10;
			ySpeed = 0;
			assignThing(50 + r.nextInt(57)*10 , 50 + r.nextInt(57)*10);		
		}
		else if(something == 2){
			xSpeed = 0;
			ySpeed = -10;
			assignThing(50 + r.nextInt(57)*10 , 50 + r.nextInt(57)*10);			
		}
		else if(something == 3){
			xSpeed = 0;
			ySpeed = 10 ;
			assignThing(50 + r.nextInt(57)*10 , 50 + r.nextInt(57)*10);			
		}
		else{
			System.out.println("ERROR: THERE HAS BEEN A PROBLEM IN SPAWNING ONE OF THE WORMS");
		}
		loop();
	}
	
	public void assignThing(int x, int y){
		xLoc = x;
		yLoc = y;
		tail[head][0] = x;
		tail[head][1] = y;
		System.out.println("Creating new Player at X: " + x + " Y: " + y + " with a Xspeed of " + xSpeed + " and a Yspeed of " + ySpeed);
	}
	
	public wormAndTail(Graphics G, Color C, int p){
		g = G;
		c = C;
		player = p;
	}

	public void keys(String e){
		if(Integer.parseInt(e) == 87 && ySpeed == 0 && moving == false){
			moving = true;
			ySpeed = -speed; 
			xSpeed = 0;
		}
		else if(Integer.parseInt(e) == 83  && ySpeed == 0 && moving == false){
			moving = true;
			ySpeed = speed;
			xSpeed = 0;
		}
		else if(Integer.parseInt(e) == 65  && xSpeed == 0 && moving == false){
			moving = true;
			xSpeed = -speed;
			ySpeed = 0;
		}
		else if(Integer.parseInt(e) == 68  && xSpeed == 0 && moving == false){
			moving = true;
			xSpeed = speed;
			ySpeed = 0;
		}
		else{
			System.out.println("The incorrect number of " + e + "was entered") ;
		}
	}
	
	public String msg;
	
	public void sendToClient(){
		int[][] temp = new int[tail.length][2];
		for(int a = 0; a < tail.length; a++){
			temp[a][0] = tail[a][0];
			temp[a][1] = tail[a][1];
		}
		
		int[] old = new int[2];
		old[0] = tail[head][0];
		old[1] = tail[head][1];
		int[] food = new int[2];
		if(!crash){
			food[0] = xFood;
			food[1] = yFood;			
		}
		else{
			food[0] = 20000;
			food[1] = 20000;
		}
		
		
		new ClientInfo().sendtoClient(temp, old, c, food, crash, player, msg);
	}
}