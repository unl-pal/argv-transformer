package fr.ac_versailles.crdp.apiscol.seek.exceptionMappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import fr.ac_versailles.crdp.apiscol.seek.MetadataRepositoryFailureException;
import fr.ac_versailles.crdp.apiscol.seek.UnknownMetadataRepositoryException;

@Provider
public class MetadataRepositoryFailureExceptionMapper implements
		ExceptionMapper<MetadataRepositoryFailureException> {
}