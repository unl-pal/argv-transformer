package com.lind.ass3.victor.ParserRelated;

import java.util.Calendar;

/**
 * A Journey has a start and an endstation. Stations between start and stop are not implemented in this version
 * @author K3LARA
 *
 */
public class Journey {
    private Calendar depDateTime;
    private Calendar arrDateTime;
    private String noOfChanges;
    private String lineOnFirstJourney;
    private String travelMinutes;
    private String timeToDeparture;
    private String noOfZones;
    private String lineTypeName;
    private String depTimeDeviation;
    private String arrTimeDeviation;
    private Station startStation;
    private Station endStation;


}