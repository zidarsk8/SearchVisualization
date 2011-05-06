/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Seminarska;

/**
 *
 * @author zidar
 */
public class MyTable {
    double[][] tab;
    int cols;
    int rows;
    int start; //ce zelimi uporabljati manjsi del tabele kot ga dejansko imamo ne da bi
    int end;

    public MyTable(double[][] tab) {
        try{
            this.tab = tab;
            this.rows = tab.length;
            this.cols = tab[0].length;
            this.start = 0;
            this.end = this.rows-1;
        }catch (Exception e){

        }
    }
    public void resetBounds(){
        this.start = 0;
        this.end = this.rows-1;
    }

    public double[][] cloneTab(){
        return this.tab.clone();
    }
    public double[][] getTab() {
        return tab;
    }
    public int getEnd() {
        return end;
    }
    public int getStart() {
        return start;
    }
    public int getMinIndex(int kljucIndex){
        return getMinIndex(start, end, kljucIndex);
    }
    public int getMaxIndex(int kljucIndex){
        return getMaxIndex(start, end, kljucIndex);
    }
    public int getMinIndex(int startIndex,int endIndex, int kljucIndex){
        return TabFun.getMinIndex(tab, startIndex, endIndex, kljucIndex);
    }
    public int getMaxIndex(int startIndex,int endIndex, int kljucIndex){
        return TabFun.getMaxIndex(tab, startIndex, endIndex, kljucIndex);
    }
    public double[] getMinElement(int kljucIndex){
        return getMinElement(start, end, kljucIndex);
    }
    public double[] getMaxElement(int kljucIndex){
        return getMaxElement(start, end, kljucIndex);
    }
    public double[] getMinElement(int startIndex,int endIndex, int kljucIndex){
        return TabFun.getMin(tab, startIndex, endIndex, kljucIndex);
    }
    public double[] getMaxElement(int startIndex,int endIndex, int kljucIndex){
        return TabFun.getMax(tab, startIndex, endIndex, kljucIndex);
    }
    public double[][] getMinPoints(int stDimenzij){
        return getMinPoints(start, end, stDimenzij);
    }
    public double[][] getMaxPoints(int stDimenzij){
        return getMaxPoints(start, end, stDimenzij);
    }
    public double[][] getMinPoints(int startIndex,int endIndex, int stDimenzij){
        double[][] minPoints = TabFun.getMinPoints(tab, startIndex,endIndex, stDimenzij); //vzame minimalne tocke jih postavi na konec tabele
        int numberOfPoints = minPoints.length;
        for (int i=0; i<numberOfPoints; i++){ //premaknemo vse tocke na konec tabele
            TabFun.swap(tab, endIndex-i, end);
            end--;
        }
//        end -= numberOfPoints; //odstanimo tocke ki iz tabele.
        return minPoints;
    }
    public double[][] getMaxPoints(int startIndex,int endIndex, int stDimenzij){
        double[][] maxPoints = TabFun.getMaxPoints(tab, startIndex,endIndex, stDimenzij); //vzame minimalne tocke jih postavi na konec tabele
        int numberOfPoints = maxPoints.length;
        for (int i=0; i<numberOfPoints; i++){ //premaknemo vse tocke na konec tabele
            TabFun.swap(tab, endIndex-i, end);
            end--;
        }
//        end -= numberOfPoints; //odstanimo tocke ki iz tabele.
        return maxPoints;
    }
    public double[][] getBoundingBox(int stDimenzij){
        return getBoundingBox(start, end, stDimenzij);
    }
    public double[][] getBoundingBox(int startIndex,int endIndex, int stDimenzij){
        double[][] maxPoints = getMaxPoints(startIndex, endIndex, stDimenzij);
        startIndex = start>startIndex? start : startIndex;
        endIndex = end<endIndex ? end : endIndex;
        double[][] minPoints = getMinPoints(startIndex, endIndex, stDimenzij);

        return TabFun.mergeTables(maxPoints,minPoints);
    }
    public MyTable getSubTree(int startIndex, int endIndex){
        if (start<=startIndex && end>=endIndex){
            double[][] sub = new double[endIndex-startIndex+1][];
            int st=0;
            for (int i=startIndex; i<=endIndex; i++){
                sub[st++] = tab[i];
            }
            return new MyTable(sub);
        }else{
            return null;
        }
    }
    public void setEnd(int end) {
        if (end>=start && end<tab.length){
            this.end = end;
        }
    }
    public void setStart(int start) {
        if (start>=0 && start<=end){
            this.start = start;
        }
    }
    public void sort(){
        sort(0);
    }
    public void sort(int kljucIndex){
        sort(start, end, kljucIndex);
    }
    public void sort(int startIndex, int endIndex, int kljucIndex){
        TabFun.quicksort(tab, startIndex, endIndex, kljucIndex);
    }
    public int split(int kljuc){
        return split(start, end, kljuc);
    }
    public int split(int startIndex, int endIndex, int kljuc){
        if (startIndex > endIndex){
            return -1; //tabela je prazna
        }
        int elementIndex = (startIndex+endIndex)/2;
        return TabFun.KthElement(tab, startIndex, endIndex, elementIndex, kljuc);
    }
    public double[] selectElement(int kljuc){
        return selectElement(((end+start)/2), start, end, kljuc);
    }
    public double[] selectElement(int elementIndex, int kljuc){
        return selectElement(elementIndex, start, end, kljuc);
    }
    public double[] selectElement(int elementIndex, int startIndex, int endIndex, int kljuc){
        int splitPoint = TabFun.KthElement(tab, startIndex, endIndex, elementIndex, kljuc);
        if (splitPoint == elementIndex){
            return tab[splitPoint];
        }
        return null;
    }
    public void removeElement(int elementIndex){
        TabFun.swap(tab, elementIndex, end);
        end--;
    }
    public void printTable(){
        TabFun.printVertical(tab,start, end);
    }
    public double getElement(int elementIndex, int kljuc){
        return tab[elementIndex][kljuc];
    }

    void normaliziraj() {
        TabFun.normalizerajCols(tab);
    }
}
