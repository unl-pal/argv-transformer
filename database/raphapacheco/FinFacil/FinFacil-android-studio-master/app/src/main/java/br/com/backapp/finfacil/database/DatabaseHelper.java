package br.com.backapp.finfacil.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.com.backapp.finfacil.R;

/**
 * Created by raphael on 20/02/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String NOME_DO_BANCO_DE_DADOS = "fin_facil.db";
    private static int VERSAO_DO_BANCO_DE_DADOS = 3;
    private Context context;


}
