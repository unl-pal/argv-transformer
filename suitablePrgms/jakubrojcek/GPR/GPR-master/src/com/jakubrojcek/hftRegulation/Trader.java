package com.jakubrojcek.hftRegulation;

import com.jakubrojcek.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

import com.jakubrojcek.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub
 * Date: 22.3.12
 * Time: 19:24
 * To change this template use File | Settings | File Templates.
 * class trader serves as (i) parameter container for individual traders (ii) individual trader object
 */
public class Trader {
    private int traderID;               // initialized
    private float privateValue;         // initialized, can be changed to double later on
    private int pv;                     // holder for private value 0(0), 1(negative), 2(positive)
    private boolean isHFT;              // initialized
    private boolean isInformed;         // initialized if the trader is informed or not
    private boolean isTraded = false;   // set by TransactionRule in book
    private boolean isReturning = false;// is he returning this time? info from priorities
    private double PriceFV;             // current fundamental value-> price at middle position
    private double EventTime = 0.0;     // event time
    private BeliefQ belief;             // reference to an old Belief, to be updated
    private double BeliefnC = 0.0;      // reference to an old Belief, to be updated
    private Order order = null;         // reference to an old Order, to update the old Belief
    private short cancelCount = 0;      // how many times cancelled

    static int model;                   // model of simulation, e.g. "returning" = 0, "speedBump" = 1
    static int TraderCount = 0;         // counting number of traders, gives traderID as well
    static int TraderCountHFT = 0;      // counting HFT traders
    static int TraderCountNonHFT = 0;   // counting nonHFT traders
    static int tradeCount = 0;          // counting number of trader
    static int statesCount = 0;         // counting number of unique states
    static double [] FprivateValues;
    static HashMap<Long, HashMap<Integer, BeliefQ>> states;/* Beliefs about payoffs for different actions
+ max + maxIndex in state=code */
    static HashMap<Long, HashMap<Integer, BeliefQ>> previousStates;    // used to compute convergence type 1
    static HashMap<Long, HashMap<Integer, BeliefQ>> convergenceStates = new HashMap<Long, HashMap<Integer, BeliefQ>>();
    // used to compute convergence type 2
    static ArrayList<Integer> updatedTraders = new ArrayList<Integer>();// list of traders already updated their one-step-ahead beliefs
    static previousStates statesConstructor;    // copy constructor for previous states
    static HashMap<Integer, BeliefQ> tempQs;
    static LOB_LinkedHashMap book;              // reference to the book
    static double ReturnFrequencyHFT;           // returning frequency of HFT
    static double ReturnFrequencyNonHFT;        // returning frequency of NonHFT
    static Hashtable<Integer, Double[]> discountFactorB = new Hashtable<Integer, Double[]>(); // container for discount factors computed using tauB
    static Hashtable<Integer, Double[]> discountFactorS = new Hashtable<Integer, Double[]>(); // container for discount factors computed using tauS
    static double[] numberOfCancels;            // initial beliefs for number of cancellations
    static double[] slippageS;                  // initial beliefs for slippage of SMO in case of speed bumps
    static double[] slippageB;                  // initial beliefs for slippage of BMO in case of speed bumps
    static HashMap<Integer, BeliefD> Deltas = new HashMap<Integer, BeliefD>();  // differences between actual fundamental value and expected fv
    static Random random = new Random();        // random generator, used for trembling
    static int infoSize;                        // 2-bid, ask, 4-last price, direction, 6-depth at bid,ask, 8-depth off bid,ask
    static byte nP;                             // number of prices
    static double tickSize;                     // size of one tick
    static int LL;                              // Lowest allowed limit order price. LL + HL = nP-1 for allowed orders centered around E(v)
    static int HL;                              // Highest allowed limit order price
    static int end;                             // HL - LL + 1 - number of LO prices on the grid
    static int maxDepth;                        // 0 to 7 which matter
    static int breakPoint;                      // breaking point for positive, negative, represents FV position on the LO grid
    static int nPayoffs;                        // size of payoff vectors
    static int fvPos;                           // tick of fundamental value
    static int nResetMax = 1000;               // nReset-> resets n to specific value for "forced learning" 32767 is max short //
    static double prTremble = 0.0;              // probability of trembling
    static boolean writeDecisions = false;      // to writeDecisions things in trader?
    static boolean writeDiagnostics = false;    // to writeDiagnostics things in trader?
    static boolean writeHistogram = false;      // to writeHistogram in trader?
    static boolean online = false;              // updates beliefs also for returning trader
    static String folder;
    static Decision decision;                   // decisions of all nonHFT and liquidity provision
    static Decision decisionTID0;                   // decisions of all nonHFT and liquidity provision
    static Decision decisionTID7;                   // decisions of all nonHFT and liquidity provision
    static Decision decision_4;                 // decisions of PV -4
    static Decision decision_2;                 // decisions of PV -2
    static Decision decision0;                  // decisions of PV 0
    static Decision decision2;                  // decisions of PV +2
    static Decision decision4;                  // decisions of PV +4
    static Decision decisionHFT;                // decisions of HFTs
    static Decision decisionHFT0;                // decisions of HFTs
    static Decision decisionHFT1;                // decisions of HFTs
    static Decision decisionHFT2;                // decisions of HFTs
    static Decision decisionHFT3;                // decisions of HFTs
    static Decision decisionHFT4;                // decisions of HFTs
    static Decision decisionHFT5;                // decisions of HFTs
    static Decision decisionHFT6;                // decisions of HFTs
    static Decision decisionHFT7;                // decisions of HFTs


    static Diagnostics diag;
    static int[] bookSizesHistory;
    static int[] previousTraderAction;
    static ArrayList<Order> orders;             // result of decision, passed to LOB.transactionRule
    static HashMap<Short, Float> ps;
    static boolean fixedBeliefs = false;        // when true, doesn't update, holds beliefs fixed
    static boolean similar = false;             // looks for similar state|action if action not in current state
    static boolean symm = false;                // uses/updates/stores seller's beliefs for buyer
    static double CFEE = 0.0;                   // cancellation fee
    static double TTAX = 0.0;                   // transaction tax
    static double MFEE = 0.0;                   // LO make fee
    static double TFEE = 0.0;                   // MO take fee
    static float rho;                           // trading "impatience" parameter
    static double speedBump;                    // value of speed bump
    static double transparencyPeriod;           // period for transparency



    // constructor bool, bool, float, int
    public Trader(boolean HFT, boolean informed, double privateValue) {
        this.isHFT = HFT;
        this.isInformed = informed;
        TraderCount++;
        this.traderID = TraderCount;
        if (HFT){
            TraderCountHFT++;
        } else {
            TraderCountNonHFT++;
        }
        /*if (privateValue > 0){pv = 2;}
        else if (privateValue < 0){pv = 1;}
        else {pv = 0;}*/
        if (privateValue == FprivateValues[4]){pv = 4;}
        else if (privateValue == FprivateValues[3]){pv = 3;}
        else if (privateValue == FprivateValues[2]){pv = 2;}
        else if (privateValue == FprivateValues[1]){pv = 1;}
        else {pv = 0;}
        this.privateValue = (float) privateValue;// - 0.0625f;
    }

