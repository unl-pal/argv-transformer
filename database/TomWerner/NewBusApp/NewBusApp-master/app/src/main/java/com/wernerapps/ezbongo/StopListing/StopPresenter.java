package com.wernerapps.ezbongo.StopListing;

import com.wernerapps.ezbongo.DatabaseObjects.Stop;

/**
 * Created by Tom on 3/26/2015.
 */
public interface StopPresenter {
    public enum ItemAction
    {
        INCOMING_TIMES,
        REMINDER,
        FAVORITE
    }
}
