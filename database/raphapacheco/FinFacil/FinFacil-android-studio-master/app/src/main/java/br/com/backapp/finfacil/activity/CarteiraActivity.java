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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import br.com.backapp.finfacil.R;
import br.com.backapp.finfacil.activity.spinner_adapter.CategoriaSpinnerAdapter;
import br.com.backapp.finfacil.data_access_object.CarteiraDAO;
import br.com.backapp.finfacil.database.DatabaseHelper;
import br.com.backapp.finfacil.model.Carteira;
import br.com.backapp.finfacil.model.Categoria;
import br.com.backapp.finfacil.resources.Recursos;

/**
 * Created by raphael on 23/02/2015.
 */
public class CarteiraActivity extends ActionBarActivity {
    public static final String PARAMETRO_CARTEIRA_ID = "_id";
    private Carteira carteira;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase base;
    private long carteiraIdSelecionado;
    private EditText editDescricao;
    private EditText editValor;
    private RadioButton radioButtonCredito;
    private Spinner spinnerRepetir;
    private Spinner spinnerCategoria;
    private TextView textData;
    private Date dataLancamento;
    private CheckBox checkPrevisao;
    private Context context;

    private void salvar(){
        if (validarCampos()) {
            int numeroParcelas = 1;
            numeroParcelas += (int) spinnerRepetir.getSelectedItemId();

            Double valor = Double.valueOf(editValor.getText().toString());

            if (!radioButtonCredito.isChecked())
                valor = valor * -1;

            carteira.setDescricao(editDescricao.getText().toString() + (numeroParcelas == 1 ? "": " (1/" + String.valueOf(numeroParcelas) + ")"));
            carteira.setValor(valor);
            carteira.setData(Recursos.converterDataParaStringBD(dataLancamento));
            carteira.setCategoria_id(((Categoria) spinnerCategoria.getSelectedItem()).getId());
            carteira.setPrevisao(checkPrevisao.isChecked());

            CarteiraDAO carteiraDAO = new CarteiraDAO(base);

            if (carteiraIdSelecionado > 0)
                carteiraDAO.atualizar(carteira);
            else
                carteiraDAO.inserir(carteira);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dataLancamento);

            for (int i = 2; i <= numeroParcelas; i++) {
                calendar.set(calendar.MONTH, calendar.get(calendar.MONTH)+ 1);
                carteira = new Carteira();
                carteira.setDescricao(editDescricao.getText().toString() + " (" + String.valueOf(i) + "/" + String.valueOf(numeroParcelas) + ")");
                carteira.setValor(valor);
                carteira.setData(Recursos.converterDataParaStringBD(calendar.getTime()));
                carteira.setCategoria_id(((Categoria) spinnerCategoria.getSelectedItem()).getId());
                carteira.setPrevisao(checkPrevisao.isChecked());

                carteiraDAO.inserir(carteira);
            }

            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);
            finish();

            Toast.makeText(getApplicationContext(), R.string.msg_lancamento_efetuado, Toast.LENGTH_SHORT).show();
        }
    }
}
