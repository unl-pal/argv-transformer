package br.com.backapp.finfacil.activity.spinner_adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.backapp.finfacil.model.Categoria;

/**
 * Created by raphael on 08/04/2015.
 */
public class CategoriaSpinnerAdapter extends ArrayAdapter<Categoria> {

    private Context context;
    private ArrayList<Categoria> categorias;
}
