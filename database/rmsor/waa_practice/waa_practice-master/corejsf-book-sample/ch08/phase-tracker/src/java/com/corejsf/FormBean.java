package com.corejsf;

import javax.faces.FactoryFinder;
import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.RequestScoped; 
   // or import javax.faces.bean.RequestScoped;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.model.SelectItem;

@Named // or @ManagedBean
@RequestScoped
public class FormBean {
   private SelectItem[] phases = {
      new SelectItem("RESTORE_VIEW"),
      new SelectItem("APPLY_REQUEST_VALUES"),
      new SelectItem("PROCESS_VALIDATIONS"),
      new SelectItem("UPDATE_MODEL_VALUES"),
      new SelectItem("INVOKE_APPLICATION"),
      new SelectItem("RENDER_RESPONSE"),
      new SelectItem("ANY_PHASE"),
   };
}
