package PaSkCode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GWView extends PSysView {
    GWView() {
    }

    void draw(GWModel gwm, Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		gwm.player.draw(g2d);
		for (int i=0; i<gwm.botList.size(); i++) {
		    Sprite s = gwm.botList.get(i);
		    s.draw(g2d);
		}
		for (int i=0; i<gwm.projectileList.size(); i++) {
		    Sprite s = gwm.projectileList.get(i);
		    s.draw(g2d);
		}
    }

}
