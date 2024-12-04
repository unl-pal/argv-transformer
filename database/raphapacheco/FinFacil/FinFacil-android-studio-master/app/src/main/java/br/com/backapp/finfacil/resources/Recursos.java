package br.com.backapp.finfacil.resources;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.text.InputFilter;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.widget.SpinnerAdapter;

import br.com.backapp.finfacil.R;
import br.com.backapp.finfacil.activity.spinner_adapter.CategoriaSpinnerAdapter;
import br.com.backapp.finfacil.data_access_object.CategoriaDAO;
import br.com.backapp.finfacil.model.Categoria;

/**
 * Created by raphael on 20/02/2015.
 */
public class Recursos {
    private static Date dataAtual;
    private static ArrayList<String> listaCategorias;
    private static ArrayList<Categoria> categorias;
}
