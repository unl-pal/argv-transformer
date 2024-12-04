package br.com.backapp.finfacil.resources;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by raphael on 13/04/2015.
 */
public class Configuracoes {

    private SharedPreferences config;
    private SharedPreferences.Editor editor;
    private String DIA_FECHAMENTO_CONFIG = "DIA_FECHAMENTO";
    private String MODO_VISUALIZACAO_TOTALIZADOR_CONFIG = "DIA_FECHAMENTO";
    private String ORDENACAO_LANCAMENTOS = "ORDENACAO_LANCAMENTOS";

    private Integer diaFechamento = 0;
    private Integer modoVisualizacao = 0;
    private Integer ordenacaoLancamentos = 0;
}
