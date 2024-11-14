package br.com.backapp.finfacil.data_access_object;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

import br.com.backapp.finfacil.model.Categoria;

/**
 * Created by raphael on 06/04/2015.
 */
public class CategoriaDAO {
    public static String NOME_DA_TABELA = "categoria";
    private int COLUNA_ID = 0;
    private int COLUNA_DESCRICAO = 1;
    private SQLiteDatabase database;
}
