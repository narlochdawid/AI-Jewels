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
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static jewels.Jewels.size;

public class CellNetwork {

    private Cell[] ICells;
    private double n = 0.9;
    private int InCnt;
    
    List<Solution> learnList;
    List<int[][]> mp;

    public CellNetwork(int in,List<Solution> ll,List<int[][]> mpList) {
        this.learnList = ll;
        this.mp = mpList;
        InCnt = in;
        ICells = new Cell[InCnt];
        Random r = new Random();
        for (int i = 0; i<InCnt; i++){
            ICells[i] = new Cell();
            for(int j=0;j<size*size*5;j++){
                ICells[i].addInput();
                ICells[i].setInputWeight(j, r.nextDouble());
            }
        }
    }

    public void uczSie(int step) {
        int [][] map = mp.get(step);
        for (int i = 0; i<InCnt; i++){
            for(int j=0;j<size;j++)
                for(int k=0;k<size;k++){
                    ICells[i].setInputData(map[j][k]+5*k+15*j,1 );
                }
        }
        
        double bladpocz = 0;
        int x,y,xt,yt;
        int z;
        for (int i = 0; i<InCnt; i++){
            yt = learnList.get(step).getYt();
            xt = learnList.get(step).getXt();
            y = learnList.get(step).getY();
            x = learnList.get(step).getX();
            if(i == yt + 3*xt + 3*3*y+3*3*3*x)
                z = 1;
            else z = 0; 
            bladpocz = (z - ICells[i].getOutput());
            for(int j=0;j<size*size*5;j++){
                ICells[i].setInputWeight(j, ICells[i].getInputWeight(j) + bladpocz*n*ICells[i].getInputData(j));
            }
        }
    }

    public int dajWynik(int map[][]) {
        int index = -1;
        double max = 0;
        for (int i = 0; i<InCnt; i++){
            for(int j=0;j<size;j++)
                for(int k=0;k<size;k++){
                    ICells[i].setInputData(map[j][k]+5*j+15*j,1 );
                }
        }
        for (int i = 0; i<InCnt; i++){            double d = ICells[i].getOutput();
            if(d > 0.7)
                if(d > max){
                    index = i;
                    max = d;
                }
        }
        return index;
    }
}
