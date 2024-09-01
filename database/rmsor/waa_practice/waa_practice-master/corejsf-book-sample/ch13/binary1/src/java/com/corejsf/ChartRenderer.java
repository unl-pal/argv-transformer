package com.corejsf;

import java.io.IOException;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

@FacesRenderer(componentFamily="javax.faces.Output", 
      rendererType="com.corejsf.Chart")
public class ChartRenderer extends Renderer {
   private static final int DEFAULT_WIDTH = 200;
   private static final int DEFAULT_HEIGHT = 200;   
}