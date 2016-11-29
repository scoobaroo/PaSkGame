package PaSkCode;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.event.*;

public class GWModel extends PSysModel {

	ArrayList <Sprite> botList;
	ArrayList <Sprite> projectileList;
	protected Sprite player;
	Image tombstoneImage;
	Image projectileImage;

    GWModel() {
			player = new Sprite();
    	botList = new ArrayList<Sprite>();
    	projectileList = new ArrayList<Sprite>();
    }

    void addBot(int rad, int x, int y, int vx, int vy, Image botImg) {
    	Sprite bot = new Sprite(rad, x, y, vx, vy, botImg);
    	botList.add(bot);
			pList.add(bot);
    }

    void addPlayer(int rad, int x, int y, int vx, int vy, Image playerImage){
    	player = new Sprite(rad, x, y, vx, vy, playerImage);
			pList.add(player);
    }

    void addGraveStone(Image gImg){
    	tombstoneImage = gImg;
    }

    void addProjectileImg(Image pImg){
    	projectileImage = pImg;
    }

    int f(Sprite p1){
			if (p1.velX > 0 || p1.velY>0){
				return 1;
			}
			else if (p1.velX < 0 || p1.velY < 0){
				return -1;
			}
			else{
				return 0;
			}
    }

    void addProjectile(Sprite p1){
    	Sprite projectile = new Sprite(10, p1.x+f(p1)*(p1.radius+10), p1.y+f(p1)*(p1.radius + 10), (p1.velX)*2, (p1.velY)*2, projectileImage);
    	projectileList.add(projectile);
			pList.add(projectile);
			System.out.println("Projectile Fired");
			System.out.println("x: "+projectile.x +"y: "+ projectile.y);
			System.out.println("x: "+ player.x + "y: "+player.y);
    }

    void keyPressed(KeyEvent e){
    	int keyCode = e.getKeyCode();
    	if(keyCode == KeyEvent.VK_UP){
				System.out.println("Up is pressed");
    		player.velY = -5;
				System.out.println(player.velY);
    	}
			if(keyCode == KeyEvent.VK_DOWN){
				System.out.println("Down is pressed");
    		player.velY = 5;
				System.out.println(player.velY);
    	}
			if(keyCode == KeyEvent.VK_LEFT){
				System.out.println("Left is pressed");
    		player.velX = -5;
				System.out.println(player.velX);
    	}
    	if(keyCode == KeyEvent.VK_RIGHT){
				System.out.println("Right is pressed");
    		player.velX = 5;
				System.out.println(player.velX);
    	}
			if(keyCode == KeyEvent.VK_SPACE){
				addProjectile(player);
			}
    }

    void keyReleased(KeyEvent e){
    	player.velX = 0;
    	player.velY = 0;
    }

    void orientBots(){
    	for (int k = 0; k < botList.size(); k++){
				Sprite bot = botList.get(k);
    		if(bot.x > player.x){
    			bot.velX = -1;
    		}
    		else if(bot.x < player.x){
    			bot.velX = 1;
    		}
    		if(bot.y > player.x){
    			bot.velY = -1;
    		}
    		else if(bot.y < player.x){
    			bot.velY = 1;
    		}
				addProjectile(bot);
    	}
    }

    void checkCollide(){
			System.out.println("player radius = " + player.radius);
			int projectileListSize = projectileList.size();
			int botListSize = botList.size();
    	for (int k = 0; k < botListSize; k++){
			  if ( isOverlap(botList.get(k), player) ){
						System.out.println("Collision between player and bot detected!!!");
						Sprite bot = botList.get(k);
		   		  bot = new Sprite(bot.radius, bot.x, bot.y, 0, 0, tombstoneImage);
	       		player = new Sprite(player.radius, player.x, player.y, 0, 0, tombstoneImage);
						botListSize -= 1;
			  };
				for (int m = 0; m < botListSize; m++) {
					if ( isOverlap(botList.get(k), botList.get(m)) ){
						System.out.println("bot collision detected!!!!");
						Sprite bot1 = botList.get(k);
						Sprite bot2 = botList.get(m);
						Sprite tombstone1 =new Sprite(bot1.radius, bot1.x, bot1.y, 0, 0, tombstoneImage);
						Sprite tombstone2 =new Sprite(bot2.radius, bot2.x, bot2.y, 0, 0, tombstoneImage);
						bot1 = tombstone1;
						bot2 = tombstone2;
						botListSize -= 2;
					}
				}
    	}
			if (projectileList.size() > 0){
	       for (int i = 0; i < projectileListSize; i++){
	    	  if( isOverlap(player, projectileList.get(i))){
						System.out.println("Collision between player and projectile detected!!!");
						Sprite tombstone = new Sprite(player.radius, player.x, player.y, 0, 0, tombstoneImage);
						player = tombstone;
						Sprite proj = projectileList.get(i);
						projectileList.remove(proj);
						pList.remove(proj);
						projectileListSize -= 1;
	    	  };
	    	  for (int j = 0; j < botListSize ; j++){
	    		  if ( isOverlap(botList.get(j), projectileList.get(i)) ){
								System.out.println("Collision between bot and projectile detected!!!");
							  Sprite bot = botList.get(j);
	    	   		  bot = new Sprite(bot.radius, bot.x, bot.y, 0, 0, tombstoneImage);
								Sprite proj = projectileList.get(i);
								pList.remove(proj);
	        		  projectileList.remove(proj);
								projectileListSize -= 1;
								botListSize -= 1;
	    		  };
	    	  }
	       }
			 }
    }

    boolean isOverlap(Particle p1, Particle p2) {
        int diffX = Math.abs(p1.x - p2.x);
        int diffY = Math.abs(p1.y - p2.y);
        if (diffX < p1.radius + p2.radius
            && diffY < p1.radius + p2.radius)
            return true;
        else
            return false;
    }
	}