    // decision about the price is made here, so far random
    public ArrayList<Order> decision(int[] BookSizes, int[] BookInfo,
                                     double et, double priceFV, double priceFvLag, double lastUpdated){
        orders = new ArrayList<Order>();
        PriceFV = priceFV;              // get price of the fundamental value = fundamental value
        Integer codeDelta = HashCode(BookInfo);
        int maxCancelled = 0;
        double d = 0.0;
        if (!isInformed){
            BeliefD delta;
            if (Deltas.containsKey(codeDelta)){
                delta = Deltas.get(codeDelta);
            } else if (similar) {
                delta = getSimilarDelta(codeDelta, BookInfo);
                if (delta == null){
                    delta = new BeliefD((short) 0, 0.0f);
                    Deltas.put(codeDelta, delta);
                }
            } else {
                delta = new BeliefD((short) 0, 0.0f);
                Deltas.put(codeDelta, delta);
            }
            d = (priceFvLag - priceFV) + delta.getD();
            if (!fixedBeliefs){
                if(delta.getN() < nResetMax) {
                    delta.increaseN();
                } else {
                    delta.setN((short) 2);
                }
                double alpha = (1.0/(1.0 + (delta.getN()))); // updating factor
                double previousDelta = delta.getD();
                delta.setD((float)((1.0 - alpha) * previousDelta + alpha * (priceFV - priceFvLag)));
                if (writeDiagnostics){
                    if (priceFV < priceFvLag){
                        if (priceFvLag - priceFV > 0.125){
                            diag.addLearning((short) 5, previousDelta);
                            diag.addLearning((short) 6, (priceFV - priceFvLag));
                        } else {
                            diag.addLearning((short) 3, previousDelta);
                            diag.addLearning((short) 4, (priceFV - priceFvLag));
                        }
                    } else if (priceFV > priceFvLag){
                        if (priceFV - priceFvLag > 0.125){
                            diag.addLearning((short) 9, previousDelta);
                            diag.addLearning((short) 10, (priceFV - priceFvLag));
                        } else {
                            diag.addLearning((short) 7, previousDelta);
                            diag.addLearning((short) 8, (priceFV - priceFvLag));
                        }
                    } else {
                        diag.addLearning((short) 1, previousDelta);
                        diag.addLearning((short) 2, (priceFV - priceFvLag));
                    }
                    diag.addDiffFV((priceFV - priceFvLag) - previousDelta);
                }
            }
        }

        int pricePosition;              // pricePosition at which to submit
        int forbiddenMarketOrder = -1;  // if 2 * end, SMO forbidden, 2 * end + 1 BMO forbidden, to not execute against himself
        boolean buyOrder = false;       // buy order?
        long Bt = BookInfo[0];          // Best Bid position
        long At = BookInfo[1];          // Best Ask position
        int oldPos = 0, oldAction = -1;
        int q = 0;
        int x = 0;
        if (isReturning && order != null){ // pricePosition position in previous action
            oldPos = order.getPosition() - book.getPositionShift(); //
            q = Math.min(maxDepth, order.getQ());
            if(order.isBuyOrder()){
                x = 2;
                if (oldPos == Bt){
                    forbiddenMarketOrder = 2 * end;
                }
            } else {
                x = 1;
                if (oldPos == At){
                    forbiddenMarketOrder = 2 * end + 1;
                }
            }
        }

        Long code = HashCode(oldPos, q, x, BookInfo, BookSizes, et - lastUpdated);
        tempQs = states.containsKey(code) ? states.get(code)
                                          : new HashMap<Integer, BeliefQ>();
        int action = -1, nLO = 0;
        double max = -1.0, p1 = -1.0, sum = 0.0, sumNC = 0.0, Q = -1.0, nC = -1.0, maxQ = -1.0, maxNC = -1.0;

        short b = (short) Math.max(BookInfo[0] - LL + 1, 0); // + 1 in order to start from one above B
        b = (short) Math.min(end, b);
        short a = (short) Math.min(BookInfo[1] - LL + end, 2 * end);
        a = (short) Math.max(end, a);

        if (isReturning && order != null){
            boolean buy = order.isBuyOrder();
            if (buy){
                action = oldPos - LL + end;
                if (action >= end && action < a){   // still in the range for LO, else is cancelled for sure
                    if (CFEE == 0.0){               // CFEE == 0.0 so normal payoff for old action
                        if (tempQs.containsKey(action)){
                            max = tempQs.get(action).getQ();
                        } else if (similar){
                            max = getSimilarBelief(code, action, BookInfo, q, oldPos);
                        } else {
                            max = (discountFactorS.get(action - end)[Math.abs(q)] *
                                    ((breakPoint - action + end) * tickSize + privateValue - TTAX + MFEE + d));
                        }
                    } else {
                        if (tempQs.containsKey(action)){
                            maxQ = tempQs.get(action).getQ();
                            maxNC = tempQs.get(action).getnC();
                            max = maxQ - maxNC * CFEE;
                        } else if (similar){
                            double[] similarOutcome = getSimilarQnC(code, action, BookInfo, q, oldPos);
                            maxQ = similarOutcome[0];
                            maxNC = similarOutcome[1];
                            max = maxQ - maxNC * CFEE;
                        } else {
                            maxQ = (discountFactorS.get(action - end)[Math.abs(q)] *
                                    ((breakPoint - action + end) * tickSize + privateValue - TTAX + MFEE + d));
                            maxNC = numberOfCancels[action];
                            max = maxQ - maxNC * CFEE;
                        }
                    }
                    oldAction = action;
                } else {
                    action = -1; // otherwise could have action == 2 * end and the SMO would not be computed later
                }
            } else {
                action = oldPos - LL;
                if (action >= 0 && action < end){ // still in the range for LO, else is cancelled for sure
                    if (CFEE == 0.0){               // CFEE == 0.0 so normal payoff for old action
                        if (tempQs.containsKey(action)){
                            max = tempQs.get(action).getQ();
                        } else if (similar){
                            max = getSimilarBelief(code, action, BookInfo, q, oldPos);
                        } else {
                            max = (discountFactorB.get(action)[Math.abs(q)] *
                                    ((action - breakPoint) * tickSize - privateValue - TTAX + MFEE - d));
                        }
                    } else {
                        if (tempQs.containsKey(action)){
                            maxQ = tempQs.get(action).getQ();
                            maxNC = tempQs.get(action).getnC();
                            max = maxQ - maxNC * CFEE;
                        } else if (similar){
                            double[] similarOutcome = getSimilarQnC(code, action, BookInfo, q, oldPos);
                            maxQ = similarOutcome[0];
                            maxNC = similarOutcome[1];
                            max = maxQ - maxNC * CFEE;
                        } else {
                            maxQ = (discountFactorB.get(action)[Math.abs(q)] *
                                    ((action - breakPoint) * tickSize - privateValue - TTAX + MFEE - d));
                            maxNC = numberOfCancels[action];
                            max = maxQ - maxNC * CFEE;
                        }
                    }
                    oldAction = action;
                } else {
                    action = -1; // otherwise could have action == 2 * end and the SMO would not be computed later
                }
            }
        }
        if (CFEE == 0.0){
            if (prTremble > 0.0 && Math.random() < prTremble){          // trembling
                HashMap<Integer, Double> p = new HashMap<Integer, Double>();
                if (action != -1){
                    p.put(action, max);
                }
                for(int i = b; i < a; i++){ // searching for best payoff
                    p1 = -1.0;
                    if (i != oldAction && i != forbiddenMarketOrder){
                        if (tempQs.containsKey(i)){
                            p1 = tempQs.get(i).getQ();
                            p.put(i, p1);
                        } else {
                            if (i < end){ // payoff to sell limit order
                                p1 = (discountFactorB.get(i)[Math.abs(BookSizes[LL + i])] *
                                        ((i - breakPoint) * tickSize - privateValue - TTAX + MFEE - d));
                                p.put(i, p1);
                            } else if (i < a) { // payoff to buy limit order
                                p1 = (discountFactorS.get(i - end)[Math.abs(BookSizes[LL + i - end])] *
                                        ((breakPoint - i + end) * tickSize + privateValue - TTAX + MFEE + d));
                                p.put(i, p1);
                            } else if (i == (2 * end)){
                                p1 = ((Bt - fvPos) * tickSize - privateValue - TTAX - TFEE - slippageS[BookInfo[2]] - d);
                                p.put(i, p1); // payoff to sell market order
                            } else if (i == (2 * end + 1)){
                                p1 = ((fvPos - At) * tickSize + privateValue - TTAX - TFEE - slippageB[BookInfo[3]] + d);
                                p.put(i, p1); // payoff to buy market order
                            } else if (i == (2 * end + 2)){
                                double Rt = (isHFT) ? 1.0 / ReturnFrequencyHFT
                                                    : 1.0 / ReturnFrequencyNonHFT; // expected return time
                                p1 = Math.exp(-rho * Rt) * (sum / Math.max(1, nLO));
                                p.put(i, p1); // 2 for averaging over 14
                            }
                        }
                        if (p1 >= 0){
                            nLO++;
                            sum += p1;
                        }
                    }
                }
                List<Integer> actions = new ArrayList <Integer>(p.keySet());
                action = actions.get(random.nextInt(actions.size()));
                max = p.get(action);
            } else {                                // get the best payoff
                for(int i = b; i < nPayoffs; i++){  // searching for best payoff
                    p1 = -1.0f;
                    if (i != oldAction && i != forbiddenMarketOrder){
                        if (tempQs.containsKey(i)){
                            if (i < end){
                                //p1 = tempQs.get(i).getQ();
                                if (BookSizes[i + LL] > -maxDepth){
                                    p1 = tempQs.get(i).getQ();
                                } /*else {
                                    System.out.println("too low priority");
                                }*/
                            } else if (i < a){
                                //p1 = tempQs.get(i).getQ();
                                if (BookSizes[i + LL - end] < maxDepth){
                                    p1 = tempQs.get(i).getQ();
                                } /*else {
                                    System.out.println("too low priority");
                                }*/
                            } else {
                                /*if (fixedBeliefs && (i == 14 || i == 15)){
                                    System.out.println("mo");
                                }*/
                                p1 = tempQs.get(i).getQ();
                            }
                        } else {
                            if (similar){
                                /*if (fixedBeliefs && (i == 14 || i == 15)){
                                    System.out.println("mo");
                                }*/
                                p1 = getSimilarBelief(code, i, BookInfo, q, oldPos);
                            }
                            if (p1 == -1.0){
                                if (i < end){ // payoff to sell limit order
                                    p1 = (discountFactorB.get(i)[Math.abs(BookSizes[LL + i])] *
                                            ((i - breakPoint) * tickSize - privateValue - TTAX + MFEE - d));
                                } else if (i < a) { // payoff to buy limit order
                                    p1 = (discountFactorS.get(i - end)[Math.abs(BookSizes[LL + i - end])] *
                                            ((breakPoint - i + end) * tickSize + privateValue - TTAX + MFEE + d));
                                } else if (i == (2 * end)){
                                    p1 = ((Bt - fvPos) * tickSize - privateValue - TTAX - TFEE - slippageS[BookInfo[2]] - d); // payoff to sell market order
                                } else if (i == (2 * end + 1)){
                                    p1 = ((fvPos - At) * tickSize + privateValue - TTAX - TFEE - slippageB[BookInfo[3]] + d); // payoff to buy market order
                                } else if (i == (2 * end + 2)){
                                    double Rt = (isHFT) ? 1.0 / ReturnFrequencyHFT
                                                        : 1.0 / ReturnFrequencyNonHFT; // expected return time
                                    p1 = Math.exp(-rho * Rt) * (sum / Math.max(1, nLO)); // 2 for averaging over 14
                                }
                            }
                        }
                        if (p1 >= 0.0){
                            nLO++;
                            sum += p1;
                            if (p1 >= max){
                                max = p1;
                                action = i;
                            }
                        }
                    }

                }
                if (isReturning && online){ // updating old belief if trader is returning
                    if (fixedBeliefs){
                        if (!updatedTraders.contains(traderID) && (belief.getNe() > 0)){
                            if(belief.getNe() < nResetMax) {
                                belief.increaseNe();
                            }
                            double alpha = (1.0 / (1.0 + (belief.getNe()))); // updating factor
                            double previousDiff = belief.getDiff();
                            belief.setDiff((1.0 - alpha) * previousDiff +
                                    alpha * Math.exp( - rho * (et - EventTime)) * max);
                            updatedTraders.add(traderID);
                            if (writeDiagnostics){writeDiagnostics(previousDiff - Math.exp(-  rho * (et - EventTime)) * max);}
                        }
                    } else {
                        if(belief.getN() < nResetMax) {
                            belief.increaseN();
                        }
                        double alpha = (1.0 / (1.0 + (belief.getN()))); // updating factor
                        double previousQ = belief.getQ();
                        belief.setQ((1.0 - alpha) * previousQ +
                                alpha * Math.exp( - rho * (et - EventTime)) * max);
                        //if (writeDiagnostics && ia == 1){writeDiagnostics((short) action, previousQ - Math.exp(-rho * (et - EventTime)) * max);}
                        //if (writeDiagnostics){writeDiagnostics((short) action, previousQ - Math.exp(-rho * (et - EventTime)) * max);}
                        if (writeDiagnostics){writeDiagnostics(previousQ - Math.exp(-  rho * (et - EventTime)) * max);}
                    }
                }
            }
            if (fixedBeliefs){                            // just save the realized beliefs for comparison
                if (convergenceStates.containsKey(code)){
                    tempQs = convergenceStates.get(code);
                    /*if (action == 14 || action == 15){
                        System.out.println("mo");
                    }*/
                    if (tempQs.containsKey(action)){
                        belief = tempQs.get(action);
                    } else {
                        belief = new BeliefQ(1, 1, max, max);
                        tempQs.put(action, belief);
                    }
                } else {
                    tempQs = new HashMap<Integer, BeliefQ>();
                    belief = new BeliefQ(1, 1, max, max);
                    tempQs.put(action, belief);
                    convergenceStates.put(code, tempQs);
                }
            } else {                                      // beliefs saved to states HashMap
                if (tempQs.containsKey(action)){
                    belief = tempQs.get(action); // obtaining the belief-> store as private value
                } else {
                    statesCount++;
                    belief = new BeliefQ((short) 1, max);
                    tempQs.put(action, belief); // obtaining the belief-> store as private value
                }
                if (!states.containsKey(code)){
                    states.put(code, tempQs);
                }
            }
        } else {        // CFEE != 0.0
            boolean cancelled = false;              // is it necessary to cancel the previous order?
            boolean buyO = false;                   // is the would be order a buy order?
            int pPosition = 0;                      // is the would be position of a would be order
            if (prTremble > 0.0 && Math.random() < prTremble){          // trembling
                HashMap<Integer, Double[]> p = new HashMap<Integer, Double[]>();
                Double[] payoffArray = new Double[3];
                if (action != -1){
                    payoffArray[0] = max;
                    payoffArray[1] = maxQ;
                    payoffArray[2] = maxNC;
                    p.put(action, payoffArray);
                }
                for(int i = b; i < nPayoffs; i++){ // searching for best payoff
                    p1 = -1.0;
                    cancelled = false;
                    if (i != oldAction && i != forbiddenMarketOrder){
                        if (tempQs.containsKey(i)){
                            Q = tempQs.get(i).getQ();
                            nC = tempQs.get(i).getnC();
                            p1 = Q - nC * CFEE;
                            payoffArray = new Double[3];
                            payoffArray[0] = p1;
                            payoffArray[1] = Q;
                            payoffArray[2] = nC;
                            p.put(i, payoffArray);
                        } else {
                            if (i < end){ // payoff to sell limit order
                                Q = (discountFactorB.get(i)[Math.abs(BookSizes[LL + i])] *
                                        ((i - breakPoint) * tickSize - privateValue - TTAX + MFEE - d));
                                nC = numberOfCancels[i];
                                p1 = Q - nC * CFEE;
                                payoffArray = new Double[3];
                                payoffArray[0] = p1;
                                payoffArray[1] = Q;
                                payoffArray[2] = nC;
                                p.put(i, payoffArray);
                            } else if (i < a) { // payoff to buy limit order
                                Q = (discountFactorS.get(i - end)[Math.abs(BookSizes[LL + i - end])] *
                                        ((breakPoint - i + end) * tickSize + privateValue - TTAX + MFEE + d));
                                nC = numberOfCancels[i];
                                p1 = Q - nC * CFEE;
                                payoffArray = new Double[3];
                                payoffArray[0] = p1;
                                payoffArray[1] = Q;
                                payoffArray[2] = nC;
                                p.put(i, payoffArray);
                            } else if (i == (2 * end)){
                                Q = ((Bt - fvPos) * tickSize - privateValue - TTAX - TFEE - d);
                                nC = 0.0;
                                p1 = Q - nC * CFEE;
                                payoffArray = new Double[3];
                                payoffArray[0] = p1;
                                payoffArray[1] = Q;
                                payoffArray[2] = nC;
                                p.put(i, payoffArray);
                            } else if (i == (2 * end + 1)){
                                Q = ((fvPos - At) * tickSize + privateValue - TTAX - TFEE + d);
                                nC = 0.0;
                                p1 = Q - nC * CFEE;
                                payoffArray = new Double[3];
                                payoffArray[0] = p1;
                                payoffArray[1] = Q;
                                payoffArray[2] = nC;
                                p.put(i, payoffArray);
                            } else if (i == (2 * end + 2)){
                                double Rt = (isHFT) ? 1.0 / ReturnFrequencyHFT
                                                    : 1.0 / ReturnFrequencyNonHFT; // expected return time
                                Q = Math.exp(-rho * Rt) * (sum / Math.max(1, nLO));
                                nC = Math.exp(-rho * Rt) * (sumNC / Math.max(1, nLO));
                                p1 = Q - nC * CFEE;
                                payoffArray = new Double[3];
                                payoffArray[0] = p1;
                                payoffArray[1] = Q;
                                payoffArray[2] = nC;
                                p.put(i, payoffArray);
                            }
                        }
                        if (isReturning && order != null){
                            if (i >= (2 * end) && i <= (2 * end + 2)){                        // no action or MO
                                cancelled = true;
                            } else{
                                if (i >= end){
                                    buyO = true;
                                    pPosition = i + LL - end;
                                } else {
                                    pPosition = i + LL;
                                }
                                if ((oldPos != pPosition) || (order.isBuyOrder() != buyO)){   // different position or different direction
                                    cancelled = true;
                                }
                            }
                            if (cancelled && oldAction != -1){
                                p1 -= CFEE;
                                payoffArray[0] -= CFEE;
                            }
                        }
                        if (p1 >= 0){
                            nLO++;
                            sum += Q;
                            sumNC += nC;
                        }
                    }
                }
                List<Integer> actions = new ArrayList <Integer>(p.keySet());
                action = actions.get(random.nextInt(actions.size()));
                payoffArray = p.get(action);
                max = payoffArray[0];
                maxQ = payoffArray[1];
                maxNC = payoffArray[2];
            } else {                                // get the best payoff
                for(int i = b; i < nPayoffs; i++){  // searching for best payoff
                    p1 = -1.0f;
                    cancelled = false;
                    if (i != oldAction && i != forbiddenMarketOrder){
                        if (tempQs.containsKey(i)){
                            if (i < end){
                                //p1 = tempQs.get(i).getQ();
                                if (BookSizes[i + LL] > -maxDepth){
                                    Q = tempQs.get(i).getQ();
                                    nC = tempQs.get(i).getnC();
                                    p1 = Q - nC * CFEE;
                                } /*else {
                                    System.out.println("too low priority");
                                }*/
                            } else if (i < a){
                                //p1 = tempQs.get(i).getQ();
                                if (BookSizes[i + LL - end] < maxDepth){
                                    Q = tempQs.get(i).getQ();
                                    nC = tempQs.get(i).getnC();
                                    p1 = Q - nC * CFEE;
                                } /*else {
                                    System.out.println("too low priority");
                                }*/
                            } else {
                                Q = tempQs.get(i).getQ();
                                nC = tempQs.get(i).getnC();
                                p1 = Q - nC * CFEE;
                            }
                        } else {
                            if (similar){
                                double[] similarOutcome = getSimilarQnC(code, i, BookInfo, q, oldPos);
                                Q = similarOutcome[0];
                                nC = similarOutcome[1];
                                p1 = Q - nC * CFEE;
                            }
                            if (p1 == -1.0){
                                if (i < end){ // payoff to sell limit order
                                    Q = (discountFactorB.get(i)[Math.abs(BookSizes[LL + i])] *
                                            ((i - breakPoint) * tickSize - privateValue - TTAX + MFEE - d));
                                    nC = numberOfCancels[i];
                                    p1 = Q - nC * CFEE;
                                } else if (i < a) { // payoff to buy limit order
                                    Q = (discountFactorS.get(i - end)[Math.abs(BookSizes[LL + i - end])] *
                                            ((breakPoint - i + end) * tickSize + privateValue - TTAX + MFEE + d));
                                    nC = numberOfCancels[i];
                                    p1 = Q - nC * CFEE;
                                } else if (i == (2 * end)){
                                    Q = ((Bt - fvPos) * tickSize - privateValue - TTAX - TFEE - d); // payoff to sell market order
                                    nC = 0.0;
                                    p1 = Q - nC * CFEE;
                                } else if (i == (2 * end + 1)){
                                    Q = ((fvPos - At) * tickSize + privateValue - TTAX - TFEE + d); // payoff to buy market order
                                    nC = 0.0;
                                    p1 = Q - nC * CFEE;
                                } else if (i == (2 * end + 2)){
                                    double Rt = (isHFT) ? 1.0 / ReturnFrequencyHFT
                                                        : 1.0 / ReturnFrequencyNonHFT; // expected return time
                                    Q = Math.exp(-rho * Rt) * (sum / Math.max(1, nLO));
                                    nC = Math.exp(-rho * Rt) * (sumNC / Math.max(1, nLO));
                                    p1 = Q - nC * CFEE;
                                }
                            }
                        }
                        if (isReturning && order != null){
                            if (i >= (2 * end) && i <= (2 * end + 2)){                        // no action or MO
                                cancelled = true;
                            } else {
                                if (i >= end){
                                    buyO = true;
                                    pPosition = i + LL - end;
                                } else {
                                    pPosition = i + LL;
                                }
                                if ((oldPos != pPosition) || (order.isBuyOrder() != buyO)){   // different position or different direction
                                    cancelled = true;
                                }
                            }
                            if (cancelled && oldAction != -1){
                                p1 -= CFEE;
                            } else {
                                cancelled = false;
                            }
                        }
                        if (p1 >= 0.0){
                            nLO++;
                            sum += Q;
                            sumNC += nC;
                            if (p1 >= max){
                                max = p1;
                                maxQ = Q;
                                maxNC = nC;
                                maxCancelled = (cancelled) ? 1 : 0;
                                action = i;
                            }
                        }
                    }
                }
                if (isReturning && online){ // updating old belief if trader is returning
                    if (fixedBeliefs){
                        if (!updatedTraders.contains(traderID) && (belief.getNe() > 0)){
                            if(belief.getNe() < nResetMax) {
                                belief.increaseNe();
                            }
                            double alpha = (1.0 / (1.0 + (belief.getNe()))); // updating factor
                            double previousDiff = belief.getDiff();
                            belief.setDiff((1.0 - alpha) * previousDiff +
                                    alpha * Math.exp( - rho * (et - EventTime)) * maxQ);
                            updatedTraders.add(traderID);                   // one-step ahead stored
                            if (writeDiagnostics){writeDiagnostics(previousDiff - Math.exp(- rho * (et - EventTime)) * maxQ);}
                        }
                    } else {
                        if(belief.getN() < nResetMax) {
                            belief.increaseN();
                        }
                        double alpha = (1.0 / (1.0 + (belief.getN()))); // updating factor
                        double previousQ = belief.getQ();
                        double previousNC = belief.getnC();
                        belief.setQ((1.0 - alpha) * previousQ +
                                alpha * Math.exp(- rho * (et - EventTime)) * maxQ);
                        belief.setnC((1.0 - alpha) * previousNC +
                                alpha * Math.exp(- rho * (et - EventTime)) * maxCancelled);
                        if (writeDiagnostics){writeDiagnostics(previousQ - Math.exp(- rho * (et - EventTime)) * maxQ);}
                    }
                }
            }
            if (fixedBeliefs){                            // just save the realized beliefs for comparison
                if (convergenceStates.containsKey(code)){
                    tempQs = convergenceStates.get(code);
                    if (tempQs.containsKey(action)){
                        belief = tempQs.get(action);
                    } else {
                        belief = new BeliefQ(1, 1, maxQ, maxQ);
                        tempQs.put(action, belief);
                    }
                } else {
                    tempQs = new HashMap<Integer, BeliefQ>();
                    belief = new BeliefQ(1, 1, maxQ, maxQ);
                    tempQs.put(action, belief);
                    convergenceStates.put(code, tempQs);
                }
            } else {                                      // beliefs saved to states HashMap
                if (tempQs.containsKey(action)){
                    belief = tempQs.get(action); // obtaining the belief-> store as private value
                } else {
                    statesCount++;
                    belief = new BeliefQ((short) 1, maxQ, maxNC);
                    tempQs.put(action, belief); // obtaining the belief-> store as private value
                }
                if (!states.containsKey(code)){
                    states.put(code, tempQs);
                }
            }
        }


        // creating an order
        if (action >= end){
            buyOrder = true;
        }
        pricePosition = (action < end) ? action + LL
                                       : action + LL - end;
        if (action == 2 * end){ // position is Bid
            pricePosition = BookInfo[0];
            buyOrder = false;
        }
        if (action == (2 * end + 1)){pricePosition = BookInfo[1];} // position is Ask
        Order currentOrder = new Order(traderID, et, buyOrder, 1, 0, pricePosition, action);

        boolean cancelled = false;                                   // used to count cancellations
        if (isReturning){
            if (order != null){
                oldPos = order.getPosition() - book.getPositionShift();
                if (action == (2 * end + 2)){                        // no action
                    order.setCancelled(true);
                    Order orderCancelled = order;
                    orders.add(orderCancelled);
                    order = null;
                    cancelled = true;
                } else if ((oldPos != pricePosition) || (order.isBuyOrder() != buyOrder)){   // different position or different direction
                    order.setCancelled(true);
                    Order orderCancelled = order;
                    orders.add(orderCancelled);
                    orders.add(currentOrder);
                    order = currentOrder;
                    cancelled = true;
                }
            } else if (action != (2 * end + 2)){
                order = currentOrder;
                orders.add(currentOrder);
            }
        } else if (action != (2 * end + 2)){
            order = currentOrder;
            orders.add(currentOrder);
        }
        if (cancelled && oldAction != -1.0){
            cancelCount++;
            maxCancelled = 1;
        }
        BeliefnC = maxNC + maxCancelled;

        /*if ((action == (2 * end)) || (action == (2 * end + 1))) {
            if (speedBump == 0.0){
                isTraded = true; // isTraded set to true if submitting MOs
            }
            *//*if (max < 0.0){
                System.out.println("negative belief");
            }*//*
        }*/
        isReturning = true;
        if (writeDecisions){writeDecision(BookInfo, (short)action, cancelled, et - lastUpdated);} // printing data for output tables
        //if (writeHistogram){writeHistogram(BookSizes);}
        if (writeDiagnostics){writeDiagnostics((short)action);}
        EventTime = et;
        return orders;
    }

