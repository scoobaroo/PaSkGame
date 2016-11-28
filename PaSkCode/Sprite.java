package PaSkCode;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite extends Particle{

    Image image; // image to display for sprite

    Sprite() {
    }

    // Sprite(int r, int nx, int ny, int vx, int vy, Image img) {
    // instantiate new sprite
    // r: radius
    // nx, ny: position (x, y)
    // vx, vy: velocity (x, y)
    // img: image to display
    
    Sprite(int r, int nx, int ny, int vx, int vy, Image img) {
    	initSprite(r, nx, ny, vx, vy, img);
    }

    void initSprite(int r, int nx, int ny, int vx, int vy, Image img) {
    	initParticle(r,nx,ny,vx,vy);
    	image = img;
    }

    void draw(Graphics2D g) {
        int bdWidth = 400;
        int bdHeight = 300;
    	int w=bdWidth/2;
    	int h=bdHeight/2;
    	x=0;
    	y=0;
    	g.drawImage(image, x, y, w, h, null); 
    }

}
