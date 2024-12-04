package com.wernerapps.ezbongo.DatabaseObjects;

import com.orm.SugarRecord;

/**
 * Created by Tom on 3/28/2015.
 */
public class RouteDirection extends SugarRecord<RouteDirection> {
    String direction, directiontag;
    Stop[] stops;
}