    // used to update payoff of action taken in a state upon execution
    public void execution(double fundamentalValue, double et){
        int pos = order.getPosition() - book.getPositionShift();
        double payoff;
        tradeCount++;

        if (order.isBuyOrder()){ // buy LO executed
            payoff = (order.getAction() == 2 * end + 1) ?
                    (breakPoint - (pos - LL)) * tickSize + privateValue - TTAX - TFEE :
                    (breakPoint - (pos - LL)) * tickSize + privateValue - TTAX + MFEE;
                    //+ (fundamentalValue - PriceFV);
        } else { // sell LO executed
            payoff = (order.getAction() == 2 * end) ?
                    (pos - LL - breakPoint) * tickSize - privateValue - TTAX - TFEE :
                    (pos - LL - breakPoint) * tickSize - privateValue - TTAX + MFEE;
                    //- (fundamentalValue - PriceFV);
        }
        if (fixedBeliefs){
            /*if (order.getAction() == 15 || order.getAction() == 14){
                if (payoff < 0.0){
                    System.out.println("negative payoff");
                }
            }*/
            if (belief.getNe() > 0){
                if(belief.getN() < nResetMax) {
                    belief.increaseN();
                }
                double alpha = (1.0/(1.0 + (belief.getN()))); // updating factor
                double previousQ = belief.getQ();
                belief.setQ((1.0 - alpha) * previousQ +                                 // realized payoff
                        alpha * Math.exp( - rho * (et - EventTime)) * payoff);
                if (!updatedTraders.contains(traderID)){
                    if(belief.getNe() < nResetMax) {
                        belief.increaseNe();
                    }
                    alpha = (1.0/(1.0 + (belief.getNe()))); // updating factor
                    previousQ = belief.getDiff();
                    belief.setDiff((float)((1.0 - alpha) * previousQ +                  // one-step ahead
                            alpha * Math.exp( - rho * (et - EventTime)) * payoff));
                }
                //if (writeDiagnostics){writeDiagnostics(previousQ - Math.exp( - rho * (et - EventTime)) * payoff);}
            }
        } else {
            if(belief.getN() < nResetMax) {
                belief.increaseN();
            }
            double alpha = (1.0/(1.0 + (belief.getN()))); // updating factor
            double previousQ = belief.getQ();
            belief.setQ((1.0 - alpha) * previousQ +
                    alpha * Math.exp( - rho * (et - EventTime)) * payoff);
            if (CFEE != 0.0){
                double previousNC = belief.getnC();
                belief.setnC((1.0 - alpha) * previousNC);
                        //+ alpha * Math.exp( - rho * (et - EventTime)) * cancelCount);   // executed before cancelled, i.e. cancelled is zero
                //if (writeDiagnostics){writeDiagnostics(belief.getnC() - previousNC);}
                }
            //if (writeDiagnostics){writeDiagnostics(belief.getQ() - previousQ);}
            //if (writeDiagnostics){writeDiagnostics(previousQ - Math.exp(-rho * (et - EventTime)) * payoff);}
            //if (writeDiagnostics){writeDiagnostics((short) order.getAction(), previousQ - Math.exp(-rho * (et - EventTime)) * payoff);}
        }
        isTraded = true;
        order = null;
    }

