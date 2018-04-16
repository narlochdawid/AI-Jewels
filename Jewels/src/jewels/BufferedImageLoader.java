/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jewels;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author jumbo
 */
public class BufferedImageLoader {
    public BufferedImage Blue;
    public BufferedImage Red;
    public BufferedImage Brown;
    public BufferedImage Black;
    public BufferedImage Green;
    public BufferedImage Yellow;
    public BufferedImage Violet;
    public BufferedImage Orange;
    public BufferedImage White;
    
    public void LoadImgs() throws IOException{
        Blue = ImageIO.read(new File("src/JewelBlue.png"));
        Red = ImageIO.read(new File("src/JewelRed.png"));
        Brown = ImageIO.read(new File("src/JewelBrown.png"));
        Black = ImageIO.read(new File("src/JewelBlack.png"));
        Green = ImageIO.read(new File("src/JewelGreen.png"));
        Yellow = ImageIO.read(new File("src/JewelYellow.png"));
        Violet = ImageIO.read(new File("src/JewelViolet.png"));
        Orange = ImageIO.read(new File("src/JewelOrange.png"));
        White = ImageIO.read(new File("src/JewelWhite.png"));
    }
}
