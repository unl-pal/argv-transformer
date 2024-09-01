package com.corejsf;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

// Renderer for the UITabbedPane component

@FacesRenderer(componentFamily="javax.faces.Command", 
   rendererType="com.corejsf.TabbedPane")
public class TabbedPaneRenderer extends Renderer {
   private static Logger logger = Logger.getLogger("com.corejsf.util");
}
