/*
 * This code has been generated by the Rebel: a code generator for modern Java.
*
* Drop us a line or two at feedback@archetypesoftware.com: we would love to hear from you!
*/
package view;

//Rappresenta un singolo livello del gioco, definendo la sua struttura, i personaggi, gli oggetti, ecc.
import java.awt.*;
import javax.swing.JPanel;

import controller.InputManager;
import controller.Player;
import model.CollisionChecker;
import model.Entity;
import model.EventHandler;
import model.GestoreRipristinoImmunita;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class GameEngine extends JPanel implements Runnable{
 
	/**
	 * Classe principale del gioco
	 * @author Bellosi Jacopo, Lara longhi, Poloni Luca
	 */
	private static final long serialVersionUID = 1L;
	private final int originalTitleSize = 16; // 16x16 title
	private final int scale=3;
	public final int titleSize = originalTitleSize * scale;
	
	
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 13;
	public final int screenWidth = titleSize * maxScreenCol;
	public final int screenHeight = titleSize * maxScreenRow;
	private int FPS=60;
	private InputManager keyH = new InputManager(this);
	public UI ui = new UI(this);
	public Tilemanger tileM = new Tilemanger(this,"/pacman/mappa/mappa01.txt");
	public EventHandler eHandler=new EventHandler(this);
	public CollisionChecker cCheck = new CollisionChecker(this);
	public AssetSetter aSetter=new AssetSetter(this,"/pacman/mappa/mappa01.txt");
	public Player player =new Player(this,keyH);
	public GameObject obj[]=new GameObject[10000];//numero massimo oggetti
	public GameObject pw[]=new GameObject[1000];
	public Entity[] ghost = new Entity[4];
	//public Level livello = new Level();
	Sound sound=new Sound(); 
	public Sound se=new Sound(); 

	private Thread gameThread;

	//game state
	public int livelloCorrente=1;
	public int livelloMax=3;
	public int gameState;
	public final int titleState=0;
	public final int playState=1;
	public final int pauseState=2;
	public final int endState=3;
	public int nextLevelState=4;
	
	public GestoreRipristinoImmunita GRI;
	 
	 
	public GameEngine() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		playSE(5);
		
		
		
	}


	public void setupGame() {
		aSetter.setMonster();
		//drawGhost.
		aSetter.setObject();
		player.setDefaultLife();
		gameState=titleState;
	}
	
	public void StartGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		double drawInterval = 1000000000/FPS;
		double nextDrawTine = System.nanoTime();
		while(gameThread != null ) {
			//System.out.print("ciao");
			//long currentTime = System.nanoTime();
			//UPDATE
			update();
			//DRAW
			repaint();
			try {
				double remainingTime = nextDrawTine - System.nanoTime();
				remainingTime = remainingTime/1000000;
				if(remainingTime<0) {
					remainingTime =0;
				}
				Thread.sleep((long) remainingTime);
				nextDrawTine += drawInterval;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void update() {
		if(gameState==playState) {
			
			player.update();
			for(int i=0;i<ghost.length;i++) {
				if(ghost[i]!=null) {
					ghost[i].update();
				}
			}
			
		}else if(gameState==pauseState) {
	    	

		}
		if(gameState==endState) {
			if(player.life==0) {
				se.setFile(4); 
				se.playWin();
			}else {
				se.setFile(1); 
				se.playWin();
			}
			
		}
		
	}
	private void printMappa(Graphics g2) {
		int col = 0;
		int row = 0;
		int x =0;
		int y=0;

		
		while(col < maxScreenCol && row < maxScreenRow) {
			int tileNum = tileM.mapTilenum[col][row];
			g2.drawImage(tileM.tile[tileNum].image, x, y, titleSize, titleSize, null);
			
			
			
			col++;
			x +=titleSize;
			
			if(col==maxScreenCol){
				col =0;
				x=0;
				row++;
				y +=titleSize;
				
			}
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		if(gameState==titleState) {
			ui.draw(g2);
		}else if(gameState==playState){
			//tileM.draw(g2);
			printMappa(g2);
			for(int i=0;i< obj.length;i++)		{
				if(obj[i]!=null) {
					obj[i].drawCFU(g2,this);
				}
			}
			
			for(int i=0;i< pw.length;i++)		{
				if(pw[i]!=null) {
					pw[i].drawPW(g2,this);
				}
			}
			player.draw(g2);
			if(player.attacking==true) {
				for(int i=0;i<ghost.length;i++) {
					if(ghost[i]!=null && ghost[i].invincible==false) {
						g2.drawImage(ghost[i].imageFantasma_vunerabile, ghost[i].x, ghost[i].y, titleSize, titleSize, null);
						ghost[i].setAction();
					}
				}
				for(int i=0;i<ghost.length;i++) {
					if(ghost[i]!=null && ghost[i].invincible==true) {
						ghost[i].setAction();
						g2.drawImage(ghost[i].imageGhost, ghost[i].x, ghost[i].y, titleSize, titleSize, null);
					}
				}
			}else {
				for(int i=0;i<ghost.length;i++) {
					if(ghost[i]!=null) {
						ghost[i].setAction();
						g2.drawImage(ghost[i].imageGhost, ghost[i].x, ghost[i].y, titleSize, titleSize, null);
					}
				}
			}
				
			
				
				
			
			//UI
			ui.drawContaPallini(g2);
			g2.dispose();
		}else if(gameState==endState) {
			ui.draw(g2);
		}else if(gameState==pauseState) {
			ui.draw(g2);
			
		}else if(gameState==nextLevelState) {
			ui.draw(g2);
		}
		}
	
	
	
	public void stopMusic(int i) { // NO_UCD (unused code)
		sound.setFile(i);
		sound.stop();		
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
	
	
	public void killMonster(int i) {
		ghost[i]=null;
		System.out.println("HAI MANGIATO UN FANSTASMA");
		 
	}


	public void restart() {
		livelloCorrente=1;
		tileM = new Tilemanger(this,"/pacman/mappa/mappa0"+livelloCorrente+".txt");
		aSetter=new AssetSetter(this,"/pacman/mappa/mappa0"+livelloCorrente+".txt");
		player.setDefaultValue();
		player.setDefaultLife();
		aSetter.setMonster();
		aSetter.setObject();
		
	}
	public void nextLevel() {
		livelloCorrente++;
		Tilemanger.resetPalliniTotali();
		tileM = new Tilemanger(this,"/pacman/mappa/mappa0"+livelloCorrente+".txt");
		aSetter=new AssetSetter(this,"/pacman/mappa/mappa0"+livelloCorrente+".txt");
		player.setDefaultValue();
		aSetter.setMonster();
		aSetter.setObject();
		player.pallini_totali = Tilemanger.getPalliniTotali();
		
	}


}