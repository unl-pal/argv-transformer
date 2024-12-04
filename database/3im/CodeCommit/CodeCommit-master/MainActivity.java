package com.example.zim.testapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends ActionBarActivity {
    TextView RUR;
    TextView EUR;
    TextView USD;
    TextView WEATHERTEXT;
    ImageView WEATHER;
    static String privatLink = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5";
    static String weatherLink = "http://api.openweathermap.org/data/2.5/weather?q=Kiev&mode=xml";

    class CurrenParsing extends AsyncTask<String, Void, List<Valuta>>{
    }

    class WeatherParsing extends AsyncTask<String, Void, String>{
        String rain = "";
        String precipitation = "";

    }

    @Table(name = "CurrencyDB")
    static class MyCurrency extends Model {

        @Column(name = "Day")
        public String date;
        @Column(name = "Currency")
        public String currency;
        @Column(name = "Buy")
        public String buy;
        @Column(name = "Sell")
        public String sell;
    }

    class Valuta {
        String name = "";
        String buy = "";
        String sell = "";
    }
}
