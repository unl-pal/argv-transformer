package to.marcus.simple_dagger_test.model;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;

/**
 * Created by marcus on 27/04/15
 * A Class template to hold the values of the GSON deserialization
 * Inner Photo class holds the JSON nested list element from the API call
 */
public class Photos {

    @Expose private int page;
    @Expose private int pages;
    @Expose private int perpage;
    @Expose private String total;
    @Expose private ArrayList<Photo> photo = new ArrayList<Photo>();

    public class Photo {
        @Expose private String id;
        @Expose private String owner;
        @Expose private String secret;
        @Expose private String server;
        @Expose private int farm;
        @Expose private String title;
        @Expose private int ispublic;
        @Expose private int isfriend;
        @Expose private int isfamily;
    }
}
