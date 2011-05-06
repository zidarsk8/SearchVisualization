/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Seminarska;

/**
 *
 * @author zidar
 */


public class KDTree{

    private int nivo;
    private int[] zaporedje;
    private int stDimenzij;
    private int kljuc;
    private double[] element;
    private double[] elementData; // na 0 = razdalja , na 1 = index elementa
    private KDTree manjse;
    private KDTree vecje;

    /**
     * naredi prazno instanco objekta
     */
    public KDTree() {
    }

    /**
     * generira KD drevo
     * za stevilo dimenzij vzame stevilo stolpcev v tabeli <i>tab</i>
     * in kljuce za locevanje jemlje zaporedno
     *
     * isto kot: KDTree(tab, tab.length);
     *
     * @param   tab tabela elementov za kreiranje drevesa
     */
    public KDTree(double[][] tab) {
        if (tab == null){
            return; //prazne tabele pac ne bomo dodajal :P k sm jst tko reku
        }
        this.stDimenzij = tab[0].length;
        zaporedje = new int[stDimenzij];
        for (int i = 0; i < zaporedje.length; i++) {
            zaporedje[i] = i;
        }
        this.nivo = 0;
        this.kljuc = zaporedje[nivo%stDimenzij];
        int start = 0; //prvi index
        int end = tab.length-1; //zadnji index
        this.elementData = new double[2];

        razdeli(tab, start, end); //doda element v to vozlisce in po potrevbi kreira podrevesa
    }
    
    /**
     * generira KD drevo
     * kljuce za locevanje jemlje zaporedno
     *
     * isto kot: KDTree(tab, {0,1,2,3...} , stDimenzij);
     *
     * @param   tab tabela elementov za kreiranje drevesa
     * @param   stDimenzij stevilo stolpcev po kateri bojo podatki loceni
     */
    public KDTree(double[][] tab, int stDimenzij) {
        if (tab == null){
            return; //prazne tabele pac ne bomo dodajal :P k sm jst tko reku
        }
        zaporedje = new int[stDimenzij];
        for (int i = 0; i < zaporedje.length; i++) {
            zaporedje[i] = i;
        }
        this.nivo = 0;
        this.stDimenzij = stDimenzij;
        this.kljuc = zaporedje[nivo%stDimenzij];
        int start = 0; //prvi index
        int end = tab.length-1; //zadnji index
        this.elementData = new double[2];

        razdeli(tab, start, end); //doda element v to vozlisce in po potrevbi kreira podrevesa
    }

    /**
     * generira KD drevo
     *
     * @param   tab tabela elementov za kreiranje drevesa
     * @param zaporedje zaporedje v katerem se bojo stolpci locevali
     * @param   stDimenzij stevilo stolpcev po kateri bojo podatki loceni
     */
    public KDTree(double[][] tab, int[] zaporedje, int stDimenzij) {
        if (tab == null){
            return; //prazne tabele pac ne bomo dodajal :P k sm jst tko reku
        }
        this.nivo = 0;
        this.zaporedje = zaporedje;
        this.stDimenzij = stDimenzij;
        this.kljuc = zaporedje[nivo%stDimenzij];
        int start = 0; //prvi index
        int end = tab.length-1; //zadnji index
        this.elementData = new double[2];

        razdeli(tab, start, end); //doda element v to vozlisce in po potrevbi kreira podrevesa
    }

