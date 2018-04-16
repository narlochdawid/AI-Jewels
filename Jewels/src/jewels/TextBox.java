/*
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

/**
 *
 * @author jumbo
 */
public class TextBox {
    
    private Rectangle labl;
    private String txt;
    private int x,y;
    private int w,h;
    private int fontSize;

    public TextBox(int x,int y,String txt,int fnts,int w,int h) {
        this.txt = txt;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.fontSize = fnts;
        labl = new Rectangle(x, y, w, h);
    }
            
    
    
    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        Font fnt0 = new Font("arial",Font.BOLD,fontSize);
        g.setFont(fnt0);
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, w, h);
        g.setColor(Color.WHITE);
        g.drawString(txt, x-txt.length()*fontSize/4+w/2-fontSize/3, y+h-fontSize/2-3);
        g2d.draw(labl);
    }
    
    public String getTxt(){
        return this.txt;
    }
    public void setTxt(String txt){
        this.txt = txt;
    }
}
