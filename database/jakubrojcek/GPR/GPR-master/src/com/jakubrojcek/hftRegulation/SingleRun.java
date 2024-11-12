package com.jakubrojcek.hftRegulation;

import com.jakubrojcek.Order;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jakub
 * Date: 15.10.12
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 * Class SingleRun is operating (1) new trader (2) & (3) decision and transaction rule (4) cancellations
 * (5) innovation of fundamental value for the "normal", "GPR2005" and "GPR2005C" model based on current
 * specification of run parameters for specific number of iterations (events).
 * I, main class loads parameters at initialization of SingleRun
 * II, run specific parameters are load together with number of events
 * III, iterations are computed
 * IV, EventTime and FundamentalValue are returned
 */
public class SingleRun {

    int model;                          // model of simulation, e.g. "returning" = 0, "speedBump" = 1

    HashMap<Integer, Integer> tifCounts = new HashMap<Integer, Integer>(6);     // 5- HFT, rest 0, 1, etc is according to trader.getPv
    HashMap<Integer, Double> tifTimes = new HashMap<Integer, Double>(6);
    HashMap<Integer, Integer> population = new HashMap<Integer, Integer>(6);


    double lambdaArrival;
    double lambdaFVchange;
    double ReturnFrequencyHFT;
    double ReturnFrequencyNonHFT;
    double tif;                                 // time in force
    double speedBump;                           // speed bump length
    double infoDelay;                           // information delay of uninformed traders
    double transparencyPeriod;                  // period for transparency
    double lastUpdateTime;                      // last time the BookInfo and BookSize were updated
    double sigma;
    float tickSize;
    double[] FprivateValues;
    double[] DistributionPV;
    double FVplus;
    double FvLag;                               // lagged fundamental value, knowledge of uninformed traders
    int ReturningHFT = 0;
    int ReturningNonHFT = 0;
    double convergenceStat = 0.0;
    ArrayList<Integer> traderIDsHFT;            // holder for IDs of HFT traders
    ArrayList<Integer> traderIDsNonHFT;         // holder for IDs of nonHFT traders
    TreeMap<Double, Integer> waitingTraders;    // waiting traders, key is time, value is trader's ID
    TreeMap<Double, Double> fundamentalValue;   // lagged fundamental value, key is the change in FV time
    HashMap<Integer, Trader> traders;
    History h;
    Trader trader;
    LOB_LinkedHashMap book;
    Order heldOrder;                            // held market order in speedBump case
    int[] bi;                                   // book info holder
    int[] BookSizes;                            // signed sizes of the book
    int[] BookInfo;                             // info used in decision making
    int end;                                    // number of actions

    boolean header = false;
    String outputNameTransactions; // output file name
    String outputNameBookData; // output file name
    String outputNameStatsData; // output file name

    boolean write = false; // writeDecisions output in this SingleRun?
    boolean writeDiagnostics = false; // write diagnostics
    boolean writeHistogram = false; // write histogram
    boolean purge = false; // purge SingleRun?
    boolean nReset = false; // reset SingleRun?

    double Lambda;


    private void writeWrite (){
        if (writeHistogram){
            trader.writeHistogram(book.getBookSizes());
        }
        bi = book.getBookInfo();
        h.addDepth(bi);
        h.addQuotedSpread(bi[1] - bi[0]);
        if (write){
            Iterator keys = waitingTraders.values().iterator();         // counting guys in the waiting queue
            Integer key;
            while (keys.hasNext()){
                key = (Integer) keys.next();
                if (traders.containsKey(key)){
                    if (traders.get(key).getIsHFT()){
                        int x = tifCounts.get(5);
                        tifCounts.put(5, x + 1);
                    } else {
                        int pv = traders.get(key).getPv();
                        int x = tifCounts.get(pv);
                        tifCounts.put(pv, x + 1);
                    }
                }
            }
            int y = population.get(5);
            population.put(5, y + ReturningHFT);
            keys = traderIDsNonHFT.iterator();
            while (keys.hasNext()){
                key = (Integer) keys.next();
                int pv = traders.get(key).getPv();
                int x = population.get(pv);
                population.put(pv, x + 1);
            }

        }
    }

}
