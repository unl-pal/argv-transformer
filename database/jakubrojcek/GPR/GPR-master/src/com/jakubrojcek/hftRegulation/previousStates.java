package com.jakubrojcek.hftRegulation;

import com.jakubrojcek.BeliefQ;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: rojcek
 * Date: 15.10.13
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public class previousStates implements java.io.Serializable {
    private HashMap<Long, HashMap<Integer, BeliefQ>> tempStates;
}
