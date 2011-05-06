/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Seminarska;

import java.awt.Color;

/**
 *
 * @author zidar
 */
public class PRTree {
    private double[][] boundingBox;
    private int[] zaporedje;
    private int kljuc;
    private int nivo;
    private int stDimenzij;
    private PRTree vecje;
    private PRTree manjse;
    private double pivot;
    private MyDraw g;

    /**
     * prazen konstruktor samo da lahko naredimo eno instanco. naj se ne uporablja
     */
    public PRTree() {
    }

    /**
     * konstruktor
     *
     * @param tab tabela podatkov za gradnjo drevesa
     * @param zaporedje zaporedje jemanja kljucev po katerem se bo drevo delilo na poddrevesa
     * @param stDimenzij stevilo dimenzij
     */
    public PRTree(MyTable tab, int[] zaporedje, int stDimenzij) {
        this.zaporedje = zaporedje;
        this.stDimenzij = stDimenzij;
        this.nivo = 0;
        kljuc = zaporedje[nivo%stDimenzij];
        vstaviPodatke(tab);
    }
    /**
     * konstruktor
     *
     * @param tab tabela podatkov za gradnjo drevesa
     * @param zaporedje zaporedje jemanja kljucev po katerem se bo drevo delilo na poddrevesa
     * @param stDimenzij stevilo dimenzij
     * @param nivo nivo oceta, nivo drevesa ki klice konstruktor, tukaj se nivo sam poveca za 1
     */
    private PRTree(MyTable tab, int[] zaporedje, int stDimenzij, int nivo) {
        this.zaporedje = zaporedje;
        this.stDimenzij = stDimenzij;
        this.nivo = nivo+1;
        kljuc = zaporedje[this.nivo%stDimenzij];
        vstaviPodatke(tab);
    }
    /**
     *
     * @return bounding box of this node
     */
    public double[][] getBoundingBox() {
        return boundingBox;
    }
    /**
     * razdalja skatle od tocke, naj se ne klice ce je tocka v skatli!
     *
     * @param vektor tocka do katere se racuna razdalja skatle
     * @return razdalja tocke od skatle, 0 ce je tocka v skatli
     */
    public double getBoxDistance(double[] vektor){
        return TabFun.getBoxDistance(boundingBox,vektor,stDimenzij);
    }
    /**
     * pogleda ce je tocka znotraj mej ki dolocajo to drevo ali podrevo
     * @param vector
     * @return true ce so vse komponente vektorja znotraj dolocenih mej
     */
    public boolean isVectorInBox(double[] vector){
        return TabFun.isVectorInBox(boundingBox, vector, stDimenzij);
    }

