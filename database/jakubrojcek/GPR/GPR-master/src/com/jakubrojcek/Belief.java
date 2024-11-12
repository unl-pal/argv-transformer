package com.jakubrojcek;

/**
 * Created with IntelliJ IDEA.
 * User: rojcek
 * Date: 21.02.13
 * Time: 19:34
 * To change this template use File | Settings | File Templates.
 */
public class Belief {
    private int n = 0;                // number of times updated
    private int ne;               // number of times executed
    private double mu;               // execution probability
    private double deltaV;           // expected change in FV upon execution
    private double diff;             // difference of new vs old execution probability
}