    /*public void execution(double fundamentalValue, double et){
        int pos = order.getPosition() - book.getPositionShift();
        double payoff;
        tradeCount++;
        if (order.isBuyOrder()){ // buy LO executed
            payoff = (breakPoint - (pos - LL)) * tickSize + privateValue
                    + (fundamentalValue - PriceFV);
        } else { // sell LO executed
            payoff = (pos - LL - breakPoint) * tickSize - privateValue
                    - (fundamentalValue - PriceFV);
        }
        if (!fixedBeliefs){
            if(belief.getN() < nResetMax) {
                belief.increaseN();
            }
            double alpha = (1.0/(1.0 + (belief.getN()))); // updating factor
            double previousQ = belief.getQ();
            belief.setQ((1.0 - alpha) * previousQ +
                    alpha * Math.exp( - rho * (et - EventTime)) * payoff);
            if (writeDiagnostics){writeDiagnostics(belief.getQ() - previousQ);}
        } else {
            if (belief.getN() == 1){ // completely new belief, falls here until end
                if(belief.getNe() < nResetMax) {
                    belief.increaseNe();
                }
                double alpha = (1.0/(1.0 + (belief.getNe()))); // updating factor
                double previousQ = belief.getQ();
                belief.setQ((1.0 - alpha) * previousQ +
                        alpha * Math.exp( - rho * (et - EventTime)) * payoff);
            } else { // old belief, can be taken into consideration for convergence
                if (belief.getNe() == 0){ // first time updated
                    belief.increaseNe();
                    belief.setDiff(belief.getQ());
                } else if(belief.getNe() < nResetMax) {
                    belief.increaseNe();
                } // continue with normal update
                double alpha = (1.0/(1.0 + (belief.getNe()))); // updating factor
                double previousQ = belief.getDiff();
                belief.setDiff((float)((1.0 - alpha) * previousQ +
                        alpha * Math.exp( - rho * (et - EventTime)) * payoff));
            }
        }
        isTraded = true;
        order = null;
    }*/

