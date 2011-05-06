package Seminarska;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author zidar
 */

// double tabela 2d in uporabne funkcije
public class TabFun{
    public static long stevecRacunanjRazadlj;
    /**
     * vrne zabelo z razdaljami posameznih tock in njihovimi indeksi v prvotni tabeli
     *
     * @param tab tabela elemntov
     * @param vektor vektro do katerega bomo racunali razdaljo
     * @return vrne tabelo ki ima na [x][0] razdalje in na [x][1] indekse elementov
     */
    public static double[][] makeDistanceIndexTable(double[][] tab, double[] vektor){
        return makeDistanceIndexTable(tab, vektor, (tab[0].length<vektor.length? tab[0].length: vektor.length));
    }
    /**
     * vrne zabelo z razdaljami posameznih tock in njihovimi indeksi v prvotni tabeli
     *
     * @param tab tabela elemntov
     * @param vektor vektro do katerega bomo racunali razdaljo
     * @param stDimenzij stevilo dimenzij ki bomo upostevali pri racunanju razdalje
     * @return vrne tabelo ki ima na [x][0] razdalje in na [x][1] indekse elementov
     */
    public static double[][] makeDistanceIndexTable(double[][] tab, double[] vektor, int stDimenzij){
        double[][] di = new double[tab.length][2];
        for (int i=0; i<tab.length; i++){
            di[i][0] = distSqare(tab[i], vektor, stDimenzij);
            di[i][1] = i;
        }
        return di;
    }
    /**
     * vrne razdaljo med vektrojema, pri katerem uposteva samo toliko elementov kolikor jih ima krajsi vektor
     *
     * @param a prvi vektor
     * @param b drugi vektor
     * @return razdalja med vektorjema
     */
    public static double distSqare(double[] a, double[] b){
        return distSqare(a, b, (a.length<b.length?a.length:b.length));
    }
    /**
     * vrne kvadrat eukledove razdalje med dvema vektorjema
     *
     * @param a prvi vektor
     * @param b drugi vektor
     * @param stDimenzij stevilo elementov ki bomo upostevali pri razdalji
     * @return razdalja med vektorjema
     */
    public static double distSqare(double[] a, double[] b, int stDimenzij){
        stevecRacunanjRazadlj++;
        double dist = 0;
        for(int i=0; i<stDimenzij; i++){
            dist+= Math.pow((a[i]-b[i]), 2);
        }
        return dist;
    }

