package fr.ac_versailles.crdp.apiscol.seek;

import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.w3c.dom.Document;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class SuggestQueryWorker implements Runnable {

	private final String query;
	private final WebResource webServiceResource;
	private final SeekApi caller;
	private final UUID identifier;
}