    // get belief of delta in a similar state
    private BeliefD getSimilarDelta(int code1b, int[] bi){
        int code2 = code1b;
        int i = 8;                                     // number of possible
        while ((Deltas.get(code2) == null) && i > 0) {
            switch (i) {
                case 8:
                    if (bi[5] < 2 * maxDepth - 15){       // depth off ask
                        code2 = code1b + (1<<22);
                    }
                    break;
                case 7:
                    if (bi[4] < 2 * maxDepth - 15){ // depth off bid is bigger than 1
                        code2 = code1b + (1<<27);
                    }
                    break;
                case 6:
                    if (bi[5] >= 3){              // depth off ask is bigger than 3
                        code2 = code1b - (1<<22);
                    }
                    break;
                case 5:
                    if (bi[4] >= 3){              // depth off bid is bigger than 3
                        code2 = code1b - (1<<27);
                    }
                    break;
                case 4:
                    if (bi[6] >= 3){               // past price is higher than 3
                        code2 = code1b - (1<<1);
                    }
                    break;
                case 3:
                    if (bi[6] <= 11){              // past price is below 11
                        code2 = code1b + (1<<1);
                    }
                    break;
                case 2:
                    if (bi[6] >= 4){               // past price is higher than 4
                        code2 = code1b - (2<<1);
                    }
                    break;
                case 1:
                    if (bi[6] <= 10){              // past price is below 10
                        code2 = code1b + (2<<1);
                    }
                    break;
            }
            i--;
        }

        return (Deltas.get(code2) != null) ? Deltas.get(code2)
                                           : null;//similarBelief;
    }
    // get belief at similar state AND or OR similar action
    private double getSimilarBelief(long code1b, int ac, int[] bi, int priority, int ownPrice){
        long code2 = code1b;                                 // modified code trying to find similar action-state belief
        //HashMap<Integer, BeliefQ> similarQs;            // HashMap of similar beliefs
        //double similarBelief = -1.0;                    // similar belief, initialized to equal standard -1 in the max choosing for loop
        int i = 13;                                     // number of possible
        //while (similarBelief == -1.0 && i > 0) {
        if (infoSize == 4){
            while ((states.get(code2) == null || states.get(code2).get(ac) == null) && i > 0) {
                switch (i) {
                    case 13:
                        if (bi[5] < 2 * maxDepth - 15){       // depth off ask
                            code2 = code1b + (1<<22);
                        }
                        break;
                    case 12:
                        if (bi[4] < 2 * maxDepth - 15){ // depth off bid is bigger than 1
                            code2 = code1b + (1<<26);
                        }
                        break;
                    case 11:
                        if (bi[5] >= 3){              // depth off ask is bigger than 3
                            code2 = code1b - (1<<22);
                        }
                        break;
                    case 10:
                        if (bi[4] >= 3){              // depth off bid is bigger than 3
                            code2 = code1b - (1<<26);
                        }
                        break;
                    case 9:
                        if (priority >= 1){            // priority of past own action is higher than 0
                            code2 = code1b - (1<<9);
                        }
                        break;
                    case 8:
                        if (bi[6] >= 3){               // past price is higher than 3
                            code2 = code1b - (1<<18);
                        }
                        break;
                    case 7:
                        if (ownPrice >= 3){
                            code2 = code1b - (1<<13);
                        }
                        break;
                    case 6:
                        if (bi[6] <= 11){              // past price is below 11
                            code2 = code1b + (1<<18);
                        }
                        break;
                    case 5:
                        if (ownPrice <= 11){
                            code2 = code1b + (1<<13);
                        }
                        break;
                    case 4:
                        if (bi[6] >= 4){               // past price is higher than 4
                            code2 = code1b - (2<<18);
                        }
                        break;
                    case 3:
                        if (ownPrice >= 4){
                            code2 = code1b - (2<<13);
                        }
                        break;
                    case 2:
                        if (bi[6] <= 10){              // past price is below 10
                            code2 = code1b + (2<<18);
                        }
                        break;
                    case 1:
                        if (ownPrice <= 10){
                            code2 = code1b + (2<<13);
                        }
                        break;
                }
                i--;
            }
        } else {
            while ((states.get(code2) == null || states.get(code2).get(ac) == null) && i > 0) {
                switch (i) {
                    case 13:
                        if (bi[5] < 2 * maxDepth - 15){       // depth off ask
                            code2 = code1b + (1<<19);
                        }
                        break;
                    case 12:
                        if (bi[4] < 2 * maxDepth - 15){ // depth off bid is bigger than 1
                            code2 = code1b + (1<<23);
                        }
                        break;
                    case 11:
                        if (bi[5] >= 3){              // depth off ask is bigger than 3
                            code2 = code1b - (1<<19);
                        }
                        break;
                    case 10:
                        if (bi[4] >= 3){              // depth off bid is bigger than 3
                            code2 = code1b - (1<<23);
                        }
                        break;
                    case 9:
                        if (priority >= 1){            // priority of past own action is higher than 0
                            code2 = code1b - (1<<6);
                        }
                        break;
                    case 8:
                        if (bi[6] >= 3){               // past price is higher than 3
                            code2 = code1b - (1<<15);
                        }
                        break;
                    case 7:
                        if (ownPrice >= 3){
                            code2 = code1b - (1<<10);
                        }
                        break;
                    case 6:
                        if (bi[6] <= 11){              // past price is below 11
                            code2 = code1b + (1<<15);
                        }
                        break;
                    case 5:
                        if (ownPrice <= 11){
                            code2 = code1b + (1<<10);
                        }
                        break;
                    case 4:
                        if (bi[6] >= 4){               // past price is higher than 4
                            code2 = code1b - (2<<15);
                        }
                        break;
                    case 3:
                        if (ownPrice >= 4){
                            code2 = code1b - (2<<10);
                        }
                        break;
                    case 2:
                        if (bi[6] <= 10){              // past price is below 10
                            code2 = code1b + (2<<15);
                        }
                        break;
                    case 1:
                        if (ownPrice <= 10){
                            code2 = code1b + (2<<10);
                        }
                        break;
                }
                i--;
            }
        }

        /*while ((states.get(code2) == null || states.get(code2).get(ac) == null) && i > 0) {
            if (i == 13 && bi[5] < 2 * maxDepth - 1){       // depth off ask is less than full
                code2 = code1b + (1<<19);
            } else if (i == 12 && bi[4] < 2 * maxDepth - 1){// depth off bid is less than full
                code2 = code1b + (1<<23);
            } else if (i == 11 && bi[5] >= 3){              // depth off ask is bigger than 3
                code2 = code1b - (1<<19);
            } else if (i == 10 && bi[4] >= 3){              // depth off bid is bigger than 3
                code2 = code1b - (1<<23);
            } else if (i == 9 && priority >= 1){            // priority of past own action is higher than 0
                code2 = code1b - (1<<6);
            } else if (i == 8 && bi[6] >= 3){               // past price is higher than 3
                code2 = code1b - (1<<15);
            } else if (i == 7 && ownPrice >= 3){
                code2 = code1b - (1<<10);
            } else if (i == 6 && bi[6] <= 11){              // past price is below 11
                code2 = code1b + (1<<15);
            } else if (i == 5 && ownPrice <= 11){
                code2 = code1b + (1<<10);
            } else if (i == 4 && bi[6] >= 4){               // past price is higher than 4
                code2 = code1b - (2<<15);
            } else if (i == 3 && ownPrice >= 4){
                code2 = code1b - (2<<10);
            } else if (i == 2 && bi[6] <= 10){              // past price is below 10
                code2 = code1b + (2<<15);
            } else if (i == 1 && ownPrice <= 10){
                code2 = code1b + (2<<10);
            }
            *//*if (states.containsKey(code2)){
                similarQs = states.get(code2);
                if (similarQs.containsKey(ac)){
                    similarBelief = similarQs.get(ac).getQ();
                }
            }*//*
            i--;
        }*/
        /*if (states.get(code2) != null && states.get(code2).get(ac) != null){
            long c = (code2 >> 39);
            if (c != bi[0]){
                System.out.println("bid not the same" + (code1b >> 39));
            }
            long b = bi[0];
            c = (code2 - (b << 39));
            long a = (c >> 35);

            if (a != bi[1]){
                c = code1b - (bi[0] << 39);
                System.out.println("bid not the same" + (c >> 35));
            }

        }*/
        return (states.get(code2) != null && states.get(code2).get(ac) != null) ? states.get(code2).get(ac).getQ()
                                                                                : -1.0;//similarBelief;
    }

