package com.corejsf;

import java.io.Serializable;

import javax.inject.Named;
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
   // or import javax.faces.bean.ApplicationScoped;

@Named //@ManagedBean
@ApplicationScoped
public class AutoComplete implements Serializable {
}
