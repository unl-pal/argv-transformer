package com.jakubrojcek;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub
 * Date: 9.3.12
 * Time: 11:38
 * To change this template use File | Settings | File Templates.
 */
public class Order {
    private int orderID;
    private int traderID;
    private double timeStamp;
    private boolean buyOrder;
    private boolean firstShare;
    private boolean cancelled = false;
    private int action;
    private int q;                      // priority in the queue
    private int size;
    private int position;
}
