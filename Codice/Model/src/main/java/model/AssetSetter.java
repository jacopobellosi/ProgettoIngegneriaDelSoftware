package model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;




public class AssetSetter {

	private int mapTilenum[][];
	private String s;
	public int mappaSpawnFantasmi [][] = { 
            { 9, 4, }, 
            { 10, 4,  }, 
            { 11, 4,  },
            { 12, 4,  } 
        }; 
	public AssetSetter() {
	
	}
	public int spawnPointGhost(int i,int j) {
		return mappaSpawnFantasmi[i][j];
	}
	/*
	public void setMonster() {
		gp.ghost[0] = new Ghost(gp,1);
		gp.ghost[0].x = gp.titleSize *9;
		gp.ghost[0].y = gp.titleSize *4;
		
		gp.ghost[1] = new Ghost(gp,2);
		gp.ghost[1].x = gp.titleSize *10;
		gp.ghost[1].y = gp.titleSize *4;
		
		gp.ghost[2] = new Ghost(gp,3);
		gp.ghost[2].x = gp.titleSize *11;
		gp.ghost[2].y = gp.titleSize *4;
		
		gp.ghost[3] = new Ghost(gp,4);
		gp.ghost[3].x = gp.titleSize *12;
		gp.ghost[3].y = gp.titleSize *4;
		
		
		
	}
	
	 public void setObject() {
	        try {
	            InputStream is = getClass().getResourceAsStream(s);
	            BufferedReader br = new BufferedReader(new InputStreamReader(is));
	            int col =0;
	            int row=0;
	            int contatore = 0;
	            while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
	                
	                String line = br.readLine();
	                while(col < gp.maxScreenCol) {
	                    
	                    String numbers[] = line.split(" ");
	                    int num = Integer.parseInt(numbers[col]);
	                    mapTilenum[col][row] = num;
	                    if(mapTilenum[col][row] == 0) {
	                        gp.obj[contatore]=new ObjCFU(); //il numero � un indice per l'elemento
	                        gp.obj[contatore].x=col * gp.titleSize; //riga della mappa
	                        gp.obj[contatore].y=row * gp.titleSize; //colonna della mappa
	                        contatore++;
	                       
	                    }
	                   
	                    if(mapTilenum[col][row]==15) {
	                    	gp.pw[contatore]=new PowerUp(); //il numero � un indice per l'elemento
	                        gp.pw[contatore].x=col * gp.titleSize; //riga della mappa
	                        gp.pw[contatore].y=row * gp.titleSize; //colonna della mappa
	                        contatore++;
	                    }
	                    col++;
	                    	
	                }
	                if(col == gp.maxScreenCol) {
	                    col =0;
	                    row++;
	                }
	                
	            }
	            br.close();
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        
	    }
	    */
}
	