    private double[] getSimilarQnC(long code1b, int ac, int[] bi, int priority, int ownPrice){
        long code2 = code1b;                            // modified code trying to find similar action-state belief
        //HashMap<Integer, BeliefQ> similarQs;          // HashMap of similar beliefs
        double[] similarBelief = {-1.0, 0.0};             // similar belief, initialized to equal standard -1 in the max choosing for loop
        int i = 13;                                     // number of possible
        //while (similarBelief == -1.0 && i > 0) {
        while ((states.get(code2) == null || states.get(code2).get(ac) == null) && i > 0) {
            switch (i) {
                case 13:
                    if (bi[5] < 2 * maxDepth - 15){       // depth off ask
                        code2 = code1b + (1<<19);
                    }
                    break;
                case 12:
                    if (bi[4] < 2 * maxDepth - 15){ // depth off bid is bigger than 1
                        code2 = code1b + (1<<23);
                    }
                    break;
                case 11:
                    if (bi[5] >= 3){              // depth off ask is bigger than 3
                        code2 = code1b - (1<<19);
                    }
                    break;
                case 10:
                    if (bi[4] >= 3){              // depth off bid is bigger than 3
                        code2 = code1b - (1<<23);
                    }
                    break;
                case 9:
                    if (priority >= 1){            // priority of past own action is higher than 0
                        code2 = code1b - (1<<6);
                    }
                    break;
                case 8:
                    if (bi[6] >= 3){               // past price is higher than 3
                        code2 = code1b - (1<<15);
                    }
                    break;
                case 7:
                    if (ownPrice >= 3){
                        code2 = code1b - (1<<10);
                    }
                    break;
                case 6:
                    if (bi[6] <= 11){              // past price is below 11
                        code2 = code1b + (1<<15);
                    }
                    break;
                case 5:
                    if (ownPrice <= 11){
                        code2 = code1b + (1<<10);
                    }
                    break;
                case 4:
                    if (bi[6] >= 4){               // past price is higher than 4
                        code2 = code1b - (2<<15);
                    }
                    break;
                case 3:
                    if (ownPrice >= 4){
                        code2 = code1b - (2<<10);
                    }
                    break;
                case 2:
                    if (bi[6] <= 10){              // past price is below 10
                        code2 = code1b + (2<<15);
                    }
                    break;
                case 1:
                    if (ownPrice <= 10){
                        code2 = code1b + (2<<10);
                    }
                    break;
            }
            i--;
        }
        /*while ((states.get(code2) == null || states.get(code2).get(ac) == null) && i > 0) {
            if (i == 13 && bi[5] < 2 * maxDepth - 1){       // depth off ask
                code2 = code1b + (1<<19);
            } else if (i == 12 && bi[4] < 2 * maxDepth - 1){ // depth off bid is bigger than 1
                code2 = code1b + (1<<23);
            } else if (i == 11 && bi[5] >= 3){              // depth off ask is bigger than 3
                code2 = code1b - (1<<19);
            } else if (i == 10 && bi[4] >= 3){              // depth off bid is bigger than 3
                code2 = code1b - (1<<23);
            } else if (i == 9 && priority >= 1){            // priority of past own action is higher than 0
                code2 = code1b - (1<<6);
            } else if (i == 8 && bi[6] >= 3){               // past price is higher than 3
                code2 = code1b - (1<<15);
            } else if (i == 7 && ownPrice >= 3){
                code2 = code1b - (1<<10);
            } else if (i == 6 && bi[6] <= 11){              // past price is below 11
                code2 = code1b + (1<<15);
            } else if (i == 5 && ownPrice <= 11){
                code2 = code1b + (1<<10);
            } else if (i == 4 && bi[6] >= 4){               // past price is higher than 4
                code2 = code1b - (2<<15);
            } else if (i == 3 && ownPrice >= 4){
                code2 = code1b - (2<<10);
            } else if (i == 2 && bi[6] <= 10){              // past price is below 10
                code2 = code1b + (2<<15);
            } else if (i == 1 && ownPrice <= 10){
                code2 = code1b + (2<<10);
            }
            *//*if (states.containsKey(code2)){
                similarQs = states.get(code2);
                if (similarQs.containsKey(ac)){
                    similarBelief = similarQs.get(ac).getQ();
                }
            }*//*
            i--;
        }*/

        if (states.get(code2) != null && states.get(code2).get(ac) != null){
            similarBelief[0] = states.get(code2).get(ac).getQ();
            similarBelief[1] = states.get(code2).get(ac).getnC();
        }

        return  similarBelief;
    }

    public void writeHistogram(int[] BookSizes){
        // histogram
        bookSizesHistory[0]++;
        int sz = BookSizes.length;
        for (int i = 0; i < sz; i++){
            if (BookSizes[i] < 0){
                bookSizesHistory[i + 1] = bookSizesHistory[i + 1] + BookSizes[i];
            } else {
                bookSizesHistory[nP + i + 1] = bookSizesHistory[nP + i + 1] + BookSizes[i];
            }
        }
    }

