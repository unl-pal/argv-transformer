package com.bashizip.andromed.gae;

import java.util.List;

import org.restlet.resource.ClientResource;

import com.bashizip.andromed.data.User;

import android.util.Log;

public class UserController {
    public final ClientResource cr = new ClientResource(
	    EngineConfiguration.gae_path + "/rest/user");
}
