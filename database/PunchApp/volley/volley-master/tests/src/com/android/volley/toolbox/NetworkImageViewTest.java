package com.android.volley.toolbox;

import android.test.InstrumentationTestCase;
import android.view.ViewGroup.LayoutParams;

public class NetworkImageViewTest extends InstrumentationTestCase {
    private NetworkImageView mNIV;
    private MockImageLoader mMockImageLoader;

    private class MockImageLoader extends ImageLoader {
        public String lastRequestUrl;
        public int lastMaxWidth;
        public int lastMaxHeight;
    }
}
