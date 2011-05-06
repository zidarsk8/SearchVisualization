/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Seminarska;

/**
 * @author zidar
 *
 */
public class MyHeap2 { //max heap
    private MyRezultat[] heap;
    private int stElementov; //max stevilo elementov v heapu

    /**
     * naredi heap
     * @param size max stevilo elementov ki jih bomo hranili
     */
    public MyHeap2(int size) {
        this.stElementov = size;
//        size = (int) Math.pow(2,Math.ceil((Math.log(size+1)/Math.log(2))))-1;
        this.heap = new MyRezultat[size];
        for (int i=0; i<size; i++){
            this.heap[i] = new MyRezultat(Double.MAX_VALUE);
        }
    }
    /**
     * vstavi element double na pravo mesto, le ce je element manjsi od najvecjega.
     *
     * @param element element ki bomo vstavili (glede na kljuc 0)
     * @param tabIndex index elementa v prvotni tabeli, ni pomemben
     * @param razdalja (kluc) razdalja elementa od vektorja, po temu se sortira heap.
     * @return true ce smo element vstavili, false ce ga nismo (torej je prevelik)
     */
    public boolean insert(double[] element,int tabIndex, double razdalja){ //stavi element ce je manjsi od najvecjega
        int mesto = 0;
        if (heap[mesto].razdalja>razdalja){
            heap[mesto].element = element;
            heap[mesto].razdalja = razdalja;
            heap[mesto].tabIndex = tabIndex;
            while (true){
                int n1 = mesto*2+1; // da se znebimo cim vec mnozenja
                int n2 = n1+1;
                int novoMesto = stElementov;
                if (n1<stElementov && n2<stElementov){
                    novoMesto = heap[n1].razdalja>heap[n2].razdalja ? n1 : n2; //poglobimo se tja kjer je vecji element
                }else{
                    if (n1<stElementov) novoMesto = n1;
                    if (n2<stElementov) novoMesto = n2;
                }
                if (novoMesto < stElementov && heap[novoMesto].razdalja>razdalja){
//                    TabFun.swap(heap, novoMesto, mesto);
                    MyRezultat temp = heap[novoMesto].clone();
                    heap[novoMesto] = heap[mesto];
                    heap[mesto] = temp;
                    mesto = novoMesto;
                }else{
                    return true;
                }
            }
        }else{
            return false;
        }
    }
    /**
     *
     * @return vrne element z najmanjso razdaljo
     */
    public double[] getMinElement(){
        int min =0;
        double minKljuc = heap[min].razdalja;
        for (int i=0; i<stElementov; i++){
            if (heap[i].razdalja<minKljuc){
                min = i;
                minKljuc = heap[min].razdalja;
            }
        }
//        TabFun.printVector(heap[min].element);
        return heap[min].element;
    }
    /**
     *
     * @return vrne najmanjso razdaljo
     */
    public double getMinRazdalja(){
        int min =0;
        double minKljuc = heap[min].razdalja;
        for (int i=0; i<stElementov; i++){
            if (heap[i].razdalja<minKljuc){
                min = i;
                minKljuc = heap[min].razdalja;
            }
        }
//        TabFun.printVector(heap[min].element);
        return heap[min].razdalja;
    }
    /**
     *
     * @return element z najvecjo razdaljo
     */
    public double[] getMaxElement(){
        return heap[0].element;
    }
    /**
     * 
     * @return vrne najvecjo razdaljo
     */
    public double getMaxRazdalja(){
        return heap[0].razdalja;
    }

    /**
     * izpise vse elemente v kopici
     */
    public void printHeap(){
        for (int i = 0; i < heap.length; i++) {
            System.out.print("razdalja: "+(heap[i].razdalja+"                     ").substring(0,20)+"   element: ");
            TabFun.printVector(heap[i].element);
        }
    }

    /**
     * klonira in vrne kopico
     * @return tabelo ki hrani kopico
     */
    public MyRezultat[] returnHeap() {
        return heap.clone();
    }

    double[][] getElementTable() {
        double[][] d = new double[heap.length][];
        for (int i=0; i<heap.length; i++){
            d[i]= heap[i].element.clone();
        }
        return d;
    }

}
