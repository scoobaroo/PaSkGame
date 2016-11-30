package PaSkCode;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.event.*;

public class GWModel extends PSysModel {

	ArrayList <Sprite> botList;
	ArrayList <Sprite> projectileList;
	protected Sprite player;
	Image tombstoneImage = null;
	Image projectileImage = null;

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
			if(gImg !=null){
				Image sImg = gImg.getScaledInstance(player.radius*2, player.radius*2, Image.SCALE_DEFAULT);
				tombstoneImage = sImg;
			}
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
			if(player.alive != false){
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
    }

    void keyReleased(KeyEvent e){
    	player.velX = 0;
    	player.velY = 0;
    }

    void orientBots(){
    	for (int k = 0; k < botList.size(); k++){
				Sprite bot = botList.get(k);
				if(bot.alive==true){
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
    }

		void kill(Sprite s){
			s.image = tombstoneImage;
			s.alive = false;
			s.velX = 0;
			s.velY = 0;
		}

    void checkCollide(){
    	for (int k = 0; k < botList.size(); k++){
				Sprite bot = botList.get(k);
			  if ( isOverlap(bot, player) && player.alive == true){
						System.out.println("Collision between player and bot detected!!!");
						kill(player);
						kill(bot);
			  };
				for (int m = 1; m < botList.size(); m++) {
					Sprite bot2 = botList.get(m);
					if ( isOverlap(bot, bot2) && bot.alive == true && bot2.alive==true && bot != bot2){
						System.out.println("bot collision detected!!!!");
						kill(bot);
						kill(bot2);
					}
				}
    	}
			if (projectileList.size() > 0){
	       for (int i = 0; i < projectileList.size() ; i++){
					Sprite proj = projectileList.get(i);
	    	  if( isOverlap(player, proj) && player.alive==true){
						System.out.println("Collision between player and projectile detected!!!");
						kill(player);
						projectileList.remove(proj);
						pList.remove(proj);
	    	  };
	    	  for (int j = 0; j < botList.size() ; j++){
						Sprite bot = botList.get(j);
	    		  if ( isOverlap(bot, proj) && bot.alive== true ){
								System.out.println("Collision between bot and projectile detected!!!");
								kill(bot);
								pList.remove(proj);
	        		  projectileList.remove(proj);
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
