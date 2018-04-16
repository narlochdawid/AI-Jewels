
/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jewels;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jumbo
 */
public class PTR extends GameObject{

    private Game game;
    public Rectangle select;
    private Color c;
    
    public PTR(int x, int y, String pattern,Game game,Color c){
        super(x, y, pattern, game);
        this.game = game;
        this.c = c;
        
        
    select = new Rectangle(x*VX-VX, y*VY-VY, VX, VY);
        
    }

    @Override
    public void render(Graphics g) {
            
        Graphics2D g2d = (Graphics2D) g;
        select.setLocation(x*VX-VX, y*VY-VY);
        g2d.setPaint(c);
        g2d.fill(select);
        g2d.draw(select);
    }
    
}
