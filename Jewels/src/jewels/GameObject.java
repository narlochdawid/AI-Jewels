/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jewels;

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

/**
 *
 * @author jumbo
 */
public abstract class GameObject {
    
    protected int x, y;
    protected String pattern;
    protected int VX = 45, VY = 60;
    
    
    public GameObject(int x, int y, String pattern, Game game){
        this.x = x;
        this.y = y;
        this.pattern = pattern;
    }
    
    public void tick() {
        y = y;
    }
    public abstract void render(Graphics g);
    
    public void setX(int x){
        this.x= x;
    }
    public void setY(int y){
        this.y= y;
    }
    public void setVX(int VX){
        this.VX= VX;
    }
    public void setVY(int VY){
        this.VY= VY;
    }
    public void setId(String p){
        this.pattern= p;
    }
    
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getVX(){
        return this.VX;
    }
    public int getVY(){
        return this.VY;
    }
    public String getPattern(){
        return this.pattern;
    }
}
