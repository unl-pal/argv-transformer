package com.example.zim.testapp;



import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DbActivity extends Activity {
    class Adapter extends ArrayAdapter<MainActivity.MyCurrency> {
        private List<MainActivity.MyCurrency> items;
        private int layoutResourceId;
        private Context context;
        public class CurrencyHolder {
            MainActivity.MyCurrency xcurrency;
            ImageView logo;
            TextView text;
            String date = "";
        }
    }


}
