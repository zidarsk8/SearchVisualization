package Seminarska;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zidar
 */
public class Seminarska2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //String filename = "/home/zidar/vreme1e5.csv";
        //double[][] tab = MyFileReader.readFileScanner(filename);
        double[][] tab = TabFun.makeRandomTable(100000, 10);
        double[] vektor = TabFun.makeRandomTable(1, tab[0].length)[0];
//        double[] vektor = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        double[] vektor = {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5};
        int[] zaporedje = {0,1,2};
        int zacetekDim = 10;
        int stDimenzij = 10;
        int steviloNajblizjih = 1;
        int stPonovitev = 1000;

        int[] stNaj = {1,5,10,20,50,100};


        TabFun.normalizerajCols(tab);

        System.out.println();
        TabFun.printIntVector(zaporedje);
        System.out.println();
        TabFun.printVector(vektor);
        System.out.println();


        System.out.println();






//        TabFun.stevecRacunanjRazadlj =0;
//        System.out.println();
//        System.out.println("naivno");
//        for (int i=0; i<6; i++){
//            steviloNajblizjih = stNaj[i];
//            System.out.print(""+steviloNajblizjih);
//            for (int j=zacetekDim; j<=stDimenzij; j++){
//                zaporedje = TabFun.getZaporedje(TabFun.varianca(tab), j);
//                meritevNaivno(tab,zaporedje,vektor,j,steviloNajblizjih,stPonovitev);
//            }
//            System.out.println();
//        }
//        System.out.println("stevilo racunanj razdalje : "+TabFun.stevecRacunanjRazadlj);




        TabFun.stevecRacunanjRazadlj =0;
        System.out.println();
        System.out.println("KDTree");
        for (int i=0; i<steviloNajblizjih; i++){
            steviloNajblizjih = stNaj[i];
            System.out.print(""+steviloNajblizjih);
            for (int j=zacetekDim; j<=stDimenzij; j++){
                zaporedje = TabFun.getZaporedje(TabFun.varianca(tab), j);
                meritevKDTree(tab,zaporedje,vektor,j,steviloNajblizjih,stPonovitev);
            }
            System.out.println();
        }
        System.out.println("stevilo racunanj razdalje : "+TabFun.stevecRacunanjRazadlj);



        TabFun.stevecRacunanjRazadlj =0;
        System.out.println();
        System.out.println("PRTree ");
        for (int i=0; i<steviloNajblizjih; i++){
            steviloNajblizjih = stNaj[i];
            System.out.print(""+steviloNajblizjih);
            for (int j=zacetekDim; j<=stDimenzij; j++){
                zaporedje = TabFun.getZaporedje(TabFun.varianca(tab), j);
                meritevPRTree(tab,zaporedje,vektor,j,steviloNajblizjih,stPonovitev);
            }
            System.out.println();
        }
        System.out.println("stevilo racunanj razdalje : "+TabFun.stevecRacunanjRazadlj);

        System.out.println();



        TabFun.stevecRacunanjRazadlj =0;
        System.out.println();
        System.out.println("KDnnTree ");
        for (int i=0; i<steviloNajblizjih; i++){
            steviloNajblizjih = stNaj[i];
            System.out.print(""+steviloNajblizjih);
            for (int j=zacetekDim; j<=stDimenzij; j++){
                zaporedje = TabFun.getZaporedje(TabFun.varianca(tab), j);
                meritevKDnnTree(tab,zaporedje,vektor,j,steviloNajblizjih,stPonovitev);
            }
            System.out.println();
        }
        System.out.println("stevilo racunanj razdalje : "+TabFun.stevecRacunanjRazadlj);

        System.out.println();

    }
