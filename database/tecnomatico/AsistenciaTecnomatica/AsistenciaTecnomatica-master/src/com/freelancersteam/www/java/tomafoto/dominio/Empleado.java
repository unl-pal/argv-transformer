package com.freelancersteam.www.java.tomafoto.dominio;
// Generated 22/07/2013 12:24:46 by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Empleado generated by hbm2java
 */
public class Empleado  implements java.io.Serializable {


     private int legajo;
     private Empresa empresa;
     private int dni;
     private String apellido;
     private String nombre;
     private Date fechaIngreso;
     private String direccion;
     private String localidad;
     private String clave;
     private Boolean administrador;
     private byte[] imagen;
     private String telefono;
     private Set<Asistencia> asistencias = new HashSet<Asistencia>(0);




}


