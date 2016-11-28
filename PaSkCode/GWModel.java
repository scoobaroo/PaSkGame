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
    }

    void addPlayer(int rad, int x, int y, int vx, int vy, Image playerImage){
    	player = new Sprite(rad, x, y, vx, vy, playerImage);
    }

    void addGraveStone(Image gImg){
    	tombstoneImage = gImg;
    }

    void addProjectileImg(Image pImg){
    	projectileImage = pImg;
    }

    int f(int w){
    	if(w<0){
    		return -1;
    	}
    	else if(w>0){
    		return 1;
    	}
    	else
    		return 0;
    }

    void addProjectile(Sprite p1){
    	Sprite projectile = new Sprite(p1.radius, p1.x+f(1)*(p1.radius+10), p1.y+f(1)*(p1.radius + 10), p1.velX, p1.velY, projectileImage);
    	projectileList.add(projectile);
    }

    void keyPressed(KeyEvent e){
    	int keyCode = e.getKeyCode();
    	if(keyCode == KeyEvent.VK_UP){
    		player.velY += 5;
    	}
    	else if(keyCode == KeyEvent.VK_DOWN){
    		player.velY -= 5;
    	}
    	else if(keyCode == KeyEvent.VK_LEFT){
    		player.velX -= 5;
    	}
    	else if(keyCode == KeyEvent.VK_RIGHT){
    		player.velX += 5;
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
    	for (Sprite bot: botList){
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

    	for (int k = 0; k < botList.size(); k++){
		  if ( isOverlap(botList.get(k), player) ){
					Sprite bot = botList.get(k);
	   		  bot = new Sprite(bot.radius, bot.x, bot.y, bot.velX, bot.velY , tombstoneImage);
    		  bot.velX = 0;
    		  bot.velY = 0;
       		player = new Sprite(player.radius, player.x, player.y, player.velX, player.velY , tombstoneImage);
    		  player.velX = 0;
    		  player.velY = 0;
		  };
    	}

       for (int i = 0; i < projectileList.size(); i++){
    	  if( isOverlap(player, projectileList.get(i))){
    		  player = new Sprite(player.radius, player.x, player.y, player.velX, player.velY , tombstoneImage);
    		  player.velX = 0;
    		  player.velY = 0;
					Sprite proj = projectileList.get(i);
    		  Sprite deadproj = new Sprite(proj.radius, proj.x, proj.y, proj.velX, proj.velY, tombstoneImage);
    		  deadproj.velX = 0;
    		  deadproj.velY = 0;
    	  };

    	  for (int j = 0; j < botList.size(); j++){
    		  if ( isOverlap(botList.get(j), projectileList.get(i)) ){
						  Sprite bot = botList.get(j);
    	   		  bot = new Sprite(bot.radius, bot.x, bot.y, bot.velX, bot.velY , tombstoneImage);
        		  bot.velX = 0;
        		  bot.velY = 0;
        		  projectileList.remove(projectileList.get(i));
    		  };
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
