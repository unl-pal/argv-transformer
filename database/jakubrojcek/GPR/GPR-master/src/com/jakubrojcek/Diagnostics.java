package com.jakubrojcek;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: rojcek
 * Date: 30.11.12
 * Time: 17:55
 * To change this template use File | Settings | File Templates.
 */
public class Diagnostics {

    private byte nP;
    private int e;
    double diff;
    double diffFV;
    double expDiffFV;
    int[] cancelCount;
    double[] expCancCount;
    int[] countC;
    int count;
    int countFV;
    HashMap<Short, Integer> actions;
    HashMap<Short, Double> payoffs;
    HashMap<Short, Integer> tickDiff;
    HashMap<Short, Double> learnDiff;
}
