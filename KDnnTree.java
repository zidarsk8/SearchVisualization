package Seminarska;

/**
 * <p> K-D drevo za iskanje bli�njih sosedov </p>
 * <p> Programje za APS1 knjigo</p>
 * <p> Copyright (c) 2003</p>
 * @author Marko Robnik �ikonja
 * @version 1.0
 * @see fri_aps1.KDnnTreeNode
 * @see fri_aps1.KDTree
 */

public class KDnnTree {
  static final double PRECISION = 1e-12 ;
  KDnnTreeNode rootNode ;
  double data[][] ;
  int k ;

  public KDnnTree(double dta[][]) {
    rootNode = null ;
    data = dta ;
    k = data[0].length ;
  }

  public void kdConstruct() {
     // pripravi primere za koren
     int exSet[] = new int[data.length] ;
     for (int i=0 ; i < data.length; i++)
        exSet[i] = i ;
      // rekurzivna konstrukcija
      rootNode = kdConstruct(exSet, data.length) ;
  }

  class SortVal implements Comparable {
    double key ;
    int val ;
    public int compareTo(Object x) {
       return Double.compare(key, ((SortVal)x).key) ;
    }
  }

  public KDnnTreeNode kdConstruct(int exSet[], int n) {
     // vhod: mnozica primerov exSet velikosti n
     // zaradi enostavnosti racunamo razbitje na nenormaliziranih podatkih
     if (n == 0) // prazna mnozica primerov
       return null ;
     else if (n == 1) {
       KDnnTreeNode node = new KDnnTreeNode() ;
       node.eltIndex = exSet[0] ;
       return node ;
     }
     else {
       KDnnTreeNode node = new KDnnTreeNode();
       int i, j ;
       // izracunamo spodnjo in zgornjo mejo primerov v vsaki dimenziji za za normalizacijo
       double low[] = new double[k] ;
       double high[] = new double[k] ;
       double range[] = new double[k] ;
       for (j = 0; j < k; j++) {
         low[j] = high[j] = data[exSet[0]][j] ;
         for (i = 1; i < n; i++)
            if (data[exSet[i]][j] < low[j])
               low[j] = data[exSet[i]][j] ;
            else if (data[exSet[i]][j] > high[j])
              high[j] = data[exSet[i]][j] ;
         range[j] = high[j] - low[j] ;
       }
       // poiscemo primer in dimenzijo za razbitje
       double avg, sq, var, val;
       double maxVar = -1;
       // poiscemo dimenzijo z maksimalno varianco
       // primere normaliziramo na [0,1]
       for (j = 0; j < k; j++) {
         if (range[j] == 0.0) // ni razlik v tej dimenziji
           continue ;
         avg = sq = 0.0;
         for (i = 0; i < n; i++) {
           val = (data[exSet[i]][j] - low[j])/range[j] ;
           avg += val;
           sq += val * val ;
         }
         avg /= n;
         var = sq / n - avg * avg;
         if (var > maxVar) {
           maxVar = var;
           node.splitD = j;
         }
       }
       if (maxVar < PRECISION ) {
         // sami enaki elementi, shranimo samo enega
         node.eltIndex = exSet[0] ;
         return node ;
       }
       // element za razbitje naj ima vrednost mediane v izbrani dimenziji
       SortVal sortArray[] = new SortVal[n];
       for (i = 0; i < n; i++) {
         sortArray[i] = new SortVal();
         sortArray[i].key = data[exSet[i]][node.splitD];
         sortArray[i].val = exSet[i];
       }
       node.eltIndex = select(n / 2, sortArray).val ;

       // primeri za levo in desno poddrevo
       int exSetLeft[] = new int[n];
       int exSetRight[] = new int[n];
       int nLeft = 0, nRight = 0;
       for (i = 0; i < n; i++)
         if (data[exSet[i]][node.splitD] < data[node.eltIndex][node.splitD])
           exSetLeft[nLeft++] = exSet[i];
         else
           exSetRight[nRight++] = exSet[i];

      // konstrukcija levega in desnega
       node.left = kdConstruct(exSetLeft, nLeft);
       node.right = kdConstruct(exSetRight, nRight);
       return node;
     }
  }

  class NearPoint {
    int nearIdx ;
    double distSq ;
  }

  public int nnFind(double q[]) {
    // vrne indeks najbljizjega primera tocke q
    // predstavitev hiperpravokotnika
    double hrMin[] = new double[k] ;
    double hrMax[] = new double[k] ;
    for (int i=0 ; i < k ; i++) {
      hrMin[i] = -Double.MAX_VALUE;
      hrMax[i] = Double.MAX_VALUE;
    }
    return nnFind(rootNode, q, hrMin, hrMax, Double.POSITIVE_INFINITY).nearIdx;
  }

