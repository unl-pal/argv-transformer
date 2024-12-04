package to.marcus.simple_dagger_test.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import to.marcus.simple_dagger_test.R;
import to.marcus.simple_dagger_test.model.Photos;

/**
 * Created by mplienegger on 5/11/2015.
 */
public class PhotoAdapter extends ArrayAdapter<Photos.Photo> {

    private final Context context;
    private LayoutInflater mLayoutInflater;
    private final ArrayList<Photos.Photo> mPhotos;
    private ViewHolder viewHolder;

    static class ViewHolder{
        ImageView imageView;
    }
}
