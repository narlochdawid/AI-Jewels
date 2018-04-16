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
public class Jewels {
    
    public static int size = 3;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         new Window((size+1)*60,(size)*60,"Jewels",new Game());
    }
    
}
