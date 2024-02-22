package view;



import java.awt.*;
import java.awt.image.BufferedImage;


public class GameObject {
   
	public int x,y;
    public BufferedImage imagecfu,imageVita, powerUp; // NO_UCD (use default)
	public String name;
	public boolean collision=false;
	
	
   	public Rectangle solidArea = new Rectangle(0,0,25,35);
   	public int solidAreaDefaultx, solidAreaDefaulty;
   	public GameObject(String name){
   		this.name=name;
   	}
   	
	public void drawCFU(Graphics2D g2,int titleSize) {

		int x = this.x ;
		int y= this.y ;
		

		g2.drawImage(imagecfu, x, y,titleSize,titleSize, null);
	}



	public void drawPW(Graphics2D g2, int titleSize) {
		// TODO Auto-generated method stub
		int x = this.x ;
		int y= this.y ;
		

			g2.drawImage(powerUp, x, y, titleSize, titleSize, null);
	}
}