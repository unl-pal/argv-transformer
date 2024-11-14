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
import br.com.backapp.finfacil.data_access_object.ContaDAO;
import br.com.backapp.finfacil.database.DatabaseHelper;
import br.com.backapp.finfacil.model.Categoria;
import br.com.backapp.finfacil.model.Conta;
import br.com.backapp.finfacil.resources.Recursos;


/**
 * Created by raphael on 22/02/2015.
 */
public class ContaActivity extends ActionBarActivity {
    public static final String PARAMETRO_CONTA_ID = "_id";
    private Conta conta;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase base;
    private long contaIdSelecionado;
    private EditText editDescricao;
    private EditText editValor;
    private RadioButton radioButtonCredito;
    private Spinner spinnerRepetir;
    private Spinner spinnerCategoria;
    private TextView textData;
    private CheckBox checkPrevisao;
    private Date dataLancamento;
    private Context context;

    private void salvar(){
        if (validarCampos()) {
            int numeroParcelas = 1;
            numeroParcelas += (int) spinnerRepetir.getSelectedItemId();

            Double valor = Double.valueOf(editValor.getText().toString());

            if (!radioButtonCredito.isChecked())
                valor = valor * -1;

            conta.setDescricao(editDescricao.getText().toString() + (numeroParcelas == 1 ? "": " (1/" + String.valueOf(numeroParcelas) + ")"));
            conta.setValor(valor);
            conta.setData(Recursos.converterDataParaStringBD(dataLancamento));
            conta.setCategoria_id(((Categoria) spinnerCategoria.getSelectedItem()).getId());
            conta.setPrevisao(checkPrevisao.isChecked());

            ContaDAO contaDAO = new ContaDAO(base);

            if (contaIdSelecionado > 0)
                contaDAO.atualizar(conta);
            else
                contaDAO.inserir(conta);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dataLancamento);

            for (int i = 2; i <= numeroParcelas; i++) {
                calendar.set(calendar.MONTH, calendar.get(calendar.MONTH)+ 1);
                conta = new Conta();
                conta.setDescricao(editDescricao.getText().toString() + " (" + String.valueOf(i) + "/" + String.valueOf(numeroParcelas) + ")");
                conta.setValor(valor);
                conta.setData(Recursos.converterDataParaStringBD(calendar.getTime()));
                conta.setCategoria_id(((Categoria) spinnerCategoria.getSelectedItem()).getId());
                conta.setPrevisao(checkPrevisao.isChecked());

                contaDAO.inserir(conta);
            }

            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);
            finish();

            Toast.makeText(getApplicationContext(), R.string.msg_lancamento_efetuado, Toast.LENGTH_SHORT).show();
        }
    }
}
