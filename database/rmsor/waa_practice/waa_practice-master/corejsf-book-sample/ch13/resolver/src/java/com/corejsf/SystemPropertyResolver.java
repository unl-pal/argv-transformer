package com.corejsf;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import javax.el.ELContext;
import javax.el.ELResolver;

public class SystemPropertyResolver extends ELResolver {
   public static class PartialResolution extends ArrayList<String> {
   }
}
