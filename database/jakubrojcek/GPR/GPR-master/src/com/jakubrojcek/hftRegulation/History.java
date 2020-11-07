package com.jakubrojcek.hftRegulation;

import com.jakubrojcek.Order;
import com.jakubrojcek.Trade;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub
 * Date: 16.3.12
 * Time: 11:16
 * To change this template use File | Settings | File Templates.
 */
public class History {
    HashMap<Integer, Trader> traders;
    ArrayList<Integer> Bids;
    ArrayList<Integer> Asks;
    ArrayList<Integer> Events;
    ArrayList<Integer> States;
    ArrayList<Double> EffSpread;
    ArrayList<Integer> QuotedSpread;
    int[] depths;                       // count, lBt, lAt, dBt, dSt
    Vector<Trade> history;
    String folder;
    double TTAX = 0.0f;
    double CFEE = 0.0f;
    double MFEE = 0.0f;
    double TFEE = 0.0f;




}
