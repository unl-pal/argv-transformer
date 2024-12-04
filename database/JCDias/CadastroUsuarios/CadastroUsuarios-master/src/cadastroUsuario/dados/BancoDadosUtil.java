/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastroUsuario.dados;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author PC
 */
public class BancoDadosUtil {
    private static final String DRIVER = "org.hsqldb.jdbcDriver";
    private static final String URL = "jdbc:hsqldb:file:BD/CadastroUsuarios;shutdown=true";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";
}
