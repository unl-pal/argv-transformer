package Entidades;
// Generated 31/03/2015 20:14:15 by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * TblProduto generated by hbm2java
 */
public class TblProduto  implements java.io.Serializable {


     private Integer produtoCodigo;
     private TblRefTipoProduto tblRefTipoProduto;
     private String produtoNome;
     private String produtoDescricao;
     private BigDecimal produtoPreco;
     private int produtoQuantidade;
     private String produtoTamanho;
     private Set tblItensPedidos = new HashSet(0);




}