    /**
     * vstavi podatke ki so v tabeli MyTable<br>
     * iz tabele vzame extremne podatke (bonding box) potem pogleda ce je dovolj podatkov da jih je treba razdeliti na dva podrevesa, in po potrebi gradi levo in desno podrevo z preostalimi podatki
     * <br> nastabi boundingBox, manjse, vecje
     * @param tab
     */
    private void vstaviPodatke(MyTable tab) {
        this.boundingBox = tab.getBoundingBox(stDimenzij);
        int delilnoMesto = tab.getEnd();
        // ce imamo v tabeli se vec elementov kot jih lahko spravimo v en list. potem razdelimo zadevo
        if (tab.getEnd()-tab.getStart() >= stDimenzij*2){ delilnoMesto = tab.split(kljuc); }
        if (delilnoMesto<0) return;
        pivot = tab.getElement(delilnoMesto, kljuc); // po temu pivotu vidimo ce ali gremo v levo ali v desno podrevo
        if (delilnoMesto>=tab.getStart()){ this.manjse = new PRTree(tab.getSubTree(tab.getStart(), delilnoMesto), zaporedje, stDimenzij, nivo); }
        if (delilnoMesto<tab.getEnd()){ this.vecje = new PRTree(tab.getSubTree(delilnoMesto+1, tab.getEnd()), zaporedje, stDimenzij, nivo); }
    }
    /**
     * metoda za iskanje najblizjih tock
     *
     * @param vektor vektor kateremu iscemo najblizje sosede
     * @param stNajblizjih stevilo najblizjih tock ki jih iscemo
     * @return najblizje tocke vektorju v obliki myHeap2
     */
    public MyHeap2 isciNajblizjeBox(double[] vektor, int stNajblizjih){
        if (vektor.length<stDimenzij){ System.out.println("premajhen vektor"); return null; }
        MyHeap2 rezultati = new MyHeap2(stNajblizjih);
        isciNajblizjeBox(vektor, rezultati);
        return rezultati;
    }
    /**
     * privatna metoda za rekurzivni spust po drevesu, za iskanje n najblizjih tock
     *
     * @param vektor vektor kateremu iscemo najblizje tocke
     * @param rezultati tabela rezultatov
     */
    private void isciNajblizjeBox(double[] vektor, MyHeap2 rezultati) {
        boolean manjseInBox = (manjse != null && manjse.isVectorInBox(vektor));
        boolean vecjeInBox = (vecje != null && vecje.isVectorInBox(vektor));
        if (manjseInBox){ manjse.isciNajblizjeBox(vektor, rezultati); }
        if (vecjeInBox){ vecje.isciNajblizjeBox(vektor, rezultati); }
        insertBoundingBox(boundingBox,vektor,rezultati); //
        if (!manjseInBox && !vecjeInBox){
            double manjseDist = Double.MAX_VALUE;
            double vecjeDist = Double.MAX_VALUE;
            if (manjse != null){ manjseDist = TabFun.getBoxDistance(manjse.getBoundingBox(), vektor, stDimenzij); }
            if (vecje != null){ vecjeDist = TabFun.getBoxDistance(vecje.getBoundingBox(), vektor, stDimenzij); }

            if (manjseDist<vecjeDist){ //pogledamo katera skatla je blizje;
                if (rezultati.getMaxRazdalja()>manjseDist){ manjse.isciNajblizjeBox(vektor, rezultati); }
                if (rezultati.getMaxRazdalja()>vecjeDist){ vecje.isciNajblizjeBox(vektor, rezultati); }
            }else{
                if (rezultati.getMaxRazdalja()>vecjeDist){ vecje.isciNajblizjeBox(vektor, rezultati); }
                if (rezultati.getMaxRazdalja()>manjseDist){ manjse.isciNajblizjeBox(vektor, rezultati); }
            }
        }else{
            if (manjse!=null && !manjseInBox && rezultati.getMaxRazdalja()>TabFun.getBoxDistance(manjse.getBoundingBox(), vektor, stDimenzij)){
                manjse.isciNajblizjeBox(vektor, rezultati);
            }
            if (vecje!=null  && !vecjeInBox  && rezultati.getMaxRazdalja()>TabFun.getBoxDistance(vecje.getBoundingBox(),  vektor, stDimenzij)){
                 vecje.isciNajblizjeBox(vektor, rezultati);
            }
        }
    }
    /**
     * metoda za iskanje najblizjih tock in izris na polje
     * <br> metoda naj se klice le iz Sem2Tester
     *
     * @param vektor vektor kateremu iscemo najblizje sosede
     * @param stNajblizjih stevilo najblizjih tock ki jih iscemo
     * @return najblizje tocke vektorju v obliki myHeap2
     */
    public MyHeap2 drawIsciNajblizjeBox(double[] vektor, int stNajblizjih){
        if (vektor.length<stDimenzij){
            throw new IndexOutOfBoundsException("vektor je manjsi od stevila dimenzij");
        }
        MyHeap2 rezultati = new MyHeap2(stNajblizjih);
        drawIsciNajblizjeBox(vektor, rezultati);
        g.drawTableRezultati(rezultati.getElementTable());
        return rezultati;
    }
    /**
     * privatna metoda za rekurzivni spust po drevesu, za iskanje n najblizjih tockin izris na polje
     * <br> metoda naj se klice le iz Sem2Tester 
     *
     * @param vektor vektor kateremu iscemo najblizje tocke
     * @param rezultati tabela rezultatov
     */
    private void drawIsciNajblizjeBox(double[] vektor, MyHeap2 rezultati) {
        g.drawBounds(boundingBox,4,Color.red);
        try { Thread.sleep(100);} catch (Exception e) { }

        boolean manjseInBox = (manjse != null && manjse.isVectorInBox(vektor));
        boolean vecjeInBox = (vecje != null && vecje.isVectorInBox(vektor));
        if (manjseInBox){ manjse.drawIsciNajblizjeBox(vektor, rezultati); }
        if (vecjeInBox){ vecje.drawIsciNajblizjeBox(vektor, rezultati); }

        insertBoundingBox(boundingBox,vektor,rezultati); //

        if (!manjseInBox && !vecjeInBox){
            double manjseDist = Double.MAX_VALUE;
            double vecjeDist = Double.MAX_VALUE;
            if (manjse != null){ manjseDist = TabFun.getBoxDistance(manjse.getBoundingBox(), vektor, stDimenzij); }
            if (vecje != null){ vecjeDist = TabFun.getBoxDistance(vecje.getBoundingBox(), vektor, stDimenzij); }

            if (manjseDist<vecjeDist){ //pogledamo katera skatla je blizje;
                if (rezultati.getMaxRazdalja()>manjseDist){ manjse.drawIsciNajblizjeBox(vektor, rezultati); }
                if (rezultati.getMaxRazdalja()>vecjeDist){ vecje.drawIsciNajblizjeBox(vektor, rezultati); }
            }else{
                if (rezultati.getMaxRazdalja()>vecjeDist){ vecje.drawIsciNajblizjeBox(vektor, rezultati); }
                if (rezultati.getMaxRazdalja()>manjseDist){ manjse.drawIsciNajblizjeBox(vektor, rezultati); }
            }
        }else{
            if (manjse!=null && !manjseInBox && rezultati.getMaxRazdalja()>TabFun.getBoxDistance(manjse.getBoundingBox(), vektor, stDimenzij)){
                manjse.drawIsciNajblizjeBox(vektor, rezultati);
            }
            if (vecje!=null  && !vecjeInBox  && rezultati.getMaxRazdalja()>TabFun.getBoxDistance(vecje.getBoundingBox(),  vektor, stDimenzij)){
                 vecje.drawIsciNajblizjeBox(vektor, rezultati);
            }
        }
    }
    /**
     * not implemented yet
     * @param vektor vektor kateremu iscemo najblizje sosede
     * @param stNajblizjih stevilo najblizjih tock ki jih iscemo
     */
    public void drawIsciNajblizjeTrivial(double[] vektor, int stNajblizjih){
        if (vektor.length<stDimenzij){
            throw new IndexOutOfBoundsException("vektor je manjsi od stevila dimenzij");
        }
        MyHeap2 rezultati = new MyHeap2(stNajblizjih);
        drawIsciNajblizjeTrivial(vektor, rezultati);
    }
    /**
     * not implemented yet izris na polje
     * <br> metoda naj se klice le iz Sem2Tester
     *
     * @param vektor vektor kateremu iscemo najblizje tocke
     * @param rezultati tabela rezultatov
     */
    private void drawIsciNajblizjeTrivial(double[] vektor, MyHeap2 rezultati) {
        g.drawBounds(boundingBox,4,Color.blue);
        try { Thread.sleep(100);} catch (Exception e) { }
        if (vektor[kljuc]<=pivot){
            if (manjse != null){ manjse.drawIsciNajblizjeTrivial(vektor, rezultati); }
        }else{
            if (vecje != null){ vecje.drawIsciNajblizjeTrivial(vektor, rezultati); }
        }
        insertBoundingBox(boundingBox,vektor,rezultati); //
    }

