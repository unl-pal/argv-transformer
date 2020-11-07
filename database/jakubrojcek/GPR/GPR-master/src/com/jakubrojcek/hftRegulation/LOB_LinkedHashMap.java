package com.jakubrojcek.hftRegulation;

import com.jakubrojcek.Order;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: Jakub
 * Date: 6.3.12
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */
public class LOB_LinkedHashMap {
    int model;                              // model of simulation, e.g. "returning" = 0, "speedBump" = 1
    double[] Prices;                        // prices for trading
    private int[] BookSizes;                // signed sizes of the book
    private int[] BookInfo;                 // info used in decision making
    private int positionShift;              // ++ if FVup, -- if FVdown
    double FV;                              // Fundamental value
    double tickSize;                        // size of one tick
    byte nPoints;                           // number of available positions
    int maxDepth;                           // 0 to 7 which matter
    private int Pt = 0;                     // last transaction position
    private int b = 0;                      // 1 if last transaction buy, 0 if sell
    private int OrderID = 0;                // id stamp for orders used as key in the book LHM
    ArrayList<Integer> traderIDsHFT = new ArrayList<Integer>();
    ArrayList<Integer> traderIDsNonHFT = new ArrayList<Integer>();
    //vectors holding traderIDs, HFT or nonHFT, traderID and position, price and position
    ArrayList<Order> ActiveOrders = new ArrayList<Order>();
    // holds orders which are in the book, remove from book: order.getPosition
    Hashtable<Integer, HashMap<Integer, Integer>> CurrentPosition =
            new Hashtable<Integer, HashMap<Integer, Integer>>();
    // traderID, position, signed number of orders at position: price + positionShift


    LinkedHashMap<Integer, Order>[] book; // orderID and orders
    History hist;
    HashMap<Integer, Trader> traders;
}
