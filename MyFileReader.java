package Seminarska;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zidar
 */
public class MyFileReader {
    /**
     * prebere datoteko in jo shrani v 2d double tabelo,
     * <p> za parsanje uporablja razred scanner (zato je tudi dosti pocasna metoda) in locila so "," , "\r\n", "\n", " " in ";"
     * </p>
     *
     * @param filename
     * @return
     */
    public static double[][] readFileScanner(String filename) {
        int rows = countRowsIgnoreEmpty(filename);
        int cols = countSeperators(filename, ",")+1; //polj je za eno vec kot je seperatorjev med njimi
        double[][] tab = new double[rows][cols];
        try{
            Scanner s = new Scanner(new FileInputStream(filename));
            s.useDelimiter(",|(\r\n)|\n| |;");
            long i=0;
            while (s.hasNextDouble()){
                tab[(int)(i/10)][(int)(i%10)] = s.nextDouble();
                i++;
            }
        }catch(Exception e){
            System.out.println("Napaka pri branju datoteke\n"+e.toString());
        }
        return tab;
    }
    /**
     * steje vrstice v datoteki
     *
     * @param filename datoteka
     * @return stevilo vrstic
     */
    public static int countRows(String filename){
        try{
            int counter = 0;
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line=br.readLine())!= null){
                counter++;
            }
            return counter;
        }catch (Exception e){
            System.out.println("napaka pri stetju vrstic : "+e.toString());
            return -1;
        }
    }
    /**
     * steje neprazne vrstice
     *
     * @param filename ime datoteke
     * @return stevilo nepraznih vrstic
     */
    public static int countRowsIgnoreEmpty(String filename){
        try{
            int counter = 0;
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line=br.readLine())!= null){
                if (!line.equals("")){
                    counter++;
                }
            }
            return counter;
        }catch (Exception e){
            System.out.println("napaka pri stetju vrstic : "+e.toString());
            return -1;
        }
    }
    /**
     * steje koliko locitnevih znakov je v eni vrstici
     *
     * @param filename ime datoteke
     * @param seperator locitveni znak
     * @return stevilo znakov v prvi vrstici datoteke
     */
    public static int countSeperators(String filename, String seperator){
        try{
            int counter = 0;
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            if ((line=br.readLine())!= null){
                while (line.indexOf(seperator) != -1){
                    counter++;
                    line = line.substring(line.indexOf(seperator)+1);
                }
            }
            return counter;
        }catch (Exception e){
            System.out.println("napaka pri stetju vrstic : "+e.toString());
            return -1;
        }
    }
}