//            if (i%(stPonovitev/100)==0){ System.out.print("*"); }

    /**
     * samo da vidimo da vse dela
     */
    public static void test(){
        // TODO code application logic here
        String filename = new String("/home/zidar/vreme1e5.csv");
//        double[][] tab = MyFileReader.readFileScanner(filename);
        double[][] tab = TabFun.makeRandomTable(20, 5);
        double[] vektor = TabFun.makeRandomTable(1, tab[0].length)[0];
//        double[] vektor = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] zaporedje = {0,1,2};
        int stDimenzij = 2;
        int steviloNajblizjih = 10;

        TabFun.normalizerajCols(tab);
        zaporedje = TabFun.getZaporedje(TabFun.varianca(tab), stDimenzij);

        System.out.println();
        TabFun.printIntVector(zaporedje);
        System.out.println();
        TabFun.printVector(vektor);
//        System.out.println();
//        TabFun.printVertical(tab);

        System.out.println();
        System.out.println();
        System.out.println();
        double[][] naivnaTabela = TabFun.makeDistanceIndexTable(tab, vektor, stDimenzij);
        TabFun.KthElement(naivnaTabela,steviloNajblizjih,0);
        TabFun.printVertical(naivnaTabela,steviloNajblizjih);

        System.out.println();
        System.out.println();
        System.out.println();

        KDTree kdTree = new KDTree(tab, zaporedje, stDimenzij);
        TabFun.printVertical(kdTree.isciNajblizje(vektor, steviloNajblizjih));

        System.out.println();
        System.out.println();
        System.out.println();

        PRTree prTree = new PRTree(new MyTable(tab), zaporedje, stDimenzij);
        prTree.isciNajblizjeBox(vektor, steviloNajblizjih).printHeap();

        System.out.println();
        System.out.println();
    }

    private static void meritevPRTree(double[][] tab, int[] zaporedje, double[] vektor, int stDimenzij, int steviloNajblizjih, int stPonovitev) {
        PRTree prTree = new PRTree(new MyTable(tab), zaporedje, stDimenzij);
        timer.startTimer();
        TabFun.stevecRacunanjRazadlj=0;
        for (int i=0; i<stPonovitev; i++){
            prTree.isciNajblizjeBox(vektor, steviloNajblizjih);
        }
//        timer.printTimerMs(",", stPonovitev);
        timer.printTimerMs("\n rac: "+(TabFun.stevecRacunanjRazadlj/stPonovitev)+"   cas: ", stPonovitev);
    }

    private static void meritevKDTree(double[][] tab, int[] zaporedje, double[] vektor, int stDimenzij, int steviloNajblizjih, int stPonovitev) {
        KDTree kdTree = new KDTree(tab, zaporedje, stDimenzij);
        timer.startTimer();
        TabFun.stevecRacunanjRazadlj=0;
        for (int i=0; i<stPonovitev; i++){
            kdTree.isciNajblizje(vektor, steviloNajblizjih);
        }
//        timer.printTimerMs(",", stPonovitev);
        timer.printTimerMs("\n rac: "+(TabFun.stevecRacunanjRazadlj/stPonovitev)+"   cas: ", stPonovitev);
    }

    private static void meritevKDnnTree(double[][] tab, int[] zaporedje, double[] vektor, int stDimenzij, int steviloNajblizjih, int stPonovitev) {
        KDnnTree kdnnTree = new KDnnTree(tab);
        kdnnTree.kdConstruct();

        timer.startTimer();
        TabFun.stevecRacunanjRazadlj=0;
        for (int i=0; i<stPonovitev; i++){
            kdnnTree.nnFind(vektor);
        }
//        timer.printTimerMs(",", stPonovitev);
        timer.printTimerMs("\n rac: "+(TabFun.stevecRacunanjRazadlj/stPonovitev)+"   cas: ", stPonovitev);
    }

    private static void meritevNaivno(double[][] tab, int[] zaporedje, double[] vektor, int stDimenzij, int steviloNajblizjih, int stPonovitev) {
        timer.startTimer();
        TabFun.stevecRacunanjRazadlj=0;
        for (int i=0; i<stPonovitev; i++){
            double[][] naivnaTabela = TabFun.makeDistanceIndexTable(tab, vektor, stDimenzij);
            TabFun.KthElement(naivnaTabela,steviloNajblizjih,0);
        }
//        timer.printTimerMs(",", stPonovitev);
        timer.printTimerMs("\n rac: "+(TabFun.stevecRacunanjRazadlj/stPonovitev)+"   cas: ", stPonovitev);
    }
}

