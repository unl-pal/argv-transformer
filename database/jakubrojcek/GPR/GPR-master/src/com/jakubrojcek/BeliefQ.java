package com.jakubrojcek;

/**
 * Created with IntelliJ IDEA.
 * User: rojcek
 * Date: 27.09.13
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */
public class BeliefQ implements java.io.Serializable {
    private int n = 0;                  // number of times updated
    private int ne = 0;                 // number of times executed
    private double q;                   // execution probability or Q factor in continuous time
    private double diff;                // difference of new vs old execution probability
    private double nC;                  // expected number of cancellations
}