    /**
     * naredi random 2D tabelo v kateri so vrednosti od 0 do 1
     *
     * @param rows stevilo vrstic
     * @param cols stevilo stolpcev
     * @return nakljucno generirano tabelo
     */
    public static double[][] makeRandomTable(int rows, int cols){
        return makeRandomTable(rows, cols, 1);
    }
    /**
     * naredi random 2D tabelo v kateri so vrednosti od 0 do faktor
     *
     * @param rows stevilo vrstic
     * @param cols stevilo stolpcev
     * @param faktor faktor s katerim bomo vrednosti med 0 in 1 pomnozili
     * @return nakljucno generirano tabelo
     */
    public static double[][] makeRandomTable(int rows, int cols,int faktor){
        double[][] t = new double[rows][cols];
        for (int i=0; i<rows ; i++){
            for (int j=0; j<cols; j++){
                t[i][j] = Math.random()*faktor;
            }
        }
        return t;
    }
    /**
     * v tabeli zamenja dva elementa
     *
     * @param tab tabela
     * @param a prvi index
     * @param b drugi index
     */
    public static void swap(double[][] tab, int a, int b){
        double[] temp = tab[a];
        tab[a] = tab[b];
        tab[b] = temp;
    }
    /**
     * postavi element na indeksu mesto v njegovo urejeno pozicijo tako da so vsi manjsi elemeti levo in vsi vecji elementi desono
     *
     * @param tab tabela z elemeti ki jo bomo delo uredili
     * @param mesto index elementa ki bomo postavili na pravo mesto
     * @param kljuc kljuc po katerem se bo gledala urejenost tabele
     * @return vrne koncni index elementa ki je zacel an lokaciji mesto
     */
    private static int part(double[][] tab, int mesto, int kljuc){
        return part(tab, 0, tab.length-1, mesto, kljuc);
    }
    /**
     * postavi element na indeksu mesto v njegovo urejeno pozicijo tako da so vsi manjsi elemeti levo in vsi vecji elementi desono, na intervalu od start do end
     *
     * @param tab tabela z elemeti ki jo bomo delo uredili
     * @param start index prvega elementa ki bomo upostevali
     * @param end index zadnjega elementa ki bomo upostevali
     * @param mesto index elementa ki bomo postavili na pravo mesto
     * @param kljuc kljuc po katerem se bo gledala urejenost tabele
     * @return vrne koncni index elementa ki je zacel an lokaciji mesto
     */
    private static int part(double[][] tab, int start, int end, int mesto, int kljuc){
        swap(tab, end, mesto);
        double pivot = tab[end][kljuc];
        int index = start;
        for (int i=start; i<end; i++){
            if (tab[i][kljuc]<pivot){
                swap(tab,index,i);
                index++;
            }
        }
        swap(tab,end,index);
        return index;
    }
    /**
     * postavi element na indeksu mesto v njegovo urejeno pozicijo tako da so vsi manjsi elemeti levo in vsi vecji elementi desono
     *
     * @param tab tabela z elemeti ki jo bomo delo uredili
     * @param mesto index elementa ki bomo postavili na pravo mesto
     * @param kljuc kljuc po katerem se bo gledala urejenost tabele
     * @return vrne koncni index elementa ki je zacel an lokaciji mesto
     */
    public static int partNeDela(double[][] tab, int mesto, int kljuc){  // no ja .. mislm da zdej dela .. :P drgac pa part  100% dela
        return partNeDela(tab, 0, tab.length-1, mesto, kljuc);
    }
    /**
     * postavi element na indeksu mesto v njegovo urejeno pozicijo tako da so vsi manjsi elemeti levo in vsi vecji elementi desono, na intervalu od start do end
     *
     * @param tab tabela z elemeti ki jo bomo delo uredili
     * @param start index prvega elementa ki bomo upostevali
     * @param end index zadnjega elementa ki bomo upostevali
     * @param mesto index elementa ki bomo postavili na pravo mesto
     * @param kljuc kljuc po katerem se bo gledala urejenost tabele
     * @return vrne koncni index elementa ki je zacel an lokaciji mesto
     */
    public static int partNeDela(double[][] tab, int start, int end, int mesto, int kljuc){
        swap(tab, end, mesto);
        double pivot = tab[end][kljuc];
        int l=start;
        int r=end-1;
        while (l<=r ){
            while(tab[l][kljuc]<pivot ){l++;}
            while(r>=0 && pivot<tab[r][kljuc] ){r--;}
            if (l<=r){
                swap(tab,l,r);
                l++;
                r--;
            }
        }
        swap(tab,end,l);
        return l;
    }
    /**
     * razdeli tabelo na dva dela in vrne mesto delilnega elemnta k
     *
     * @param tab tabela ki se deli
     * @param mesto mesto na kjer zelimo delilni element
     * @param kljuc kljuc po katerem se bo tabela delila
     * @return vrne index delilsnega elementa (naceloma mora biti enak kot mesto)
     */
    public static int KthElement(double[][] tab, int mesto, int kljuc) { //dela
        return KthElement(tab, 0, tab.length-1, mesto, kljuc);
    }
    /**
     *
     * @param tab tabela ki se deli
     * @param start index prvega elementa
     * @param end index zadnjaga elementa
     * @param mesto mesto na kjer zelimo delilni element med start in end
     * @param kljuc kljuc po katerem se bo tabela delila
     * @return vrne index delilsnega elementa (naceloma mora biti enak kot mesto)
     */
    public static int KthElement(double[][] tab, int start, int end, int mesto, int kljuc) { //dela
        while (true){
            int novPivot = partNeDela(tab, start, end, mesto, kljuc);
            if (novPivot==mesto){
                return novPivot;
            }
            if (mesto < novPivot){
                end = novPivot-1;
            }else{
                start = novPivot+1;
            }
        }
    }

