/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Seminarska;

import java.util.Arrays;
import java.util.Vector;

/**
 *
 * @author zidar
 */
public class MyVector extends Vector{
    public void addArray(double[][] tab){
        this.addAll(Arrays.asList(tab));
    }

    public void printAll(){
        for(int i=0; i<this.elementCount; i++){
            TabFun.printVector((double[])this.get(i));
        }
    }

    public double[] getElement(int index){
        return (double[])this.get(index);
    }
    public double getValue(int index, int kljuc){
        return ((double[])this.get(index))[kljuc];
    }

    public int getMaxIndex(int kljuc){
        int maxIndex = 0;
        double maxValue = getValue(maxIndex, kljuc);
        for (int i=0; i<this.elementCount; i++){
            if (maxValue<getValue(i, kljuc)){
                maxIndex = i;
                maxValue = getValue(i, kljuc);
            }
        }
        return maxIndex;
    }
    public int getMinIndex(int kljuc){
        int minIndex = 0;
        double minValue = getValue(minIndex, kljuc);
        for (int i=0; i<this.elementCount; i++){
            if (minValue>getValue(i, kljuc)){
                minIndex = i;
                minValue = getValue(i, kljuc);
            }
        }
        return minIndex;
    }

    public MyVector getMinPoints(int stDimenzij){
        try{
            MyVector minPoints = new MyVector();
            stDimenzij = stDimenzij<this.elementCount?stDimenzij:this.elementCount;
            for (int i=0; i<stDimenzij; i++){
                int index = getMinIndex(i);
                minPoints.add(this.get(index)); //tocko damo naprej da se vpise v tabelo, in jo izbrisemo iz seznama
                this.remove(index);
            }
            return minPoints;
        }catch (IndexOutOfBoundsException e){
            System.out.println("getMinPoints, prevec dimenzij "+e.toString());
        }
        return null;
    }
    public MyVector getMaxPoints(int stDimenzij){
        try{
            MyVector maxPoints = new MyVector();
            stDimenzij = stDimenzij<this.elementCount?stDimenzij:this.elementCount;
            for (int i=0; i<stDimenzij; i++){
                int index = getMaxIndex(i);
                maxPoints.add(this.get(index)); //tocko damo naprej da se vpise v tabelo, in jo izbrisemo iz seznama
                this.remove(index);
            }
            return maxPoints;
        }catch (IndexOutOfBoundsException e){
            System.out.println("getMaxPoints, prevec dimenzij "+e.toString());
        }
        return null;
    }

}
