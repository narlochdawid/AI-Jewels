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
import static java.lang.Math.abs;
import java.util.LinkedList;

/**
 *
 * @author jumbo
 */
public class Handler {
    LinkedList<GameObject> object = new LinkedList<GameObject>();
    
    public void tick(int speed){
        for(int i =0; i <object.size(); i++){
            GameObject tempObj = object.get(i);
        
            tempObj.tick();
        }  
    }
    
    public void clear(){
        for(int i =0; i <object.size(); i++){
            GameObject tempObj = object.get(i);
            this.removeObject(tempObj);
        }
    }
    
    public void addObject(GameObject object){
            this.object.add(object);
    }
  
    
    public void removeObject(GameObject object){
            this.object.remove(object);
    }

    public void render(Graphics g){
        for(int i =0; i <object.size(); i++){
            GameObject tempObj = object.get(i);
        
            tempObj.render(g);
        }
        
    }

    public GameObject findByPos(int x,int y){
        for(int i =0; i <object.size(); i++){
            if(object.get(i).getX() == x && object.get(i).getY()== y){
                return object.get(i);
            }
        }
        return null;
        
    }   
}
