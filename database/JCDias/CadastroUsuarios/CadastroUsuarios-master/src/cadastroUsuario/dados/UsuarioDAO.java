/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastroUsuario.dados;

import cadastroUsuario.entidades.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class UsuarioDAO {

    //Verificar se o cpf digitado já esta cadastrado
    private static final String SQL_SELECT_CPF_IGUAL = "SELECT  NOME, CPF FROM USUARIOS WHERE CPF = ?";

    //Cadastrar Usuario
    private static final String SQL_INSERT_USUARIO = "INSERT INTO USUARIOS( NOME, CPF, RG, DATA_NASC, SEXO, RUA, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, TELEFONE, CELULAR, EMAIL, MALHA, TEMPO, CATEGORIA, TIPO, DIA, HORARIO, DATA_CADASTRO )\n"
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    //Selecionar Usuarios para gerar relatório
    private static final String SQL_SELECT_RELATORIO_USUARIOS = "SELECT NOME, CPF, MALHA, TEMPO, CATEGORIA, TIPO FROM USUARIOS order by(nome) asc";
}
