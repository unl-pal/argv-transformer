package Entidades;
// Generated 31/03/2015 20:14:15 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * TblCliente generated by hbm2java
 */
public class TblCliente  implements java.io.Serializable {


     private Integer clienteCodigo;
     private String clienteGenero;
     private String clientePrimeironome;
     private String clienteUltimonome;
     private String clienteEmail;
     private String clienteLogin;
     private String clienteSenha;
     private String clienteTelefone;
     private String clienteEndereco1;
     private String clienteEndereco2;
     private String clienteCidade;
     private String clienteEstado;
     private String clientePais;
     private Boolean clienteAdm;
     private Set tblPedidos = new HashSet(0);
     private Set tblMetodosPagamentoClientes = new HashSet(0);




}


