package com.lind.ass3.victor.ParserRelated;

public class Constants {
    public static final String testURL = "http://www.labs.skanetrafiken.se/v2.2/resultspage.asp?cmdaction=next&selPointFr=%7C81116%7C0&selPointTo=%7C65008%7C0&LastStart=2012-10-14%2008:00";
    public static final String baseURL = "http://www.labs.skanetrafiken.se/v2.2/";
    public static final String queryURL = "resultspage.asp?cmdaction=next&selPointFr=";
    public static final String getStationURL = "querystation.asp?inpPointfr=";
    public static final String stationResultURL = "stationresults.asp?selPointFrKey=";
    public static final String pipe = "%7C";
    public static final String space = "%20";
    public static final String midPartURL = "0&selPointTo=";
    public static final String lastPartURL = "0&LastStart=";
    public static final String noOfResults = "&NoOf=";
    public static int nbrResultsToGet = 25;

}