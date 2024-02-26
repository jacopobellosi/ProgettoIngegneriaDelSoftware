/*
 * This code has been generated by the Rebel: a code generator for modern Java.
*
* Drop us a line or two at feedback@archetypesoftware.com: we would love to hear from you!
*/
package controller;

import java.awt.*;
import java.io.*;
import javax.swing.JPanel;
import model.*;
import view.*;

/**
 * Classe principale del gioco
 * @author Bellosi Jacopo, Lara longhi, Poloni Luca
 */


public class GameEngine extends JPanel implements Runnable{


	private static final long serialVersionUID = 1L;
	public static final Object[] PLAY_STATE = null;
	private final int originalTitleSize = 16; // 16x16 title
	private final int scale=3;
	public final int titleSize = originalTitleSize * scale;


	public final int maxScreenCol = 16;
	public final int maxScreenRow = 13;
	private final int screenWidth = titleSize * maxScreenCol;
	private final int screenHeight = titleSize * maxScreenRow;
	private int FPS=60;
	private InputManager keyH = new InputManager(this);

	Tilemanger tileM = new Tilemanger("/pacman/mappa/mappa01.txt",maxScreenCol, maxScreenRow );
	EventHandler eHandler=new EventHandler(this);
	CollisionChecker cCheck = new CollisionChecker(this);
	private AssetSetter aSetter=new AssetSetter();
	Player player =new Player(this,keyH);
	public GameObject obj[]=new GameObject[10000];//numero massimo oggetti
	GameObject pw[]=new GameObject[1000];
	public Entity[] ghost = new Entity[4];
	DatiGhost[] dg = new DatiGhost[4];
	private GamePanel gp = new GamePanel();
	UI ui = new UI(gp);
	Sound sound=new Sound();  // NO_UCD (unused code)
	Sound se=new Sound();
	int mapTilenum[][] = new int[maxScreenCol][maxScreenRow];
	private Thread gameThread;

	//game state
	public int livelloCorrente=1;
	int livelloMax=3;
	public int gameState;
	final int titleState=0;
	public final static int playState=1;
	public final static int pauseState=2;
	final int endState=3;
	int nextLevelState=4;

	GestoreRipristinoImmunita GRI;

	private int conteggio=0;

	public GameEngine() {
		loadMap("/pacman/mappa/mappa01.txt");
		System.out.println("ho caricato la mappa");
		player.pallini_totali=getPalliniTotali();
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		playSE(5);
	}


	public void setupGame() {
		setMonster();
		setObject();
		player.setDefaultLife();
		gameState=titleState;
	}

	void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start();
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		double drawInterval = 1000000000/FPS;
		double nextDrawTine = System.nanoTime();
		while(gameThread != null ) {
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
					for(int j=0;j<dg.length;j++) {
						if(dg[j]!=null) {
							if(ghost[i] != null && ghost[i].type == dg[j].i) {
								dg[j].x = ghost[i].x;
								dg[j].y = ghost[i].y;
								//System.out.println("il fantasma "+dg[i].i+" si è mosso x="+dg[j].x+ " y="+dg[j].y);
							}
						}
					}
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

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		if(gameState==titleState) {
			ui.draw(g2,screenWidth,screenHeight,gameState,player.punteggio,titleSize,player.countLife(),livelloCorrente);
		}else if(gameState==playState){
			gp.paintComponent(g2,obj,titleSize,pw,player.attacking,tileM,mapTilenum,dg);
			player.draw(g2);
			for(int i=0;i<ghost.length;i++) {
				if(ghost[i]!=null) {
					ghost[i].setAction();
				}

			}

			//UI
			ui.drawContaPallini(g2,player.punteggio,titleSize,player.countLife());
			g2.dispose();
		}else if(gameState==endState) {
			ui.draw(g2,screenWidth,screenHeight,gameState,player.punteggio,titleSize,player.countLife(),livelloCorrente);
		}else if(gameState==pauseState) {
			ui.draw(g2,screenWidth,screenHeight,gameState,player.punteggio,titleSize,player.countLife(),livelloCorrente);
		}else if(gameState==nextLevelState) {
			ui.draw(g2,screenWidth,screenHeight,gameState,player.punteggio,titleSize,player.countLife(),livelloCorrente);
		}
	}

