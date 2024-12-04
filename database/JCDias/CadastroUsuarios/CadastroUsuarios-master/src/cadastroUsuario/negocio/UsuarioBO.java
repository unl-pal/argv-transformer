/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastroUsuario.negocio;

import cadastroUsuario.Excecoes.CamposVaziosException;
import cadastroUsuario.Excecoes.CpfInvalidoException;
import cadastroUsuario.Excecoes.CpfJaCadastradoException;
import cadastroUsuario.Excecoes.DataInvalidaException;
import cadastroUsuario.Excecoes.EmailInvalidoException;
import cadastroUsuario.Excecoes.NomeInvalidoException;
import cadastroUsuario.dados.UsuarioDAO;
import cadastroUsuario.entidades.Usuario;
import cadastroUsuario.utilitarios.Utils;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author SEC04
 */
public class UsuarioBO {
}

//Leia mais em: Validando o CPF em uma Aplicação Java http://www.devmedia.com.br/validando-o-cpf-em-uma-aplicacao-java/22097#ixzz3W4aPqXlo

