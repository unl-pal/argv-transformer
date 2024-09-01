/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositorio;

import Entidades.TblCliente;
import Entidades.TblMetodosPagamentoCliente;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import Util.HibernateUtil;
import org.hibernate.Hibernate;

/**
 *
 * @author MiguelLemos
 */
public class RepositorioTblMetodosPagamentoCliente {
    SessionFactory factory = HibernateUtil.getSessionFactory();
}
