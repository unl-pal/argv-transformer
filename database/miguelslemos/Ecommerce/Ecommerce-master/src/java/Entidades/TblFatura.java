package Entidades;
// Generated 31/03/2015 20:14:15 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * TblFatura generated by hbm2java
 */
public class TblFatura  implements java.io.Serializable {


     private Integer faturaCodigo;
     private TblPedidos tblPedidos;
     private TblRefFaturaStatus tblRefFaturaStatus;
     private String faturaData;
     private String faturaDetalhes;
     private Set tblEnvioses = new HashSet(0);
     private Set tblPagamentoses = new HashSet(0);




}


