import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import javax.swing.JFrame;


public class Start {
	static PlayerConnection[] b = new PlayerConnection[1];
	static display frame;
	
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new display();
					frame.setVisible(true);
					frame.setTitle("Worms server");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		try {
			Thread.sleep(300);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 new ClientInfo().setGrapics(frame.contentPane.getGraphics());
		 int portNumber = 3663;
		 int four = 0;
		 startDelay sd = new startDelay(frame.contentPane.getGraphics(), frame);
		 (new Thread(sd)).start();
     	System.out.println("Server waiting for player...");
	        try (
	            ServerSocket serverSocket = new ServerSocket(portNumber);
	        ) {
	        	while(!sd.done){
		            Socket clientSocket = serverSocket.accept();  
		            if(!sd.done){
			            sd.time = 4;
			            PlayerConnection pc = new PlayerConnection(clientSocket, frame, four+1);
			            pc.start();
			            sd.incrementArray(pc);
			            four++;
			            System.out.println("There is " + four + " Players Currently");
			            new ClientInfo().incrementDP(four);
		            }
	        	}
	            System.out.println("Players can no longer join...");
	        } catch (IOException e) {
	            System.out.println("Exception caught when trying to listen on port "
	                + portNumber + " or listening for a connection");
	            System.out.println(e.getMessage());
	        }
	    }
	
		public static void prints(String msg){
			System.out.println(msg);
		}
	}

class PlayerConnection extends Thread{
    private Socket client;
    private String line="";
    private BufferedReader input = null;
    //private PrintWriter output = null;
    private ObjectOutputStream oos;
    private display frame;
    private int player;
    
	public PlayerConnection(Socket serv, display frames, int mPlayer){
		frame = frames;
		player = mPlayer;
		try {
            this.client =serv;
            System.out.println("New Client Connected to port:"+ client.getPort());
            } catch (Exception e) {
                    System.out.println(e.getMessage());
            }    
		System.out.println("why");
	}

	public wormAndTail wat;
	private Random r;
	@Override
	public void run() {
        try {
        	oos = new ObjectOutputStream(client.getOutputStream());
			//output=new PrintWriter(client.getOutputStream(), true);
			input=new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		new ClientInfo().setoos(oos);
		r = new Random();
		String inputs;
		//output.println("Thankyou for your connection your worm is being created now...");
		try {
			Color c = Color.getHSBColor((float)(r.nextFloat()+.5), (float)(r.nextFloat() +.5), (float)(r.nextFloat() + .5));
			wat = new wormAndTail(frame.contentPane.getGraphics(), c, player);
			new ClientInfo().updateThings("Has joined the game", c);
			new ClientInfo().ServerSend("Has joined the game.", c);
			wat.init();
			while((inputs = input.readLine()) != null){
				wat.keys(inputs);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			new ClientInfo().updateThings("Has disconected", wat.c);
			
			wat.crash = true;
			System.out.println("One of the players has left the game");
		}
		// TODO Auto-generated method stub
		
	}
}

class startDelay implements Runnable{
	private Graphics g;
	
	public int time = 15;
	public PlayerConnection[] a = new PlayerConnection[0];
	public boolean done = false;
	public display test;
	public startDelay(Graphics G, display b){
		a = new PlayerConnection[1];
		test = b;
		g=G;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(time > 0){
			g.setColor(Color.WHITE);
			new ClientInfo().updateThings("Game starts in " + time + " seconds.", null);
			new ClientInfo().ServerSend("Game starts in " + time + " seconds.", null);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			time--;
		}
		if(a[0] != null){
			new ClientInfo().updateThings(">>>> LET THE GAMES BEGIN <<<<", null);
			new ClientInfo().ServerSend(">>>LET THE GAMES BEGIN<<<", null);
			System.out.println("System starting...");
			done = true;
			test.init(a);
		}
		else{
			System.out.println("ERROR: there is a error in the PlayerConnection array");
			new ClientInfo().updateThings("NO ONE JOINED", null);
			time = 15;
			run();
		}
	}
	
	public void incrementArray(PlayerConnection c){
		if(a[0] != null){
			PlayerConnection [] b = new PlayerConnection[a.length];
			for(int i = 0; i < a.length; i++){
				b[i] = a[i];
			}
			a = new PlayerConnection[b.length+1];
			for(int i = 0; i < b.length; i++){
				a[i+1] = b[i];
			}
		}
		a[0] = c;
	}
}