    /**
     * izpise vse elementa drevesa na standardni izhod
     */
    public void printTree(){
        TabFun.printVertical(boundingBox);
        System.out.println();
        if (this.manjse!= null) this.manjse.printTree();
        if (this.vecje!= null) this.vecje.printTree();
    }

    /**
     * narise drevo, izris lahko upocasnimo z slip casovnikom
     * <br><i>ne uporablja threda tko da je dobr me hiter casovnik</i>
     *
     * @param g risalni razred
     * @param slip delay timer
     */
    public void drawTree(MyDraw g, int slip){
        this.g = g;
        g.drawBounds(boundingBox);
        try {
            Thread.sleep(slip);
        } catch (Exception e) {
            System.out.println("no time "+e.toString());
        }
        if (this.manjse!= null) this.manjse.drawTree(g,slip);
        if (this.vecje!= null) this.vecje.drawTree(g,slip);
    }

    /**
     * vstavi robne elemente(boundingBox) v tabelo rezultatov, v primeru da so elemti blizji od rezultatov ki so ze shranjeni
     *
     * @param boundingBox tabela elemtntov za vstaviti
     * @param vektor vektor do katerega se racuna razdalja
     * @param rezultati tabela kamor se shrani najblizje elemente tabele.
     */
    private void insertBoundingBox(double[][] boundingBox, double[] vektor, MyHeap2 rezultati) {
        for(int i=0; i<boundingBox.length; i++){
            double razdalja = TabFun.distSqare(boundingBox[i], vektor, stDimenzij);
            rezultati.insert(boundingBox[i], 0, razdalja);
        }
    }
}