    public void computeInitialBeliefs(double cf, double sb){
        System.out.println("computing initial beliefs  for CFEE or SB or ultra transparency");
        if (cf != 0.0){
            int[] occurrences = new int[nPayoffs];
            Iterator<Integer> iteratorActions;
            HashMap<Integer, BeliefQ> beliefQs;
            Iterator<Long> iteratorStates = states.keySet().iterator();
            Long code;
            Integer action;
            while (iteratorStates.hasNext()){
                code = iteratorStates.next();
                beliefQs = states.get(code);
                iteratorActions = beliefQs.keySet().iterator();
                while (iteratorActions.hasNext()){
                    action = iteratorActions.next();
                    numberOfCancels[action] += beliefQs.get(action).getnC();
                    occurrences[action]++;
                }
            }
            for (int i = 0; i < nPayoffs; i++){
                numberOfCancels[i] = (double)(numberOfCancels[i] / Math.max(occurrences[i], 1));
            }
        }
        if (sb != 0.0 || model == 2){
            int[] slippageScount = new int[maxDepth + 1];
            int[] slippageBcount = new int[maxDepth + 1];
            Iterator<Integer> iteratorActions;
            HashMap<Integer, BeliefQ> beliefQs;
            Iterator<Long> iteratorStates = states.keySet().iterator();
            Long code;
            if (model == 2){
                while (iteratorStates.hasNext()){
                    code = iteratorStates.next();
                    beliefQs = states.get(code);
                    long code2 = code;
                    long Bt = (code2 >> 42);
                    code2 = code2 - (Bt << 42);

                    long At = (code2 >> 38);
                    code2 = code2 - (At << 38);

                    long lBt = (code2 >> 34);
                    code2 = code2 - (lBt << 34);

                    long lAt = (code2 >> 30);
                    code2 = code2 - (lAt<<30);

                    long dBt = (code2 >> 26);
                    code2 = code2 - (dBt<<26);

                    long dSt = (code2 >> 22);
                    code2 = code2 - (dSt<<22);

                    long Pt = (code2 >> 18);
                    code2 = code2 - (Pt<<18);

                    long b = (code2 >> 17);
                    code2 = code2 - (b<<17);

                    long P = (code2 >> 13);
                    code2 = code2 - (P<<13);

                    long q = (code2 >> 9);
                    code2 = code2 - (q<<9);

                    long x = (code2 >> 7);
                    code2 = code2 - (x<<7);

                    long a = (code2 >> 4);
                    code2 = code2 - (a<<4);

                    long l = (code2 >> 3);
                    code2 = code2 - (l<<3);

                    long tid = code2;
                    code2 = code2 - tid;
                    if (code2 != 0){
                        System.out.println("code not 0");
                    }
                    if (beliefQs.containsKey(2 * end)){
                        double p1 = (Bt - fvPos) * tickSize - FprivateValues[(int)a] - TTAX - TFEE; // payoff to sell market order
                        slippageS[2 * (int)lBt] += p1 - beliefQs.get(2 * end).getQ();
                        slippageScount[2 * (int)lBt]++;
                        /*slippageS[(int) tid] += p1 - beliefQs.get(2 * end).getQ();
                        slippageScount[(int) tid]++;*/
                    }
                    if (beliefQs.containsKey(2 *  end + 1)){
                        double p1 = (fvPos - At) * tickSize + FprivateValues[(int)a] - TTAX - TFEE;
                        slippageB[2 * (int)lAt] += p1 - beliefQs.get(2 * end + 1).getQ();
                        slippageBcount[2 * (int)lAt]++;
                        /*slippageB[(int) tid] += p1 - beliefQs.get(2 * end + 1).getQ();
                        slippageBcount[(int) tid]++;*/
                    }
                }
            } else if (sb != 0.0){
                while (iteratorStates.hasNext()){
                    code = iteratorStates.next();
                    beliefQs = states.get(code);
                    long code2 = code;
                    long Bt = (code2 >> 39);
                    code2 = code2 - (Bt << 39);

                    long At = (code2 >> 35);
                    code2 = code2 - (At << 35);

                    long lBt = (code2 >> 31);
                    code2 = code2 - (lBt << 31);

                    long lAt = (code2 >> 27);
                    code2 = code2 - (lAt<<27);

                    long dBt = (code2 >> 23);
                    code2 = code2 - (dBt<<23);

                    long dSt = (code2 >> 19);
                    code2 = code2 - (dSt<<19);

                    long Pt = (code2 >> 15);
                    code2 = code2 - (Pt<<15);

                    long b = (code2 >> 14);
                    code2 = code2 - (b<<14);

                    long P = (code2 >> 10);
                    code2 = code2 - (P<<10);

                    long q = (code2 >> 6);
                    code2 = code2 - (q<<6);

                    long x = (code2 >> 4);
                    code2 = code2 - (x<<4);

                    long a = (code2 >> 1);
                    code2 = code2 - (a<<1);

                    long l = code2;
                    code2 = code2 - l;
                    if (code2 != 0){
                        System.out.println("code not 0");
                    }
                    if (beliefQs.containsKey(2 * end)){
                        double p1 = (Bt - fvPos) * tickSize - FprivateValues[(int)a] - TTAX - TFEE; // payoff to sell market order
                        slippageS[2 * (int)lBt] += p1 - beliefQs.get(2 * end).getQ();
                        slippageScount[2 * (int)lBt]++;
                    }
                    if (beliefQs.containsKey(2 *  end + 1)){
                        double p1 = (fvPos - At) * tickSize + FprivateValues[(int)a] - TTAX - TFEE;
                        slippageB[2 * (int)lAt] += p1 - beliefQs.get(2 * end + 1).getQ();
                        slippageBcount[2 * (int)lAt]++;
                    }
                }
            }
            int sz = maxDepth / 2;
            for (int i = 0; i < sz; i++){
                slippageS[2 * i] = (double)(slippageS[i] / Math.max(slippageScount[2 * i], 1));
                slippageB[2 * i] = (double)(slippageB[i] / Math.max(slippageBcount[2 * i], 1));
                slippageS[2 * i + 1] = slippageS[2 * i];
                slippageB[2 * i + 1] = slippageB[2 * i];
            }
        }
    }

