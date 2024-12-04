 package com.bashizip.andromed.gae;

import org.restlet.engine.Engine;
import org.restlet.ext.httpclient.HttpClientHelper;
import org.restlet.ext.jackson.JacksonConverter;
 
public class EngineConfiguration
{
    private static EngineConfiguration  ourInstance = new EngineConfiguration();
 
    public final static String  gae_path = "http://andromed-cloud.appspot.com/";
}