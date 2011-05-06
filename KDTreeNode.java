package Seminarska;

/**
 * <p> Programje za APS1 knjigo</p>
 * <p> Vozlisce k-d drevesa </p>
 * <p> Copyright (c) 2003</p>
 * @author Marko Robnik �ikonja
 * @version 1.0
 * @see fri_aps1.KDTree
 */

public class KDTreeNode {
 Comparable keys[] ; //  array [0..k-1]
 KDTreeNode left, right ;

 public KDTreeNode() {
   keys = null ;
   left = right = null ;
 }
 public KDTreeNode(int k) {
   keys = new Comparable[k] ;
   left = right = null ;
}
}