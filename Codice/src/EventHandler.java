import java.awt.Rectangle;

public class EventHandler {

	GameEngine gp;
	Rectangle eventRect;
	int eventRectDefaultX,eventRectDefaultY;
	
	public EventHandler(GameEngine gp) {
		this.gp=gp;
		eventRect.x=23;
		eventRect.y=23;
		eventRect.width=2;
		eventRect.height=2;
		eventRectDefaultX=eventRect.x;
		eventRectDefaultY=eventRect.y;	

	}
	
	public void checkEvent() {
		if(hit(1,7,"left")==true) {
			tunnelsx();
		}
		if(hit(16,7,"right")==true) {
			tunneldx();
		}
	}
	private boolean hit(int eventCol, int eventRow, String reqDirection) {

		boolean hit=false;
		gp.player.solidArea.x=gp.player.x+gp.player.solidArea.x;
		gp.player.solidArea.y=gp.player.y+gp.player.solidArea.y;
		eventRect.x=eventCol*gp.titleSize+eventRect.x;
		eventRect.y=eventRow*gp.titleSize+eventRect.y;

		if(gp.player.solidArea.intersects(eventRect)) {
			if(gp.player.direction.equals(reqDirection)||reqDirection.contentEquals("any")) {
				hit=true;
			}
		}
		
		gp.player.solidArea.x=gp.player.solidAreaDefaultx;
		gp.player.solidArea.y=gp.player.solidAreaDefaulty;
		eventRect.x=eventRectDefaultX;
		eventRect.y=eventRectDefaultY;

		return hit;
	}

	public void tunnelsx() {
		gp.player.x=gp.titleSize*17;
		gp.player.y=gp.titleSize*16;

	}
	public void tunneldx() {
		gp.player.x=gp.titleSize*17;
		gp.player.y=gp.titleSize*1;

	}
}