package br.com.backapp.finfacil.data_access_object;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.backapp.finfacil.model.Conta;
import br.com.backapp.finfacil.resources.Recursos;

/**
 * Created by raphael on 20/02/2015.
 */
public class ContaDAO {
    public static String NOME_DA_TABELA = "resumo";
    private int COLUNA_ID = 0;
    private int COLUNA_DESCRICAO = 1;
    private int COLUNA_VALOR = 2;
    private int COLUNA_DATA = 3;
    private int COLUNA_CATEGORIA = 4;
    private int COLUNA_PREVISAO = 5;
    private SQLiteDatabase database;
}
