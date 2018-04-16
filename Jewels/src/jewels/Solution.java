/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jewels;

import java.io.Serializable;

/**
 *
 * @author jumbo
 */
public class Solution implements Serializable {
    private int x,y,xt,yt;
    private double w;

    public Solution(int x, int y, int xt, int yt, double w) {
        this.x = x;
        this.y = y;
        this.xt = xt;
        this.yt = yt;
        this.w = w;
    }
    
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getXt(){
        return this.xt;
    }
    public int getYt(){
        return this.yt;
    }
    public double getW(){
        return this.w;
    }
    public void setW(double newWeight){
        this.w=newWeight;
    }
}
