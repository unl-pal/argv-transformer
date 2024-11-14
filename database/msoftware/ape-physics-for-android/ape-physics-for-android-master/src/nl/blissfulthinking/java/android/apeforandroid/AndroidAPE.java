package nl.blissfulthinking.java.android.apeforandroid;

import nl.blissfulthinking.java.android.apeforandroid.GameView.GameThread;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

//package nl.blissfulthinking.java.android.apeforandroid;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.TextView;
//
//public class AndroidAPE extends Activity {
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////      setContentView(R.layout.main);
//        TextView tv = new TextView(this);
//        tv.setText("Hello World!");
//        setContentView(tv);
//    }
//}

public class AndroidAPE extends Activity {
    private static final int MENU_PAUSE = Menu.FIRST;

    private static final int MENU_RESUME = Menu.FIRST + 1;

    private static final int MENU_START = Menu.FIRST + 2;

    private static final int MENU_STOP = Menu.FIRST + 3;

    /** A handle to the thread that's actually running the animation. */
    private GameThread mGameThread;

    /** A handle to the View in which the game is running. */
    private GameView mGameView;
}
