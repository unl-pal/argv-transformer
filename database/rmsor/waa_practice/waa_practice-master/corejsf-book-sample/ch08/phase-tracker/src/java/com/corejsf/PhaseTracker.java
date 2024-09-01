package com.corejsf;

import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseListener;
import javax.faces.event.PhaseId;

public class PhaseTracker implements PhaseListener {
   private static final String PHASE_PARAMETER ="com.corejsf.phaseTracker.phase";
   private static final Logger logger = Logger.getLogger("com.corejsf.phases");
   private static String phase = null;
}