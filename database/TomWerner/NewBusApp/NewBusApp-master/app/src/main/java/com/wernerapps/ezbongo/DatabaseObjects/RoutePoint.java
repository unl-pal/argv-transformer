package com.wernerapps.ezbongo.DatabaseObjects;

import com.orm.SugarRecord;

/**
 * Created by Tom on 3/28/2015.
 */
public class RoutePoint extends SugarRecord<RoutePoint> {
    double lat, lng;
    String tag;
}
