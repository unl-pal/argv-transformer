package com.nubware.healthyapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

/**
 * Created by ACOLOR on 2015-04-03.
 */
public class ApplicationStep1 extends WizardStep {

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


    EditText firstNameEt;
    EditText lastNameEt;
    EditText professionEt;
    EditText streetEt;
    EditText cityEt;
    Spinner provincesSpin;
    EditText postalcodeEt;
    EditText homephoneEt;
    EditText workphoneEt;
    EditText mobileEt;
    EditText emailEt;

    Boolean firstNameFlag = false;
    Boolean lastNameFlag = false;
}
