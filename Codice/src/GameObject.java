

import java.util.*;
import java.time.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject {
   public int x,y;
   public int speed;
   public Image up1,up2, down1,down2,left,right;
   public String direction;
   
   public Rectangle solidArea;
   public boolean collisionON = false;

   	public int solidAreaDefaultx, solidAreaDefaulty;

    public BufferedImage imagecfu;
	public String name;
	public boolean collision=false;
	public int mapTilenum[][];
	
	
   	public Rectangle solidArea2 = new Rectangle(0,0,48,48);
   	public int solidAreaDefaultx2, solidAreaDefaulty2;
   	
   	
   	
	public void drawCFU(Graphics2D g2,GameEngine gp) {

		int x = this.x ;
		int y= this.y ;
		

			g2.drawImage(imagecfu, x, y, gp.titleSize, gp.titleSize, null);
			
	
			
		
		
	}
}