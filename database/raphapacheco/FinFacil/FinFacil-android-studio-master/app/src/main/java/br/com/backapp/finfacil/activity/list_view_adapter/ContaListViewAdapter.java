package br.com.backapp.finfacil.activity.list_view_adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.backapp.finfacil.R;
import br.com.backapp.finfacil.model.Conta;
import br.com.backapp.finfacil.resources.Recursos;

/**
 * Created by raphael on 21/02/2015.
 */
public class ContaListViewAdapter extends BaseAdapter {
    private ArrayList<Conta> contas;
    private LayoutInflater inflater;
    private Context context;

    private class ViewHolder {
        TextView textViewLineDescricao;
        TextView textViewLineValor;
        TextView textViewLineData;
    }
}
