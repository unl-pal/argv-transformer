/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelancersteam.www.java.tomafoto.dominio.dao.imp;

/**
 *
 * @author alumno
 */
public class Borrador {
    
//    PARA USAR EL POOL CPO3 
//     <property name="connection.provider_class">
//     org.hibernate.connection.C3P0ConnectionProvider
//    </property>
//    <property name="c3p0.acquire_increment">1</property>
//    <property name="c3p0.idle_test_period">100</property><!-- seconds -->
//    <property name="c3p0.max_size">5</property>
//    <property name="c3p0.max_statements">10</property>
//    <property name="c3p0.min_size">3</property>
//    <property name="c3p0.timeout">200</property><!-- seconds -->
//    <property name="hibernate.c3p0.validate">SELECT 1</property>
//    
    
//    public List<Empleado> listarEmpleado() {
//        Transaction t = getSession().beginTransaction();
//        List<Empleado> lista ;
//        try { 
//            Criteria criteria = getSession().createCriteria(Empleado.class);
//            lista = criteria.list();
//         t.commit();
//        } catch (RuntimeException e) {
//             if (t != null) t.rollback();
//              throw e; // or display error message
//        }finally{
//         if (getSession() != null && getSession().isOpen()) {
//                if (getSession().getTransaction() != null && 
//                getSession().getTransaction().isActive()) {
//                getSession().getTransaction().rollback();
//                 }
//                getSession().close();
//              //  getSession().clear();
//             }
//        }
//         return lista;
//        
//                
//    }
//
//    public void addEmpleado(Empleado a) {
//         Transaction t = getSession().beginTransaction();
//          try { 
//         getSession().save(a);
//         t.commit();
//         } catch (RuntimeException e) {
//             if (t != null) t.rollback();
//              throw e; // or display error message
//        }finally{
//        if (getSession() != null && getSession().isOpen()) {
//                if (getSession().getTransaction() != null && 
//                getSession().getTransaction().isActive()) {
//                getSession().getTransaction().rollback();
//                 }
//                getSession().close();
//              //  getSession().clear();
//             }
//          }
//    }
//
//    public void deleteEmpleado(Empleado a) {
//        Transaction t = getSession().beginTransaction();
//        try { 
//        getSession().delete(a);
//       t.commit();
//       } catch (RuntimeException e) {
//             if (t != null) t.rollback();
//              throw e; // or display error message
//        }finally{
//               if (getSession() != null && getSession().isOpen()) {
//                if (getSession().getTransaction() != null && 
//                getSession().getTransaction().isActive()) {
//                getSession().getTransaction().rollback();
//                 }
//                getSession().close();
//              //  getSession().clear();
//             }
//        }
//    }
//
//    public void upDateEmpleado(Empleado a) {
//      Transaction t = getSession().beginTransaction();
//       try {
//            getSession().update(a);
//            t.commit();
//       } catch (RuntimeException e) {
//             if (t != null) t.rollback();
//              throw e; // or display error message
//        }finally{
//           if (getSession() != null && getSession().isOpen()) {
//                if (getSession().getTransaction() != null && 
//                getSession().getTransaction().isActive()) {
//                getSession().getTransaction().rollback();
//                 }
//                getSession().close();
//              //  getSession().clear();
//             }
//       }
//    }
//
//    public Empleado getEmpleado(int idEmpleado) {
//        Transaction t = getSession().beginTransaction();
//        Empleado a;
//        try {
//            a = (Empleado) getSession().get(Empleado.class, idEmpleado);
//            t.commit();
//        } catch (RuntimeException e) {
//             if (t != null) t.rollback();
//              throw e; // or display error message
//        }finally{
//            if (getSession() != null && getSession().isOpen()) {
//                if (getSession().getTransaction() != null && 
//                getSession().getTransaction().isActive()) {
//                getSession().getTransaction().rollback();
//                 }
//                getSession().close();
//              //  getSession().clear();
//             }
//            
//            
//        }              
//       return a;
//    }
//    
    
    
//     public List<Asistencia> listarAsistencia() {
//       
//       Transaction t = getSession().beginTransaction();
//        List<Asistencia> lista ;
//        try { 
//            Criteria criteria = getSession().createCriteria(Asistencia.class);
//            lista = criteria.list();
//         t.commit();
//        } catch (RuntimeException e) {
//             if (t != null) t.rollback();
//              throw e; // or display error message
//        }finally{
//         if (getSession() != null && getSession().isOpen()) {
//                if (getSession().getTransaction() != null && 
//                getSession().getTransaction().isActive()) {
//                getSession().getTransaction().rollback();
//                 }
//                getSession().close();
//              //  getSession().clear();
//             }
//        }
//         return lista;
//    }
//
//    public void addAsistencia(Asistencia a) {
//        Transaction t = getSession().beginTransaction();
//          try { 
//         getSession().save(a);
//         t.commit();
//         } catch (RuntimeException e) {
//             if (t != null) t.rollback();
//              throw e; // or display error message
//        }finally{
//         if (getSession() != null && getSession().isOpen()) {
//                if (getSession().getTransaction() != null && 
//                getSession().getTransaction().isActive()) {
//                getSession().getTransaction().rollback();
//                 }
//                getSession().close();
//              //  getSession().clear();
//             }
//          }
//    }
//
//    public void deleteAsistencia(Asistencia a) {
//         Transaction t = getSession().beginTransaction();
//        try { 
//        getSession().delete(a);
//       t.commit();
//       } catch (RuntimeException e) {
//             if (t != null) t.rollback();
//              throw e; // or display error message
//        }finally{
//       if (getSession() != null && getSession().isOpen()) {
//                if (getSession().getTransaction() != null && 
//                getSession().getTransaction().isActive()) {
//                getSession().getTransaction().rollback();
//                 }
//                getSession().close();
//              //  getSession().clear();
//             }
//        }
//    }
//
//    public void upDateAsistencia(Asistencia a) {
//       Transaction t = getSession().beginTransaction();
//       try {
//            getSession().update(a);
//            t.commit();
//       } catch (RuntimeException e) {
//             if (t != null) t.rollback();
//              throw e; // or display error message
//        }finally{
//            if (getSession() != null && getSession().isOpen()) {
//                if (getSession().getTransaction() != null && 
//                getSession().getTransaction().isActive()) {
//                getSession().getTransaction().rollback();
//                 }
//                getSession().close();
//              //  getSession().clear();
//             }
//       }
//    }
//
//    public Asistencia getAsistencia(int idAsistencia) {
//        Transaction t = getSession().beginTransaction();
//        Asistencia a;
//        try {
//            a = (Asistencia) getSession().get(Asistencia.class, idAsistencia);
//            t.commit();
//        } catch (RuntimeException e) {
//             if (t != null) t.rollback();
//              throw e; // or display error message
//        }finally{
//             if (getSession() != null && getSession().isOpen()) {
//                if (getSession().getTransaction() != null && 
//                getSession().getTransaction().isActive()) {
//                getSession().getTransaction().rollback();
//                 }
//                getSession().close();
//              //  getSession().clear();
//             }
//        }              
//       return a;
//    }
//    
}
