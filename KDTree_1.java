package Seminarska;

/**
 * <p> K-D drevo</p>
 * <p> Programje za APS1 knjigo</p>
 * <p> Copyright (c) 2003</p>
 * @author Marko Robnik �ikonja
 * @version 1.0
 * @see fri_aps1.KDTreeNode
 */

public class KDTree_1 {
  KDTreeNode rootNode ;
  int k ;

  public KDTree_1() {
    rootNode = null ;
  }

  public void printRange(Comparable low[], Comparable high[],
                    KDTreeNode root, int level) {
    // Low[i]..High[i] = interval i-tega kljuca
    //  level = nivo trenutnega vozlisca  mod k
   if (root != null)  {
     boolean inRange = true;
     for (int i = 0; i < k; i++)
       if (root.keys[i].compareTo(low[i])<0 || root.keys[i].compareTo(high[i])>0) {
         inRange = false;
         break;
       }
     if (inRange) System.out.print(root.keys);

     if (low[level].compareTo(root.keys[level]) <=0)
       printRange(low, high, root.left, (level+1) % k);

     if (high[level].compareTo(root.keys[level])>=0)
       printRange(low, high, root.right, (level+1) % k);
   }
}

  public static void main(String[] args) {
    KDTree tree = new KDTree();
  }

}