package is.arontibo.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.InjectView;
import is.arontibo.library.ElasticDownloadView;
import is.arontibo.library.ProgressDownloadView;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.elastic_download_view)
    ElasticDownloadView mElasticDownloadView;

}