    /**
     * sortira del tabele
     * @param tab tabela za sortiranje
     * @param kljuc kljuc po katerm se sortira (drugi parameter v tab)
     */
    public static void quicksort(double[][] tab, int kljuc){  //dela
        quicksort(tab,0,tab.length-1,kljuc);
    }
    /**
     * sortira del tabele
     * @param tab tabela za sortiranje
     * @param start zacetni index ki naj bo sortiran
     * @param end zadnji index ki naj bo sortiran
     * @param kljuc kljuc po katerm se sortira (drugi parameter v tab)
     */
    public static void quicksort(double[][] tab, int start, int end, int kljuc) { //dela
        if(start<end){
            int l=start;
            int r=end;
            double pivot = tab[(start+end)/2][kljuc];
            while(l<=r){
                while(tab[l][kljuc]<pivot) {l++;}
                while(pivot<tab[r][kljuc]) {r--;}
                if(l<=r) swap(tab, l++, r--);
            }
            quicksort(tab, start, r, kljuc);
            quicksort(tab, l, end, kljuc);
        }
    }
    /**
     * izpise vektor v vrstico
     * <br>enako kot
     * <br>printVector(vektor, vektor.length);
     *
     * @param vektor vektor za izpis
     */
    public static void printVector(double[] vektor){
        if (vektor!= null){
            printVector(vektor, vektor.length);
        }
    }
    /**
     * izpise n elemntov vektorja v vrstico
     * @param vektor vektor za izpis
     * @param steviloElementov stevilo elementov ki se bojo izpisali
     */
    public static void printVector(double[] vektor, int steviloElementov){
        steviloElementov = steviloElementov<vektor.length? steviloElementov : vektor.length;
        for (int j = 0; j < steviloElementov; j++) {
//            System.out.print("          ".substring(((""+vektor[j]+"          ").substring(0, 7)).length())+(""+vektor[j]+"          ").substring(0, 7));
            System.out.print("                              ".substring(((""+vektor[j]+"                           ").substring(0, 25)).length())+(""+vektor[j]+"                                 ").substring(0, 25));
        }
        System.out.println();
    }
    /**
     * izpise tabelo vertikalno
     * <br>enako kot
     * <br>printVertical(t, t[0].length);
     *
     * @param t tabela za izpis
     */
    public static void printVertical(double[][] t){
        for (int i = 0; i < t.length; i++) {
            if (t[i]!=null){
                printVector(t[i]);
            }
        }
    }
    /**
     * izpise tabelo vertikalno
     * @param t tabela za izpis
     * @param steviloElementov stevilo stolpcev za izpis.
     */
    public static void printVertical(double[][] t, int steviloElementov){
        for (int i = 0; i < steviloElementov; i++) {
            printVector(t[i],steviloElementov);
        }
    }
    /**
     * izpise tabelo vertikalno med indeksi start in end
     * @param t tabela za izpis
     * @param start index prvega izpisanega elementa
     * @param end index zadnjega izpisanega elementa
     */
    public static void printVertical(double[][] t, int start, int end){
        for (int i = start; i <=end; i++) {
            printVector(t[i]);
        }
    }
    /**
     * izpise tabelo horizontalno
     * @param t tabela za izpis
     */
    public static void printHorizontal(double[][] t){
        for (int j = 0; j < t[0].length; j++) {
            for (int i = 0; i < t.length; i++) {
                System.out.print("          ".substring(((""+t[i][j]+"          ").substring(0, 7)).length())+(""+t[i][j]+"          ").substring(0, 7));
            }
            System.out.println();
        }
    }
    /**
     * normalizira tabelo po stolplcih
     *
     * @param tab tabela za normalizacijo
     */
    public static void normalizerajCols(double[][] tab){
        int rows = tab.length;
        int cols = tab[0].length;
        double[] min = tab[0].clone();
        double[] max = tab[0].clone();
        double[] razdalja = new double[min.length];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++) {
                if (min[j]>tab[i][j]){
                    min[j] = tab[i][j];
                }
                if (max[j]<tab[i][j]){
                    max[j] = tab[i][j];
                }
            }
        }
        for (int i = 0; i < razdalja.length; i++) {
            razdalja[i] = max[i]-min[i];
        }
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++) {
                tab[i][j] = (tab[i][j]-min[j])/razdalja[j];
            }
        }
    }
    /**
     * izracuna varianco tabele po stolpcih
     * <br><br><i>ne vrne prave vrednosti, le stevila so v pravem razmerju</i>
     *
     * @param tab tabele po kateri racunamo varianco
     * @return vektor izracunanih varianc
     */
    public static double[] varianca(double[][] tab){
        int rows = tab.length;
        int cols = tab[0].length;
        double[] avg = new double[cols];
        double[] var = new double[cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++) {
                avg[j] += tab[i][j];
            }
        }
        for (int j = 0; j < cols; j++) {
            avg[j] = avg[j]/(double)rows;
        }
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++) {
//                var[j] += Math.abs((tab[i][j]-avg[j]));      //bo treba sam pogledat ce se to zaporedje kej pozna :D
                var[j] += Math.pow((tab[i][j]-avg[j]),2.0);
            }
        }