    /**
     * se ne sme klicati, uporablja se samo za gradnjo drevesa
     *
     * @param nivo nivo na katerem se nahajamo
     * @param tab tabela vseh elementov
     * @param zaporedje zaporedje po katerem se jemljejo kljuci
     * @param stDimenzij stevilo stolpcev po katerih se locuje tabelo
     * @param start index prvega elemnta ki ki bo v drevesu
     * @param end index zadnjega elementa ki bo v drevesu
     */
    private KDTree(int nivo, double[][] tab, int[] zaporedje, int stDimenzij, int start, int end) {
        nivo++; //da lahko parent klice konstritor normalno bomo nivo sele tuka poglobili.
        this.nivo = nivo;
        this.zaporedje = zaporedje;
        this.stDimenzij = stDimenzij;
        this.kljuc = zaporedje[nivo%stDimenzij]; //iz zaporedja kljucev izberemo pravega za ta nivo
        this.elementData = new double[2];

        razdeli(tab, start, end); //doda element v to vozlisce in po potrevbi kreira podrevesa
    }
    /**
     * funkcija razdeli elemente na dva dela, delilni element shrani v to instanco KD drevesa, iz preostalih delov pa naredi nova KD poddrevesa
     *
     * @param tab tabela vseh elementov
     * @param start index prvega elementa ki pase to drevo
     * @param end index zadnjega elemnta ki pase to drevo
     */
    private void razdeli(double[][] tab, int start, int end){
        if(end >= start){
            int m = TabFun.KthElement(tab, start, end, ((end+start)/2), this.kljuc);
            this.element = tab[m].clone(); //shranimo element v drevo
            //System.out.println("  element "+tab[m]+"  mesto "+m);
            this.elementData[1] = m;
            if (start <= m-1) manjse = new KDTree(nivo, tab, zaporedje, stDimenzij, start, m-1);
            if (m+1 <= end) vecje = new KDTree(nivo, tab, zaporedje, stDimenzij, m+1, end);
        }
    }
    /**
     * izpise celotno drevo
     * <br>isto kot
     * <br><i>printTree(Double.MIN_VALUE, Double.MAX_VALUE, 0);</i>
     */
    public void printTree(){
        printTree(Double.MIN_VALUE, Double.MAX_VALUE, 0);
    }
    /**
     * naredi sprehod celotnem drevesu in izpise vse elemente ki so vecji od odK in manjsi od doK
     *
     * @param odK spodnja meja elementov za izpis
     * @param doK zgornja meja elementov za izpis
     * @param k kljuc po katerem se preveja meje
     */
    public void printTree(double odK, double doK, int k){
        try{
            if (manjse != null) manjse.printTree(odK, doK, k);
            if (element[k]>=odK && element[k]<= doK){
                System.out.print("************".substring(nivo)+"               ".substring(0,nivo)+"   nivo "+nivo+"   kljuc "+kljuc+"    element: "+(""+element[kljuc]+"                      ").substring(0, 6)+"      ");
                TabFun.printVector(element);
            }
            if (vecje != null) vecje.printTree(odK, doK, k);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    /**
     * isce i najblizjih elementov podanemu vektorju
     *
     * @param vektor vektor do katerega se racuna razdalja
     * @param i stevilo najblizjih elementov ki jih iscemo
     * @return tabela vseh i najblizjih elementov
     */
    public double[][] isciNajblizje(double[] vektor, int i) { //i = stevilo najblizjih
        MyHeap rezultat = new MyHeap(i);
        isciNajblizje(rezultat, vektor);
        return rezultat.returnHeap();
    }
    /**
     * rekurzivno poisce vse najblizje elemente podanemu vektorju
     *
     * @param rezultat posebna kopica ki hrani rezultate
     * @param vektor vektor za racunanje razdalje
     */
    private void isciNajblizje(MyHeap rezultat, double[] vektor){
        double najvecjaRazdalja = rezultat.getMax();
        if (vektor[kljuc]<element[kljuc]){ //v tej dimenziji pogledamo ali je iskani element levo ali desno
            if (manjse != null){
                manjse.isciNajblizje(rezultat, vektor);
            }
//            if (vektor[kljuc]-element[kljuc] > najvecjaRazdalja){ return; }//ce je to vecje itaq nima smisla racunat
            elementData[0] = TabFun.distSqare(element, vektor, stDimenzij); // zracunamo razdaljo tega elementa od vektorja
            if (rezultat.insert(elementData)){ // vstavimo element v tabelo rezultaov
                najvecjaRazdalja = rezultat.getMax(); //ce smo ga vstavili, moramo vzeti se novo najdaljso razdaljo
            }
            if (Math.abs(vektor[kljuc]-element[kljuc])<najvecjaRazdalja && vecje != null){ //pogledamo ce je hiperravnina izven radija
                vecje.isciNajblizje(rezultat, vektor);
            }
        }else{ //v tej dimenziji pogledamo ali je iskani element levo ali desno
            if (vecje != null){
                vecje.isciNajblizje(rezultat, vektor); //gremo do konca globoko, da se zeljeni tocki priblizamo
            }
//            if (vektor[kljuc]-element[kljuc] > najvecjaRazdalja){ return; }//ce je to vecje itaq nima smisla racunat
            elementData[0] = TabFun.distSqare(element, vektor, stDimenzij); // zracunamo razdaljo tega elementa od vektorja
            if (rezultat.insert(elementData)){ // vstavimo element v tabelo rezultaov
                najvecjaRazdalja = rezultat.getMax(); //ce smo ga vstavili, moramo vzeti se novo najdaljso razdaljo
            }
            if (Math.abs(vektor[kljuc]-element[kljuc])<najvecjaRazdalja && manjse != null){ //pogledamo ce je hiperravnina izven radija
         //   if (manjse != null){ //pogledamo ce je hiperravnina izven radija
                manjse.isciNajblizje(rezultat, vektor);
            }
        }
    }
}



