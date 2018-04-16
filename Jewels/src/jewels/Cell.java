/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jewels;

import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.List;
import static jewels.Jewels.size;

/**
 *
 * @author jumbo
 */
public class Cell {
    
    protected List<Integer> Dendrites;
    protected List<Double> Synapses;
    
     public Cell()
     {
         Dendrites = new ArrayList<Integer>(size*size*5);
         Synapses = new ArrayList<Double>(size*size*5);
     }
   
    public double Cnt(double p)
    {
			return 1/(1+pow(Math.E,-1.0*p));
    }
    
    public double finalizeDataIC(double membranePotential)
    {
        return Dendrites.get(1);
    }
    
    public double deriv(double wartosc){
                    return Cnt(getSum())*(1-Cnt(getSum()));
    }
    
    public int getInputSize()
    {
        return Dendrites.size();
    }
    
    public void addInput(){
        Dendrites.add(0);
        Synapses.add(1.0);
    }

    public void addInput(int count)
    {
        for(int i = 1; i <= count; i++)
            this.addInput();
    }

    public double getInputData(int index)
    {
        return Dendrites.get(index);
    }

    public void setInputData(int index, int value)
    {
        Dendrites.set(index, value);
    }

    public double getInputWeight(int index)
    {
        return Synapses.get(index);
    }

    public void setInputWeight(int index, double weight)
    {
        Synapses.set(index, weight);
    }
		
    public double processCellNode(int index)
    {
        return (Dendrites.get(index)*Synapses.get(index));
    }
    
    public double getSum()
    {
        if(getInputSize() == 0)
            return -1;
        double sum = 0;
        sum+=Synapses.get(0);
        for (int i = 1; i < getInputSize(); i++)
            sum+=processCellNode(i);
        return sum;
    }

    public double getOutput()
    {
        if(getInputSize() == 0)
            return -1;
        return Cnt(getSum());
    }
    
}
