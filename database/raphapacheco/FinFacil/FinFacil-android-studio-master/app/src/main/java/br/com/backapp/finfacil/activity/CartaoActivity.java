package br.com.backapp.finfacil.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import br.com.backapp.finfacil.R;
import br.com.backapp.finfacil.activity.spinner_adapter.CategoriaSpinnerAdapter;
import br.com.backapp.finfacil.data_access_object.CartaoDAO;
import br.com.backapp.finfacil.database.DatabaseHelper;
import br.com.backapp.finfacil.model.Cartao;
import br.com.backapp.finfacil.model.Categoria;
import br.com.backapp.finfacil.resources.Recursos;

/**
 * Created by raphael on 23/02/2015.
 */
public class CartaoActivity extends ActionBarActivity {
    public static final String PARAMETRO_CARTAO_ID = "_id";
    private Cartao cartao;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase base;
    private long cartaoIdSelecionado;
    private EditText editDescricao;
    private EditText editValor;
    private Spinner spinnerRepetir;
    private Spinner spinnerCategoria;
    private TextView textData;
    private Date dataLancamento;
    private Context context;
}
