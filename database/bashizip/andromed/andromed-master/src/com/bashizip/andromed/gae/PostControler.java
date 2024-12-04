package com.bashizip.andromed.gae;

import java.util.List;

import org.restlet.resource.ClientResource;

import com.bashizip.andromed.data.Patient;
import com.bashizip.andromed.data.Post;

import android.util.Log;

public class PostControler {

	public final ClientResource cr = new ClientResource(
			EngineConfiguration.gae_path + "/rest/post");
}
