package Seminarska;

/**
 * <p> vozlisce k-d drevesa za iskanje bli�njih sosedov </p>
 * <p> Programje za APS1 knjigo</p>
 * <p> Copyright (c) 2003</p>
 * @author Marko Robnik �ikonja
 * @version 1.0
 * @see fri_aps1.KDnnTree
 */

public class KDnnTreeNode {
  int splitD ; //  split dimension
  int eltIndex ; // index of element
  KDnnTreeNode left, right ;

  public KDnnTreeNode() {
    splitD = -1 ;
    eltIndex = -1 ;
    left = right = null ;
  }
}