    public Long HashCode(int P, int q, int x, int[] BookInfo, int [] BS, double infoDelay){

        long code = (long) 0;
        if (infoSize == 2){
            long Bt = BookInfo[0]; // Best Bid position
            long At = BookInfo[1]; // Best Ask position
            int a = pv; // private value zero(0), negative (1), positive (2)
            code = (Bt<<17) + (At<<12) + (P<<7) + (q<<4) + (x<<2) + a;

            /*boolean[] test = new boolean[13];
long code2 = code;
test[0] = (code2>>17 == Bt);
System.out.println(test[0]);
code2 = code - (Bt<<17);
test[1] = (code2>>12 == At);
System.out.println(test[1]);
code2 = code2 - (At<<12);
test[2] = (code2>>10 == lBt);
System.out.println(test[2]);
code2 = code2 - (lBt<<10);
test[3] = (code2>>8 == lAt);
System.out.println(test[3]);
code2 = code2 - (lAt<<8);
test[4] = (code2>>5 == dBt);
System.out.println(test[4]);
code2 = code2 - (dBt<<5);
test[5] = (code2>>2 == dSt);
System.out.println(test[5]);
code2 = code2 - (dSt<<2);
test[6] = (code2>>14 == Pt);
System.out.println(test[6]);
code2 = code2 - (Pt<<14);
test[7] = (code2>>13 == b);
System.out.println(test[7]);
code2 = code2 - (b<<13);
test[8] = (code2>>7 == P);
System.out.println(test[8]);
code2 = code2 - (P<<7);
test[9] = (code2>>4 == q);
System.out.println(test[9]);
code2 = code2 - (q<<4);
test[10] = (code2>>2 == x);
System.out.println(test[10]);
code2 = code2 - (x<<2);
test[11] = (code2 == a);
System.out.println(test[11]);
code2 = code2 - a;
System.out.println(Long.toBinaryString(code));
if (code2 !=0){
System.out.println("problem");
}*/ // tests

        }else if (infoSize == 4){
            int tid = (int) (2.0 * infoDelay / transparencyPeriod);
            long Bt = BookInfo[0];      // Best Bid position
            long At = BookInfo[1];      // Best Ask position
            long lBt = BookInfo[2] / 2;     // depth at best Bid
            long lAt = BookInfo[3] / 2;     // depth at best Ask
            long dBt = Math.min(15, BookInfo[4] / 3); // depth off Bid
            long dSt = Math.min(15, BookInfo[5] / 3); // depth off Ask
            int Pt = BookInfo[6];       // last transaction pricePosition position
            int b = BookInfo[7];        // 1 if last transaction buy, 0 if sell
            q = Math.min(15, q);
            int a = pv;                 // private value zero(0), negative (1), positive (2)
            int l = (isHFT) ? 1 : 0;    // arrival frequency slow (0), fast (1)
            //System.out.println(Bt + " : " + lBt + " ; " + At + " : " + lAt);
            /*Long code = (Bt<<50) + (At<<44) + (lBt<<40) + (lAt<<36) + (dBt<<29) + (dSt<<22) + (Pt<<16) + (b<<15) +
+ (P<<9) + (q<<5) + (x<<3) + (a<<1) + l;*/
            code = (Bt<<42) + (At<<38) + (lBt<<34) + (lAt<<30) + (dBt<<26) + (dSt<<22) + (Pt<<18) + (b<<17) +
                    + (P<<13) + (q<<9) + (x<<7) + (a<<4) + (l<<3) + tid;

            /*long code2 = code;
            boolean [] test = new boolean[15];
            test[0] = ((code2 >> 42) == Bt);
            code2 = code2 - (Bt<<42);

            test[1] = ((code2 >> 38) == At);
            code2 = code2 - (At<<38);

            test[2] = ((code2 >> 34) == lBt);
            code2 = code2 - (lBt<<34);

            test[3] = ((code2 >> 30) == lAt);
            code2 = code2 - (lAt<<30);

            test[4] =((code2 >> 26) == dBt);
            code2 = code2 - (dBt<<26);

            test[5] = ((code2 >> 22) == dSt);
            code2 = code2 - (dSt<<22);

            test[6] = ((code2 >> 18) == Pt);
            code2 = code2 - (Pt<<18);

            test[7] = ((code2 >> 17) == b);
            code2 = code2 - (b<<17);

            test[8] = ((code2 >> 13) == P);
            code2 = code2 - (P<<13);

            test[9] = ((code2 >> 9) == q);
            code2 = code2 - (q<<9);

            test[10] = ((code2 >> 7) == x);
            code2 = code2 - (x<<7);

            test[11] = ((code2 >> 4) == a);
            code2 = code2 - (a<<4);

            test[12] = ((code2 >> 3) == l);
            code2 = code2 - (l<<3);

            test[13] = (code2 == tid);
            code2 = code2 - tid;

            test[14] = (code2 == 0);
            for (int i = 0; i < 15; i++){
                if (!test[i]){
                    System.out.println("testing hash code failed");
                }
            }*/         // tests
        } else if (infoSize == 5) { // GPR 2005 state space
            long tempCode;
            int buy;
            for (int i = 1; i < nP - 1; i++){
                buy = (BS[i] > 0) ? 1 : 0;
                tempCode = (code<<5) + (buy<<4) + Math.abs(BS[i]);
                code = tempCode;
            }
            /*long Bt = BookInfo[0]; // Best Bid position
long At = BookInfo[1]; // Best Ask position
long lBt = BookInfo[2]; // depth at best Bid
long lAt = BookInfo[3]; // depth at best Ask
long dBt = (BookInfo[4]); // depth at buy
int dSt = (BookInfo[5]); // depth at sell
int l = (isHFT) ? 1 : 0; // arrival frequency slow (0), fast (1)
//int u2t = (units2trade == 2) ? 1 : 0;
int u2t = 0;

*//*code = (Bt<<30) + (At<<26) + (lBt<<21) + (lAt<<16) +
(dBt<<9) + (dSt<<2) + (l<<1) + u2t;*//*
code = (B2<<48) + (A2<<44) + (lB2<<39) + (lA2<<34) + (Bt<<30) + (At<<26) + (lBt<<21) + (lAt<<16) +
(dBt<<9) + (dSt<<2) + (l<<1) + u2t;*/

            /* boolean[] test = new boolean[13];
long code2 = code;
test[0] = (code2>>27 == Bt);
System.out.println(test[0]);
code2 = code - (Bt<<27);
test[1] = (code2>>22 == At);
System.out.println(test[1]);
code2 = code2 - (At<<22);
test[2] = (code2>>18 == lBt);
System.out.println(test[2]);
code2 = code2 - (lBt<<18);
test[3] = (code2>>14 == lAt);
System.out.println(test[3]);
code2 = code2 - (lAt<<14);
test[4] = (code2>>8 == dBt);
System.out.println(test[4]);
code2 = code2 - (dBt<<8);
test[5] = (code2>>2 == dSt);
System.out.println(test[5]);
code2 = code2 - (dSt<<2);
test[6] = (code2>>1 == l);
System.out.println(test[6]);
code2 = code2 - (l<<1);
test[7] = (code2 == u2t);
System.out.println(test[7]);
code2 = code2 - u2t;
System.out.println(Long.toBinaryString(code));
if (code2 !=0){
System.out.println("problem");
} */
            /*boolean[] test = new boolean[2 * (nP - 2)];
long code2 = code;
int buy2;
long bs;
for (int i = nP - 2; i > 0; i--){
buy2 = (BS[nP - 1 - i] > 0) ? 1 : 0;
test[nP - 3 + i] = (code2 >> ((i - 1) * 5 + 4)) == buy2;
code2 = code2 - (buy2 << ((i - 1) * 5 + 4));
bs = code2 >> ((i - 1) * 5);
test[i - 1] = bs == Math.abs(BS[nP - 1 - i]);
code2 = code2 - (bs<<(i - 1) * 5);
}
if (code2 !=0){
System.out.println("problem");
}*/// tests

        } else if (infoSize == 6) {
            long Bt = BookInfo[0]; // Best Bid position
            long At = BookInfo[1]; // Best Ask position
            long lBt = BookInfo[2] / 3; // depth at best Bid
            long lAt = BookInfo[3] / 3; // depth at best Ask
            int a = pv; // private value zero(0), negative (1), positive (2)
            code = (Bt<<21) + (At<<16) + (lBt<<14) + (lAt<<12) + (P<<7) + (q<<4) + (x<<2) + a;

        } else if (infoSize == 7){
            long Bt = BookInfo[0]; // Best Bid position
            long At = BookInfo[1]; // Best Ask position
            long lBt = BookInfo[2] / 2; // depth at best Bid
            long lAt = BookInfo[3] / 2; // depth at best Ask
            long dBt = (BookInfo[4] / maxDepth); // depth at buy
            int dSt = (BookInfo[5] / maxDepth); // depth at sell
            int Pt = BookInfo[6]; // last transaction pricePosition position
            int b = BookInfo[7]; // 1 if last transaction buy, 0 if sell
            int a = pv; // private value zero(0), negative (1), positive (2)
            int h = (isHFT) ? 1 : 0; // arrival frequency slow (0), fast (1)

            /*Long code = (Bt<<50) + (At<<44) + (lBt<<40) + (lAt<<36) + (dBt<<29) + (dSt<<22) + (Pt<<16) + (b<<15) +
+ (P<<9) + (q<<5) + (x<<3) + (a<<1) + l;*/
            code = (Bt<<34) + (At<<29) + (lBt<<27) + (lAt<<25) + (dBt<<22) + (dSt<<19) + (Pt<<14) + (b<<13) +
                    + (P<<8) + (q<<5) + (x<<3) + (a<<1) + h;

        }
        else if (infoSize == 8){
            long Bt = BookInfo[0];      // Best Bid position
            long At = BookInfo[1];      // Best Ask position
            long lBt = BookInfo[2] / 2;     // depth at best Bid
            long lAt = BookInfo[3] / 2;     // depth at best Ask
            long dBt = Math.min(15, BookInfo[4] / 3); // depth off Bid
            long dSt = Math.min(15, BookInfo[5] / 3); // depth off Ask
            int Pt = BookInfo[6];       // last transaction pricePosition position
            int b = BookInfo[7];        // 1 if last transaction buy, 0 if sell
            q = Math.min(15, q);
            int a = pv;                 // private value zero(0), negative (1), positive (2)
            int l = (isHFT) ? 1 : 0;    // arrival frequency slow (0), fast (1)
            //System.out.println(Bt + " : " + lBt + " ; " + At + " : " + lAt);
            /*Long code = (Bt<<50) + (At<<44) + (lBt<<40) + (lAt<<36) + (dBt<<29) + (dSt<<22) + (Pt<<16) + (b<<15) +
+ (P<<9) + (q<<5) + (x<<3) + (a<<1) + l;*/
            code = (Bt<<39) + (At<<35) + (lBt<<31) + (lAt<<27) + (dBt<<23) + (dSt<<19) + (Pt<<15) + (b<<14) +
                    + (P<<10) + (q<<6) + (x<<4) + (a<<1) + l;

            /*long code2 = code;
            boolean [] test = new boolean[14];
            test[0] = ((code2 >> 39) == Bt);
            code2 = code2 - (Bt<<39);

            test[1] = ((code2 >> 35) == At);
            code2 = code2 - (At<<35);

            test[2] = ((code2 >> 31) == lBt);
            code2 = code2 - (lBt<<31);

            test[3] = ((code2 >> 27) == lAt);
            code2 = code2 - (lAt<<27);

            test[4] =((code2 >> 23) == dBt);
            code2 = code2 - (dBt<<23);

            test[5] = ((code2 >> 19) == dSt);
            code2 = code2 - (dSt<<19);

            test[6] = ((code2 >> 15) == Pt);
            code2 = code2 - (Pt<<15);

            test[7] = ((code2 >> 14) == b);
            code2 = code2 - (b<<14);

            test[8] = ((code2 >> 10) == P);
            code2 = code2 - (P<<10);

            test[9] = ((code2 >> 6) == q);
            code2 = code2 - (q<<6);

            test[10] = ((code2 >> 4) == x);
            code2 = code2 - (x<<4);

            test[11] = ((code2 >> 1) == a);
            code2 = code2 - (a<<1);

            test[12] = (code2 == l);
            code2 = code2 - l;

            test[13] = (code2 == 0);
            for (int i = 0; i < 14; i++){
                if (test[i] == false){
                    System.out.println("testing hash code failed");
                }
            }*/         // tests
        } else if (infoSize == 9){
            long Bt = BookInfo[0];      // Best Bid position
            long At = BookInfo[1];      // Best Ask position
            long lBt = BookInfo[2] / 2;     // depth at best Bid
            long lAt = BookInfo[3] / 2;     // depth at best Ask
            long dBt = Math.min(15, BookInfo[4] / 3); // depth off Bid
            long dSt = Math.min(15, BookInfo[5] / 3); // depth off Ask
            int Pt = BookInfo[6];       // last transaction pricePosition position
            int b = BookInfo[7];        // 1 if last transaction buy, 0 if sell
            q = Math.min(15, q);
            int a = pv;                 // private value zero(0), negative (1), positive (2)
            int l = (isHFT) ? 1 : 0;    // arrival frequency slow (0), fast (1)
            //System.out.println(Bt + " : " + lBt + " ; " + At + " : " + lAt);
            /*Long code = (Bt<<50) + (At<<44) + (lBt<<40) + (lAt<<36) + (dBt<<29) + (dSt<<22) + (Pt<<16) + (b<<15) +
+ (P<<9) + (q<<5) + (x<<3) + (a<<1) + l;*/
            code = (Bt<<42) + (At<<37) + (lBt<<33) + (lAt<<29) + (dBt<<25) + (dSt<<21) + (Pt<<16) + (b<<15) +
                    + (P<<10) + (q<<6) + (x<<4) + (a<<1) + l;

            /*long code2 = code;
            boolean [] test = new boolean[14];
            test[0] = ((code2 >> 42) == Bt);
            code2 = code2 - (Bt<<42);

            test[1] = ((code2 >> 37) == At);
            code2 = code2 - (At<<37);

            test[2] = ((code2 >> 33) == lBt);
            code2 = code2 - (lBt<<33);

            test[3] = ((code2 >> 29) == lAt);
            code2 = code2 - (lAt<<29);

            test[4] =((code2 >> 25) == dBt);
            code2 = code2 - (dBt<<25);

            test[5] = ((code2 >> 21) == dSt);
            code2 = code2 - (dSt<<21);

            test[6] = ((code2 >> 16) == Pt);
            code2 = code2 - (Pt<<16);

            test[7] = ((code2 >> 15) == b);
            code2 = code2 - (b<<15);

            test[8] = ((code2 >> 10) == P);
            code2 = code2 - (P<<10);

            test[9] = ((code2 >> 6) == q);
            code2 = code2 - (q<<6);

            test[10] = ((code2 >> 4) == x);
            code2 = code2 - (x<<4);

            test[11] = ((code2 >> 1) == a);
            code2 = code2 - (a<<1);

            test[12] = (code2 == l);
            code2 = code2 - l;

            test[13] = (code2 == 0);
            for (int i = 0; i < 14; i++){
                if (test[i] == false){
                    System.out.println("testing hash code failed");
                }
            }*/         // tests
        }

        return code;
    }
}
