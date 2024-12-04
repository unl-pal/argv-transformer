package com.nubware.healthyapp;

import android.appwidget.AppWidgetHost;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

/**
 * Created by ACOLOR on 2015-04-03.
 */
public class ApplicationSummary extends WizardStep {

    @ContextVariable
    private String firstname;
    @ContextVariable
    private String lastname;
    @ContextVariable
    private String profession;
    @ContextVariable
    private String street;
    @ContextVariable
    private String city;
    @ContextVariable
    private String province;
    @ContextVariable
    private String postalcode;
    @ContextVariable
    private String homephone;
    @ContextVariable
    private String workphone;
    @ContextVariable
    private String mobile;
    @ContextVariable
    private String email;
    @ContextVariable
    private String receiveinfo;


}
