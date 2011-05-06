package Seminarska;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author zidar
 */
public class timer {

    private static long timer;
    private static long timerPause;

    /**
     * zacne timer
     */
    public static void startTimer(){
        timer = System.nanoTime();
    }
    /**
     * konca timer
     * @param string sporocilo ki se izpise pred casom
     * @param steviloPonovitev stevilo za normiranje (deljenje) casa, v primeri da eno operaci n krat ponavljamo
     */
    public static void printTimer(String string,long steviloPonovitev) {
        timer = (System.nanoTime()-timer)/steviloPonovitev;
        int sekunde = (int)(timer/1000000000);
        timer %= 1000000000;
        String nule = new String("000000000");

//        LOL ker izpis ... to more asistent vidt
//        System.out.print("\n"+string+sekunde+"."+nule.substring((""+timer).length())+""+timer);
        System.out.println(string+sekunde+"."+nule.substring((""+timer).length())+""+timer);
    }
    public static void printTimerMs(String string,long steviloPonovitev) {
        timer = (System.nanoTime()-timer)/steviloPonovitev;
        int sekunde = (int)(timer/1000000000);
        timer %= 1000000000;
        String nule = new String("000000000");
        System.out.print(string+sekunde+"."+nule.substring((""+timer).length())+""+timer);
    }

    /**
     * vstavi timer in vrne lepo formatiran cas v obliki 0.99999999  (v sekundah)
     * @param string prefix pri izpisu, le doda se pred cas
     * @param steviloPonovitev stevilo z katerim se bo cas dell da dobimo cas ene iteracije
     * @return string prefix+cas
     */
    public static String returnTimer(String string,long steviloPonovitev) {
        timer = (System.nanoTime()-timer)/steviloPonovitev;
        int sekunde = (int)(timer/1000000000);
        timer %= 1000000000;
        String nule = new String("000000000");
//        LOL ker izpis ... to more asistent vidt
//        System.out.print("\n"+string+sekunde+"."+nule.substring((""+timer).length())+""+timer);
        return (string+sekunde+"."+nule.substring((""+timer).length())+""+timer);
    }
}
