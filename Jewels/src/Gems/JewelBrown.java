/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gems;

/**
 *
 * @author jumbo
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class JewelBrown extends GameObject{

    private Game game;
    private BufferedImage bg;
    
    public JewelBrown(int x, int y, String pattern,Game game){
        super(x, y, pattern, game);
        this.game = game;
        BufferedImageLoader ss = new BufferedImageLoader();
        bg = game.ss.Brown;
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(bg, VX*x, VY*y, VX, VY, null);
    }
    
}
