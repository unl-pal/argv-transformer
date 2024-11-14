package br.com.backapp.finfacil.activity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.backapp.finfacil.R;
import br.com.backapp.finfacil.resources.MascaraCasasDecimaisInputFilter;
import br.com.backapp.finfacil.resources.Recursos;


/**
 * Created by raphael on 01/03/2015.
 */
public class ConfiguracaoActivity extends ActionBarActivity {
    private String SALARIO_CONFIG = "salario";
    private String DIA_FECHAMENTO_CONFIG = "dia_fechamento_cartao";
    private TextView textDiaFechamento;
}
