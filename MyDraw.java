/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Seminarska;

import java.awt.*;

/**
 *
 * @author zidar
 */
public class MyDraw {
    private Color[] colorTable;
    private Graphics g;
    private int width;
    private int height;
    private boolean koordinate;
    private int faktor=1;
    private int xzamik = 20;


    public MyDraw(Graphics g) {
        this.g = g;
        colorTable = new Color[100];
        for (int i=0; i<colorTable.length; i++){
            colorTable[i] = new Color(rand(), rand(), rand());
        }
    }

    public int getFaktor() {
        return faktor;
    }

    public void setFaktor(int faktor) {
        this.faktor = faktor;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isKoordinate() {
        return koordinate;
    }

    public void setKoordinate(boolean koordinate) {
        this.koordinate = koordinate;
    }
    
    private int rand(){
        return rand(0,200);
    }
    private int rand(int a, int b){
        return (int) (Math.random()*(b-a)+a);
    }

    public void drawTable(Graphics g,double[][] tab){
        for(int i=0; i<tab.length; i++){
            g.setColor(colorTable[i%colorTable.length]);
            drawPointDouble(g, tab[i][0], tab[i][1]);
        }
    }

    public void drawPointDouble(Graphics g, double x, double y){
        drawPoint(g, (int)(x*faktor), (int)(y*faktor));
    }
    public void fillPointDouble(Graphics g, double x, double y){
        fillPoint(g, (int)(x*faktor), (int)(y*faktor));
    }
    public void drawPoint(Graphics g, int x, int y){
        g.drawOval(x-2+xzamik, height-y-2, 4, 4);
        if (koordinate){
            g.drawString("("+x+","+y+")", x+5+xzamik, height-y-10);
        }
    }
    public void fillPoint(Graphics g, int x, int y){
        g.fillOval(x-2+xzamik, height-y-2, 4, 4);
        if (koordinate){
            g.drawString("("+x+","+y+")", x+5+xzamik, height-y-10);
        }
    }
    public void fillPointDoubleVector(Graphics g, double x, double y){
        fillPointVector(g, (int)(x*faktor), (int)(y*faktor));
    }
    public void fillPointVector(Graphics g, int x, int y){
        g.setColor(Color.red);
        g.fillOval(x-5+xzamik, height-y-5, 10, 10);
            g.drawString("("+x+","+y+")", x+5+xzamik, height-y-10);
    }

    public void drawBounds(double[][] tab){
        int el = tab.length<4? tab.length:4;
        for(int i=0; i<el; i++){
            g.setColor(colorTable[i%colorTable.length]);
            fillPointDouble(g, tab[i][0], tab[i][1]);
        }
        int minX = (int)(TabFun.getMin(tab, 0)[0]*faktor);
        int maxX = (int)(TabFun.getMax(tab, 0)[0]*faktor);
        int minY = (int)(TabFun.getMin(tab, 1)[1]*faktor);
        int maxY = (int)(TabFun.getMax(tab, 1)[1]*faktor);
        int recW = maxX-minX;
        int recH = maxY-minY;

        g.drawRect(minX+xzamik, height-maxY, recW, recH);

    }

    public void drawBounds(double[][] tab,int thick){
        drawBounds(tab, thick, Color.red);
    }

    public void drawBounds(double[][] tab,int thick, Color c){
        int el = tab.length<4? tab.length:4;
        g.setColor(c);
        int minX = (int)(TabFun.getMin(tab, 0)[0]*faktor);
        int maxX = (int)(TabFun.getMax(tab, 0)[0]*faktor);
        int minY = (int)(TabFun.getMin(tab, 1)[1]*faktor);
        int maxY = (int)(TabFun.getMax(tab, 1)[1]*faktor);
        int recW = maxX-minX;
        int recH = maxY-minY;

        g.drawRect(minX+xzamik, height-maxY, recW, recH);
        for (int i=0; i<thick/2; i++)
        g.drawRect(minX+xzamik-i, height-maxY-i, recW+i*2, recH+i*2);

    }

    void drawVectorPoint(double[] vector) {
        fillPointDoubleVector(g, vector[0], vector[1]);
    }

    void drawTable(double[][] elementTable) {
        drawTable(g, elementTable);
    }

    void drawVektorRezultati(double[] vektor){
        koordinate = true;
        fillPointDouble(g, vektor[0], vektor[1]);
        koordinate = false;
    }

    void drawTableRezultati(double[][] elementTable) {
        koordinate = true;
        drawTable(elementTable);
        koordinate = false;
    }
}
