/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Seminarska;

/**
 * @author zidar
 *
 */
public class MyHeap { //max heap
    private double[][] heap; //na 0 bo razdalja oz utez po kateri bo heap urejen
    private int stElementov; //max stevilo elementov v heapu
    int kljuc;

    /**
     * naredi heap
     * @param size max stevilo elementov ki jih bomo hranili
     */
    public MyHeap(int size) {
        this.stElementov = size;
//        size = (int) Math.pow(2,Math.ceil((Math.log(size+1)/Math.log(2))))-1;
        this.heap = new double[size][1];
        for (int i=0; i<size; i++){
            this.heap[i][0] = Double.MAX_VALUE;
        }
        this.kljuc = 0;
    }
    /**
     * vstavi element double na pravo mesto, le ce je element manjsi od najvecjega.
     *
     * @param element element ki bomo vstavili (glede na kljuc 0)
     * @return true ce smo element vstavili, false ce ga nismo (torej je prevelik)
     */
    public boolean insert(double[] element){ //stavi element ce je manjsi od najvecjega
        int mesto = 0;
        double elementKljuc = element[kljuc]; //naslavljanje tabele je pocasno
        if (heap[mesto][kljuc]>elementKljuc){
            heap[mesto] = element;
            while (true){
                int n1 = mesto*2+1; // da se znebimo cim vec mnozenja
                int n2 = n1+1;
                int novoMesto = stElementov;
                if (n1<stElementov && n2<stElementov){
                    novoMesto = heap[n1][kljuc]>heap[n2][kljuc] ? n1 : n2; //poglobimo se tja kjer je vecji element
                }else{
                    if (n1<stElementov) novoMesto = n1;
                    if (n2<stElementov) novoMesto = n2;
                }
                if (novoMesto < stElementov && heap[novoMesto][kljuc]>elementKljuc){
                    TabFun.swap(heap, novoMesto, mesto);
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
     * vrne najmajsi element v heapu
     *
     * @return najmanjsi element
     */
    public double[] getMin(){
        int min =0;
        double minKljuc = heap[min][kljuc];
        for (int i=0; i<stElementov; i++){
            if (heap[i][kljuc]<minKljuc){
                min = i;
                minKljuc = heap[min][kljuc];
            }
        }
        TabFun.printVector(heap[min]);
        return heap[min];
    }

    /**
     * izpise vse elemente v kopici
     */
    public void printHeap(){
        if (kljuc == 0){
            TabFun.printVertical(heap, 1);
        }else{
            TabFun.printVertical(heap);
        }
    }

    /**
     * klonira in vrne kopico
     * @return tabelo ki hrani kopico
     */
    public double[][] returnHeap() {
        return heap.clone();
    }
    /**
     *
     * @return najvecji element na kljucu (razddaljo)
     */
    public double getMax() {
        return heap[0][kljuc];
    }
    /**
     * v heap vstavi vse elemente ki pasejo (so manjsi od trenutnih v heapu)
     *
     * @param boundingBox seznam elementov za vstaviti
     */
    public void insert(double[][] boundingBox) {
        for (int i=0; i<boundingBox.length; i++){
            insert(boundingBox[i]);
        }
    }
}
