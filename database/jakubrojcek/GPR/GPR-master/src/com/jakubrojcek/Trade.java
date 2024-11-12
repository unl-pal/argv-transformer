package com.jakubrojcek;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub
 * Date: 16.3.12
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
public class Trade {
    private int buyerID;            // trader ID
    private double buyerPV;         // private values
    private boolean buyerIsHFT;     // isHFT
    private int sellerID;
    private boolean sellerIsHFT;
    private double sellerPV;
    private double timeSeller;
    private double timeBuyer;
    private double timeTrade;
    private double FVbeforeBuyer;
    private double FVbeforeSeller;
    private double price;
    private double FV;                      // fundamental value
    private double trCostsBuyer = 0.0;      // buyer's transaction costs
    private double trCostsSeller = 0.0;     // seller's transaction costs
}



