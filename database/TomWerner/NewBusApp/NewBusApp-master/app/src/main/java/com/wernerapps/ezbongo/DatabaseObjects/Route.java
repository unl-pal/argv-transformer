package com.wernerapps.ezbongo.DatabaseObjects;

import com.orm.SugarRecord;

/**
 * Created by Tom on 3/28/2015.
 */
public class Route extends SugarRecord<Route>{

    String name;
    String tag;
    String agency;
    String agencyname;
    double max_lat, max_lng, min_lat, min_lng;
    RoutePath[] paths;
    String[] messages;
    RouteDirection[] directions;
}
