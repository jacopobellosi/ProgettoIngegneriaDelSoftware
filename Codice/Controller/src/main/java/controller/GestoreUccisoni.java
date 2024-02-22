package controller;

import view.DatiGhost;

public class GestoreUccisoni extends Thread{
	GameEngine gp;
	int index;
	public GestoreUccisoni(GameEngine gp,int index) {
		this.gp=gp;
		this.index=index;
	}
	public void run() {
		try {
			Thread.sleep(4000);
			//System.out.println("Fantasmino "+i+" dato ordine di respawn");
			boolean flag=false;
			//gp.spawnMonster(i);
			for(int i=0;i<gp.ghost.length && flag==false;i++) {
				if(gp.ghost[i] ==null) {
					gp.ghost[i] = new Ghost(gp,index);
					gp.ghost[i].x = gp.titleSize *(9+(i+1));
					gp.ghost[i].y = gp.titleSize *4;
					gp.dg[i] = new DatiGhost(index);
					gp.dg[i].x = gp.ghost[i].x;
					gp.dg[i].y = gp.ghost[i].y;
					//System.out.println("Fantasmino "+i+" eseguito il respawn");
					flag=true;
					gp.ghost[i].invincible = true;
					 gp.dg[i].invincible = true;
					gp.GRI = new GestoreRipristinoImmunita(gp,i);
					gp.GRI.start();
					//int ultimoFantasmaEliminato = numeroFantasmiEliminati.remove(0);

				}
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
