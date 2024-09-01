package com.corejsf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

public class BinaryPhaseListener implements PhaseListener {
   public static final String BINARY_PREFIX = "/binary";

   public static final String DATA_ID_PARAM = "id";
}