  private NearPoint nnFind(KDnnTreeNode node, double q[], double hrMin[], double hrMax[], double maxDistSq) {
    // v poddrevesu, ki ga doloca node in vsebuje tocke s koordinatani med hrMin in hrMax,
    // iscemo tocko, ki je najblizja q in je oddaljena manj kot maxDistSq
    NearPoint nearest ;
    if (node == null) {
       nearest = new NearPoint() ;
       nearest.distSq = Double.POSITIVE_INFINITY ;
       nearest.nearIdx =  -1 ;
       return nearest ;
     }
     else if (node.left == null && node.right == null) {
       // leaf
       nearest = new NearPoint();
       nearest.distSq = distEsq(q, data[node.eltIndex]);
       nearest.nearIdx = node.eltIndex;
       return nearest;
     }
     else {
       // glede na razbitje sestavimo levi in desni hiperpravokotnik
       double hrMinLeft[] = (double[]) hrMin.clone();
       double hrMaxLeft[] = (double[]) hrMax.clone();
       double hrMinRight[] = (double[]) hrMin.clone();
       double hrMaxRight[] = (double[]) hrMax.clone();
       hrMaxLeft[node.splitD] = hrMinRight[node.splitD] = data[node.eltIndex][
           node.splitD];
       boolean qIsLeft = q[node.splitD] < data[node.eltIndex][node.splitD];
       // set nearer and farther subtree
       double hrMinNear[], hrMaxNear[], hrMinFar[], hrMaxFar[];
       KDnnTreeNode kdNear, kdFar;
       if (qIsLeft) {
         hrMinNear = hrMinLeft;
         hrMaxNear = hrMaxLeft;
         kdNear = node.left;
         hrMinFar = hrMinRight;
         hrMaxFar = hrMaxRight;
         kdFar = node.right;
       }
       else {
         hrMinNear = hrMinRight;
         hrMaxNear = hrMaxRight;
         kdNear = node.right;
         hrMinFar = hrMinLeft;
         hrMaxFar = hrMaxLeft;
         kdFar = node.left;
       }
       nearest = nnFind(kdNear, q, hrMinNear, hrMaxNear, maxDistSq);
       maxDistSq = Math.min(maxDistSq, nearest.distSq);
       // ce kaksen del kdFar lezi znotraj hiperkrogle na
       // razdalji sqrt(maxDistSq), gremo iskat tudi v kdFar sicer ne
       // poiscemo najblizjo tocko v kdFar
       int i;
       double pClose[] = new double[k];
       for (i = 0; i < k; i++)
         if (q[i] < hrMinFar[i])
           pClose[i] = hrMinFar[i];
         else if (q[i] > hrMaxFar[i])
           pClose[i] = hrMaxFar[i];
         else
           pClose[i] = q[i];
       if (distEsq(q, pClose) < maxDistSq) {
         double pivotDist = distEsq(q, data[node.eltIndex]);
         if (pivotDist < nearest.distSq) {
           nearest.nearIdx = node.eltIndex;
           nearest.distSq = maxDistSq = pivotDist;
         }
         NearPoint farNearest = nnFind(kdFar, q, hrMinFar, hrMaxFar, maxDistSq);
         if (farNearest.distSq < nearest.distSq)
           nearest = farNearest;
       }
       return nearest;
     }
  }

  public static double distEsq (double p1[], double p2[]) {
    TabFun.stevecRacunanjRazadlj++;
    double distSq = 0.0, t ;
    for (int i=0 ; i < p1.length ; i++) {
      t =  p1[i]-p2[i] ;
      distSq += t * t ;
    }
    return distSq ;
  }

  public static int nnNaive(double q[], double data[][]) {
    double sqDist, minSqDist = Double.MAX_VALUE ;
    int minIdx = -1 ;
    for (int i=0 ; i < data.length; i++){
       sqDist = distEsq(q, data[i]) ;
       if (sqDist < minSqDist){
         minSqDist = sqDist;
         minIdx = i ;
       }
    }
    return minIdx ;
  }

  SortVal select(int s, SortVal table[]) {
     // izbere s-ti po vrsti element iz polja table in
     // preuredi polje tako, da so manjsi pred s-tim in vecji za njim
     int i,j,mid, left=0, right=table.length-1;
     SortVal t ;
     while (true) {
       if (right <= left+1) {
          if (right == left+1 && table[right].compareTo(table[left]) < 0) {
             // dva elementa, zamenjamo
             t = table[left]; table[left] = table[right]; table[right] = t ;
          }
          return table[s];
        }
        else {
          // za razbitje izberemo mediano elementov left, right in mid ter
          // preuredimo tabelo tako, da table[left+1] <= table[left], table[right] >= table[left]
          mid=(left+right) / 2;
          t = table[mid]; table[mid] = table[left+1]; table[left+1] = t ;
          if (table[left+1].compareTo(table[right]) > 0) {
             t = table[left+1]; table[left+1] = table[right]; table[right] = t ;
          }
          if (table[left].compareTo(table[right]) > 0) {
             t = table[left]; table[left] = table[right]; table[right] = t ;
          }
          if (table[left+1].compareTo(table[left]) > 0) {
             t = table[left+1]; table[left+1] = table[left]; table[left] = t ;
          }
          // pripravimo se na delitev
          // delitveni element je table[left]
          i=left+1;
          j=right;
          while (true) {
             do i++; while (table[i].compareTo(table[left]) < 0) ;  // navzgor iscemo vecjega od delilnega
             do j--; while (table[j].compareTo(table[left]) > 0) ;  // navzdol iscemo manjsega od delilnega
             if (j < i)  //  razbijanje koncano
                break;
             // zamenjava
             t = table[i] ; table[i] = table[j]; table[j] = t ;
          }
          // vstavimo delitveni element
          t = table[left] ; table[left] = table[j]; table[j] = t ;
          // particija, ki vsebuje s-ti element ostane aktivna
          if (j >= s)
             right=j-1;
          if (j <= s)
             left=i;
        }
      }
  }

  public static void main(String[] args) {
    int k = 5 ;
    double data[][] = new double[100][k];
    int i, j ;
    for (i=0 ; i < data.length ; i++)
      for(j=0 ; j < k ; j++)
        data[i][j] = Math.random() ;
    KDnnTree nnTree = new KDnnTree(data);
    nnTree.kdConstruct();
    double q[] = new double[k] ;
    System.out.println("Nearest point (naive) :" + nnNaive(q, data));
    System.out.println("Nearest point (kdTree):" + nnTree.nnFind(q));
  }

}