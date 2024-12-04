package br.com.backapp.finfacil.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.backapp.finfacil.R;
import br.com.backapp.finfacil.activity.list_view_adapter.CartaoListViewAdapter;
import br.com.backapp.finfacil.activity.list_view_adapter.CarteiraListViewAdapter;
import br.com.backapp.finfacil.activity.list_view_adapter.ContaListViewAdapter;
import br.com.backapp.finfacil.data_access_object.CartaoDAO;
import br.com.backapp.finfacil.data_access_object.CarteiraDAO;
import br.com.backapp.finfacil.data_access_object.ContaDAO;
import br.com.backapp.finfacil.database.DatabaseHelper;
import br.com.backapp.finfacil.model.Cartao;
import br.com.backapp.finfacil.model.Carteira;
import br.com.backapp.finfacil.model.Conta;
import br.com.backapp.finfacil.resources.Configuracoes;
import br.com.backapp.finfacil.resources.Recursos;

public class ResumoActivity extends ActionBarActivity {
    public static final String ABA_RESUMO_NOME = "Resumo";
    public static final String ABA_CONTA_NOME = "Conta";
    public static final String ABA_CARTEIRA_NOME = "Carteira";
    public static final String ABA_CARTAO_NOME = "Cartao";
    public static final String CONFIG_ABA_SELECIONADA = "CONFIG_ABA_SELECIONADA";
    private Context context;
    private ListView listViewConta;
    private ListView listViewCarteira;
    private ListView listViewCartao;
    private TextView textTotal;
    private TextView textTotalPrevisto;
    private View cardTotal;
    private View botaoAdicionar;
    private LinearLayout linearLayoutTotal;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase base;
    private ContaDAO contaDAO;
    private CarteiraDAO carteiraDAO;
    private CartaoDAO cartaoDAO;
    private ArrayList<Conta> contas;
    private ArrayList<Carteira> carteiras;
    private ArrayList<Cartao> cartaos;
    private String abaSelecionada;
    private double totalConta = 0;
    private double totalContaPrevisto = 0;
    private double totalCarteira = 0;
    private double totalCarteiraPrevisto = 0;
    private double totalCartao = 0;
    private double totalCarteiraAnterior = 0;
    private double totalContaAnterior = 0;
    private double totalCarteiraPrevistoAnterior = 0;
    private double totalContaPrevistoAnterior = 0;
    private Integer posicaoItemSelecionado = -1;
    private MenuItem menuData;
    private Conta contaSelecionado;
    private Carteira carteiraSelecionado;
    private Cartao cartaoSelecionado;
    private Configuracoes configuracoes;

    private LinearLayout viewResumo;
    private TextView resumoTotal;
    private TextView resumoTotalPrevisto;
    private TextView resumoTotalConta;
    private TextView resumoTotalContaPrevisto;
    private TextView resumoTotalCarteria;
    private TextView resumoTotalCarteriaPrevisto;
    private TextView resumoTotalCartao;
    private void carregarContasSelecionadas() {
        contas = contaDAO.obterTodosNaDataAtual(configuracoes.getOrdenacaoLancamentos() == 1);
        totalConta = contaDAO.obterTotalConta();
        totalContaPrevisto = contaDAO.obterTotalContaPrevisto();
        totalContaAnterior = contaDAO.obterTotalContaAnterior();
        totalContaPrevistoAnterior = contaDAO.obterTotalContaPrevistoAnterior();

        carteiras = carteiraDAO.obterTodosNaDataAtual(configuracoes.getOrdenacaoLancamentos() == 1);
        totalCarteira = carteiraDAO.obterTotalCarteira();
        totalCarteiraPrevisto = carteiraDAO.obterTotalCarteiraPrevisto();
        totalCarteiraAnterior = carteiraDAO.obterTotalCarteiraAnterior();
        totalCarteiraPrevistoAnterior = carteiraDAO.obterTotalCarteiraPrevistoAnterior();

        cartaos = cartaoDAO.obterTodosNaDataAtual(configuracoes.getOrdenacaoLancamentos() == 1);
        totalCartao = cartaoDAO.obterTotalCartao();

        if (configuracoes.getModoVisualizacao() < 3) {
            totalCarteira += totalCarteiraAnterior;
            totalCarteiraPrevisto += totalCarteiraPrevistoAnterior;
            totalConta += totalContaAnterior;
            totalContaPrevisto += totalContaPrevistoAnterior;
        }

        Conta saldoContaAnterior = new Conta();
        saldoContaAnterior.setId(-1);
        saldoContaAnterior.setDescricao(this.getResources().getString(R.string.text_saldo_anterior));
        saldoContaAnterior.setValor(totalContaAnterior);
        saldoContaAnterior.setData(Recursos.dataAtualString());
        contas.add(0, saldoContaAnterior);

        Carteira saldoCarteiraAnterior = new Carteira();
        saldoCarteiraAnterior.setId(-1);
        saldoCarteiraAnterior.setDescricao(this.getResources().getString(R.string.text_saldo_anterior));
        saldoCarteiraAnterior.setValor(totalCarteiraAnterior);
        saldoCarteiraAnterior.setData(Recursos.dataAtualString());
        carteiras.add(0, saldoCarteiraAnterior);
    }
}
