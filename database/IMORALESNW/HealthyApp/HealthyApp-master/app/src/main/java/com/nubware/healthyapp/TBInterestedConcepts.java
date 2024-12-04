package com.nubware.healthyapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by IMORALES on 05/05/2015.
 */
public class TBInterestedConcepts {

    private long _id;//IdTablet_InterestedConcept

    public String IDInterestedConcept;
    public String IDProspect;
    public String Description;
    public int Result;


    public enum Concepts
    {
        Physical_Training_and_Exercise,
        Skin_Rejuvenation,
        Weight_Loss_Body_Contouring
    }
}
