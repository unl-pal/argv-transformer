package com.lind.ass3.victor.ParserRelated;

/**
 * Contains all information for an individual station
 * @author K3LARA
 *
 */
public class Station implements Comparable<Station>{
    private String stationNbr;
    private String stationName;
    private double latitude; //X coordinate, RT90
    private double longitude; //Y coordinate, RT90
}