//        for (int j = 0; j < cols; j++) { var[j] = var[j]/(double)rows; }  // razmerje velikosti bo enako, samo manj se racuna
        return var;
    }
    /**
     * vrne optimalno zaporedje za gradnjo KD drevesa na podlagi variance
     *
     * @param var tabela varianc podatkov
     * @param dim stevilo dimenzij ki jih zelimo imeti
     * @return zaporedje indeksov varianc po padojocih vrednostih
     */
    public static int[] getZaporedje(double[] var, int dim){
        int[] z = new int[dim];
        for (int i=0;i<dim; i++){
            z[getSteviloManjsih(var,var[i],dim)] = i;
        }
        return z;
    }
    /**
     * presteje stevilo od dolocene vrednosti manjsih elementov od zacetka do podane tocke. uporablja se za ugotavljanje zaporedja iz variance
     *
     * @param tab vektor stevil ki po katerm bomo steli (varianca)
     * @param val mejna vrednost do katere se steje manjse elemente
     * @param dim zadnji index do katerega stejemo
     * @return stevilo elementov manjsih od val na intervalu od 0 do dim
     */
    private static int getSteviloManjsih(double[] tab, double val, int dim){
        try {
            int stevec = 0;
            for (int i=0;i<dim; i++){
               if (tab[i]>val){
                   stevec++;
               }
            }
            return stevec;
        }catch (IndexOutOfBoundsException e){
            System.out.println("preveliko stevilo dimenzij: \n"+e.toString());
            System.exit(1);
            return -1;
        }
    }
    /**
     * izpise integer vector v eno vrstico
     * za izpisovanje stevil do 10e6
     *
     * @param   t   vektor celih stevil za izpis
     */
    public static void printIntVector(int[] t){
        System.out.println();
        for (int i = 0; i < t.length; i++) {
            System.out.print((""+t[i]+"                      ").substring(0, 5)+" ");
        }
        System.out.println();
    }
    /**
     * isce najmanjsi element
     * @param tab tabela po kateri isce
     * @param kljuc kljuc ki gleda za primerjavo
     * @return kopijo najmanjsega elementa
     */
    public static double[] getMin(double[][] tab, int kljuc){
        return getMin(tab, 0, tab.length-1, kljuc);
    }
    /**
     * isce najmanjsi element
     * @param tab tabela po kateri isce
     * @param start zacetni index intervala
     * @param end koncni index intervala
     * @param kljuc kljuc ki gleda za primerjavo
     * @return kopijo najmanjsega elementa ki je na intervalu od start do end
     */
    public static double[] getMin(double[][] tab, int start, int end, int kljuc){
        int minIndex = start;
        double minValue = tab[minIndex][kljuc];
        for (int i = start; i<=end ; i++){
            if(minValue>tab[i][kljuc]){
                minIndex=i;
                minValue = tab[minIndex][kljuc];
            }
        }
        return tab[minIndex].clone();
    }
    /**
     * isce najmanjsi element
     * @param tab tabela po kateri isce
     * @param start zacetni index intervala
     * @param end koncni index intervala
     * @param kljuc kljuc ki gleda za primerjavo
     * @return index najmanjsega elementa na intervalu od start do end
     */
    public static int getMinIndex(double[][] tab, int start, int end, int kljuc){
        int minIndex = start;
        double minValue = tab[minIndex][kljuc];
        for (int i = start; i<=end ; i++){
            if(tab[i]!= null && minValue>tab[i][kljuc]){
                minIndex=i;
                minValue = tab[minIndex][kljuc];
            }
        }
        return minIndex;
    }
    /**
     * isce najvecji element
     * @param tab tabela po kateri isce
     * @param kljuc kljuc ki gleda za primerjavo
     * @return kopijo najvecjega elementa
     */
    public static double[] getMax(double[][] tab, int kljuc){
        return getMax(tab, 0, tab.length-1, kljuc);
    }
    /**
     * isce najvecji element
     * @param tab tabela po kateri isce
     * @param start zacetni index intervala
     * @param end koncni index intervala
     * @param kljuc kljuc ki gleda za primerjavo
     * @return kopijo najvecjega elementa ki je na intervalu od start do end
     */
    public static double[] getMax(double[][] tab, int start, int end, int kljuc){
        int maxIndex = start;
        double maxValue = tab[maxIndex][kljuc];
        for (int i = start; i<=end ; i++){
            if(maxValue<tab[i][kljuc]){
                maxIndex=i;
                maxValue = tab[maxIndex][kljuc];
            }
        }
        return tab[maxIndex].clone();
    }
    /**
     * isce najvecji element
     * @param tab tabela po kateri isce
     * @param start zacetni index intervala
     * @param end koncni index intervala
     * @param kljuc kljuc ki gleda za primerjavo
     * @return index najvecjega elementa na intervalu od start do end
     */
    public static int getMaxIndex(double[][] tab, int start, int end, int kljuc){
        int maxIndex = start;
        double maxValue = tab[maxIndex][kljuc];
        for (int i = start; i<=end ; i++){
            if(tab[i]!= null && maxValue<tab[i][kljuc]){
                maxIndex=i;
                maxValue = tab[maxIndex][kljuc];
            }
        }
        return maxIndex;
    }
    public static double[][] getMaxPoints(double[][] tab, int start, int end, int stDimenzij){
        stDimenzij = tab[0].length<stDimenzij? tab[0].length : stDimenzij; // da ne gremo cez rob tabele
        stDimenzij = (end-start+1)<stDimenzij? (end-start+1) : stDimenzij; // da ne gremo cez rob tabele
        double[][] maxPoints = new double[stDimenzij][];
        for (int i=0; i<stDimenzij ; i++){ //za vsako dimenziji najdemo extremno tocko
            int maxIndex = getMaxIndex(tab, start, end-i, i); //-i je da zadnjega elementa ne iscemo
            maxPoints[i] = tab[maxIndex].clone(); //shranimo to extremno tocko v tabelo
            swap(tab, maxIndex, end-i); //najvecji element umaknemo
        }

        return maxPoints;
    }
    public static double[][] getMinPoints(double[][] tab, int start, int end, int stDimenzij){
        stDimenzij = tab[0].length<stDimenzij? tab[0].length : stDimenzij; // da ne gremo cez rob tabele
        stDimenzij = (end-start+1)<stDimenzij? (end-start+1) : stDimenzij; // da ne gremo cez rob tabele
        double[][] minPoints = new double[stDimenzij][];
        for (int i=0; i<stDimenzij ; i++){ //za vsako dimenziji najdemo extremno tocko
            int minIndex = getMinIndex(tab, start, end-i, i); //-i je da zadnjega elementa ne iscemo
            minPoints[i] = tab[minIndex].clone(); //shranimo to extremno tocko v tabelo
            swap(tab, minIndex, end-i); //najvecji element umaknemo
        }
        return minPoints;
    }

    public static double[][] trimNull(double[][] tab){
        int i=tab.length-1;
        while(tab[i]==null && i>0){i--;}
        double[][] n = new double[i+1][];
        for(int j=0; j<=i; j++){
            n[j]=tab[j];
        }
        return n;
    }

    public static double[][] mergeTables(double[][] a, double[][] b){
        int al = a.length;
        int bl = b.length;
        int ab = al+bl;
        double[][] merge = new double[al+bl][];
        int stevec =0;
        while (stevec<al){
            merge[stevec] = a[stevec];
            stevec++;
        }

        while (stevec<ab){
            merge[stevec] = b[stevec-al];
            stevec++;
        }
        return merge;
    }

    static boolean isVectorInBox(double[][] boundingBox, double[] vektor,int stDimenzij) {
        for(int i=0; i<stDimenzij; i++){
//            System.out.println("v= "+vektor[i]+"   min= "+getMinElement(boundingBox, i));
//            System.out.println("v= "+vektor[i]+"   max= "+getMaxElement(boundingBox, i));
            if (vektor[i]<getMinElement(boundingBox, i) || vektor[i]>getMaxElement(boundingBox, i)){
                return false;
            }
        }
        return true;
    }


    private static double getMinElement(double[][] boundingBox, int kljuc) {
        return boundingBox[getMinIndex(boundingBox, kljuc)][kljuc];
    }

    private static double getMaxElement(double[][] boundingBox, int kljuc) {
        return boundingBox[getMaxIndex(boundingBox, kljuc)][kljuc];
    }

    private static int getMaxIndex(double[][] boundingBox, int kljuc) {
        return getMaxIndex(boundingBox, 0, boundingBox.length-1, kljuc);
    }
    private static int getMinIndex(double[][] tab, int kljuc) {
        return getMinIndex(tab, 0, tab.length-1, kljuc);
    }

    static double getBoxDistance(double[][] boundingBox, double[] vektor, int stDimenzij) {
        double[] nearestEdge = vektor.clone();
        double t;
        for (int i=0; i<stDimenzij; i++){
            if ((t = getMinElement(boundingBox, i)) > vektor[i]){
                nearestEdge[i] = t;
            }else if ((t= getMaxElement(boundingBox, i))< vektor[i]){
                nearestEdge[i] = t;
            }// drugace je ta dimenzija enaka in jo pustimo pri miru
        }
        return distSqare(nearestEdge, vektor, stDimenzij);
    }
    static double[] getNearestBoxEdge(double[][] boundingBox, double[] vektor, int stDimenzij) {
        double[] nearestEdge = vektor.clone();
        double t;
        for (int i=0; i<stDimenzij; i++){
            if ((t = getMinElement(boundingBox, i)) > vektor[i]){
                nearestEdge[i] = t;
            }else if ((t= getMaxElement(boundingBox, i))< vektor[i]){
                nearestEdge[i] = t;
            }// drugace je ta dimenzija enaka in jo pustimo pri miru
        }
        return nearestEdge;
    }

}

