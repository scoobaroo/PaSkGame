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
    	botList = new ArrayList<Sprite>();
    	projectileList = new ArrayList <Sprite>();
    	player = new Sprite();
    }

    void addBot(int rad, int x, int y, int vx, int vy, Image botImg) {
    	Sprite bot = new Sprite(rad, x, y, vx, vy, botImg);
    	botList.add(bot);
    }

    void addPlayer(int rad, int x, int y, int vx, int vy, Image playerImage){
    	Sprite player = new Sprite(rad, x, y, vx, vy , playerImage);
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

    void addProjectile(){
    	Sprite projectile = new Sprite(player.radius, player.x+f(1)*(player.radius+10), player.y+f(1)*(player.radius + 10), player.velX, player.velY, projectileImage);
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
				addProjectile();
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
    		if(bot.x < player.x){
    			bot.velX = 1;
    		}
    		if(bot.y > player.x){
    			bot.velY = -1;
    		}
    		if(bot.y < player.x){
    			bot.velY = 1;
    		}
    	}
    }

    void checkCollide(){

    	for (Sprite bot: botList){
		  if ( isOverlap(bot, player) ){
	   		  bot = new Sprite(bot.radius, bot.x, bot.y, bot.velX, bot.velY , tombstoneImage);
    		  bot.velX = 0;
    		  bot.velY = 0;
       		  player = new Sprite(player.radius, player.x, player.y, player.velX, player.velY , tombstoneImage);
    		  player.velX = 0;
    		  player.velY = 0;
		  };
    	}

       for (Sprite proj: projectileList){
    	  if( isOverlap(player, proj)){
    		  player = new Sprite(player.radius, player.x, player.y, player.velX, player.velY , tombstoneImage);
    		  player.velX = 0;
    		  player.velY = 0;
    		  Sprite deadproj = new Sprite(proj.radius, proj.x, proj.y, proj.velX, proj.velY, tombstoneImage);
    		  deadproj.velX = 0;
    		  deadproj.velY = 0;
    	  };

    	  for (Sprite bot: botList){
    		  if ( isOverlap(bot, proj) ){
    	   		  bot = new Sprite(bot.radius, bot.x, bot.y, bot.velX, bot.velY , tombstoneImage);
        		  bot.velX = 0;
        		  bot.velY = 0;
        		  projectileList.remove(proj);
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
