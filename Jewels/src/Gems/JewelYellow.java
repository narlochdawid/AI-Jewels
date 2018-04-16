
package Gems;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jewels.Game;
import jewels.GameObject;
import jewels.BufferedImageLoader;

/**
 *
 * @author jumbo
 */
public class JewelYellow extends GameObject{

    private Game game;
    private BufferedImage bg;
    
    public JewelYellow(int x, int y, String pattern,Game game){
        super(x, y, pattern, game);
        this.game = game;
        BufferedImageLoader ss = new BufferedImageLoader();
        bg = game.ss.Yellow;
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(bg, VX*x, VY*y, VX, VY, null);
    }
    
}