	public void loadMap(String S) {
		try {
			InputStream is = getClass().getResourceAsStream(S);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col =0;
			int row=0;
			while(col < maxScreenCol && row < maxScreenRow) {
					String line = br.readLine();

					while(col < maxScreenCol) {
						String numbers[] = line.split(" ");
						int num = Integer.parseInt(numbers[col]);
						mapTilenum[col][row] = num;
						if(mapTilenum[col][row]==0) {
							conteggio++;
						}
						if(mapTilenum[col][row]==15) {
							conteggio++;
						}
						col++;
					}
					if(col == maxScreenCol) {
						col =0;
						row++;
					}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getMap(int row, int col) {
		// TODO Auto-generated method stub
		return mapTilenum[col][row];
	}

	void setMonster() {
		ghost[0] = new Ghost(this,1);
		ghost[0].x = titleSize * aSetter.mappaSpawnFantasmi[0][0];
		ghost[0].y = titleSize * aSetter.mappaSpawnFantasmi[0][1];

		ghost[1] = new Ghost(this,2);
		ghost[1].x = titleSize * aSetter.mappaSpawnFantasmi[1][0];
		ghost[1].y = titleSize * aSetter.mappaSpawnFantasmi[0][1];

		ghost[2] = new Ghost(this,3);
		ghost[2].x = titleSize * aSetter.mappaSpawnFantasmi[2][0];
		ghost[2].y = titleSize * aSetter.mappaSpawnFantasmi[0][1];

		ghost[3] = new Ghost(this,4);
		ghost[3].x = titleSize * aSetter.mappaSpawnFantasmi[3][0];
		ghost[3].y = titleSize * aSetter.mappaSpawnFantasmi[0][1];

		//Clono i dati
		dg[0] = new DatiGhost(1);
		dg[0].x = titleSize * aSetter.mappaSpawnFantasmi[0][0];
		dg[0].y = titleSize * aSetter.mappaSpawnFantasmi[0][1];

		dg[1] = new DatiGhost(2);
		dg[1].x = titleSize * aSetter.mappaSpawnFantasmi[1][0];
		dg[1].y = titleSize * aSetter.mappaSpawnFantasmi[0][1];

		dg[2] = new DatiGhost(3);
		dg[2].x = titleSize * aSetter.mappaSpawnFantasmi[2][0];
		dg[2].y = titleSize * aSetter.mappaSpawnFantasmi[0][1];

		dg[3] = new DatiGhost(4);
		dg[3].x = titleSize * aSetter.mappaSpawnFantasmi[3][0];
		dg[3].y = titleSize * aSetter.mappaSpawnFantasmi[0][1];

	}

	 private void setObject() {
		 	int contatore = 0;
	           for(int i=0;i<maxScreenCol;i++) {
	        	   for(int j=0;j<maxScreenRow;j++) {
	                    if(mapTilenum[i][j] == 0) {
	                        obj[contatore]=new ObjCFU(); // un indice per l'elemento
	                        obj[contatore].x=i * titleSize; //riga della mappa
	                        obj[contatore].y=j * titleSize; //colonna della mappa
	                        contatore++;
	                     }
	                    if(mapTilenum[i][j]==15) {
	                    	pw[contatore]=new PowerUp(); //il numero � un indice per l'elemento
	                        pw[contatore].x=i * titleSize; //riga della mappa
	                        pw[contatore].y=j * titleSize; //colonna della mappa
	                        contatore++;
	                    }
		           }
	    }
	}

	void playSE(int i) {
		se.setFile(i);
		se.play();
	}


	public void killMonster(int i) {
		System.out.println("HAI MANGIATO IL FANTASMA"+ghost[i].type);
		int type= ghost[i].type;
		for(int j=0;j<dg.length;j++) {
			if(dg[j]!=null) {
				if(dg[j].i==type) {
					ghost[i]=null;
					dg[j]=null;
				}
			}
		}

	}

	public int getPalliniTotali() {
		return conteggio;
	}

	private void resetPalliniTotali() {
		conteggio=0;
		System.out.println("reset conteggio "+conteggio);
	}

	void restart() {
		livelloCorrente=1;
		tileM = new Tilemanger("/pacman/mappa/mappa0"+livelloCorrente+".txt",maxScreenCol, maxScreenRow );
		aSetter=new AssetSetter();
		loadMap("/pacman/mappa/mappa0"+livelloCorrente+".txt");
		player.setDefaultValue();
		player.setDefaultLife();
		setMonster();
		setObject();

	}
	public void nextLevel() {
		livelloCorrente++;
		resetPalliniTotali();
		tileM = new Tilemanger("/pacman/mappa/mappa0"+livelloCorrente+".txt",maxScreenCol, maxScreenRow);
		aSetter=new AssetSetter();
		loadMap("/pacman/mappa/mappa0"+livelloCorrente+".txt");
		player.pallini_totali = getPalliniTotali();
		player.setDefaultValue();
		setMonster();
		setObject();

	}


	public Object[] getGameState() {
		// TODO Auto-generated method stub
		return null;
	}


	public Object getConteggio() {
		// TODO Auto-generated method stub
		return null;
	}


}
