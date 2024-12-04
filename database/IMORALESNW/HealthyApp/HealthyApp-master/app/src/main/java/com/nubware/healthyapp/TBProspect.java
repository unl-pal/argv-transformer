package com.nubware.healthyapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by IMORALES on 27/04/2015.
 */
public class TBProspect {

    private static long tabletConsecutive = 0;

    //region TBProspect variables

    private long _id;//IdTabletProspect
    public String IDProspect;
    public int IDDemonstrator;
    public int IDEvent;
    public int IDSubEvent;
    public int IDChair;
    public int Consecutive;
    public String FirstName;
    public String LastName;
    public String Profession;
    public String Street;
    public String City;
    public String Prov;
    public String PostalCode;
    public String DaytimePhone;
    public String EveningPhone;
    public String Mobile;
    public String Email;
    public String Pregnant;
    public String CancerTreatments;
    public String LightSensitive;
    public String Observations;
    public String AreaConcernOne;
    public String AreaConcernTwo;
    public String AreaConcernThree;
    public String Initials;
    public String PrintName;
    public String SignatureProspect;
    public String NameWitness;
    public String SignatureWitness;
    public String SendEmailRegistered;
    public boolean SendEmailFeedback;//Boolean (0-1)
    public boolean ReadReleaseAndWaiver;//Boolean (0-1)
    public boolean SyncCloud;//Boolean (0-1)
    public boolean SyncInfusionSoft;//Boolean (0-1)
    public String PathPDF;
    public String PathApplicantSignature;
    public String PathWitnessSignature;
    public String RegistrationDate;//UnixTime (number of seconds since midnight 1 January 1970 UTC)
    public String SyncRegistrationDate;//UnixTime (number of seconds since midnight 1 January 1970 UTC)
    public String SessionStartDateTime;
    public String SessionEndDateTime;
    public String Notes;
    public String Estatus;
    //endregion

}
