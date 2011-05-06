/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Seminarska;

/**
 *
 * @author zidar
 */
public class MyRezultat {
    /**
     * element z vsemi koordinatami (vrstica v prvotni tabeli)
     */
    public double[] element;
    /**
     * indeks v prvotni tabelu. nepomemben del
     */
    public int tabIndex;
    /**
     * razdalja do iskanega vektorja
     */
    public double razdalja;

    /**
     *
     * @param element
     * @param tabIndex
     * @param razdalja
     */
    public MyRezultat(double[] element, int tabIndex, double razdalja) {
        this.element = element;
        this.tabIndex = tabIndex;
        this.razdalja = razdalja;
    }

    /**
     *
     * @param razdalja
     */
    public MyRezultat(double razdalja) {
        this.razdalja = razdalja;
    }

    /**
     *
     */
    public MyRezultat() {
    }

    @Override
    public MyRezultat clone() {
        return new MyRezultat(element, tabIndex, razdalja);
    }



}
