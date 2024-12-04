package com.stardog.ext.spring;

import java.util.function.Supplier;

import com.complexible.stardog.Stardog;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.ConnectionCredentials;
import com.complexible.stardog.api.admin.AdminConnection;
import com.complexible.stardog.api.admin.AdminConnectionConfiguration;
import com.stardog.ext.spring.Provider;

/**
 * Created by albaker on 3/4/14.
 */
public class EmbeddedProvider implements Provider {

    private static Stardog stardog;
}
