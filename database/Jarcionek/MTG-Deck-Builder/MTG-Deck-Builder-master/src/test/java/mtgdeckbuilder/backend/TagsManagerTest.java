package mtgdeckbuilder.backend;

import com.shazam.shazamcrest.matcher.CustomisableMatcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class TagsManagerTest {

    private static final String TAG_ONE = "tagNameOne";
    private static final String TAG_TWO = "tagNameTwo";
    private static final String CARD_ONE = "cardNameOne";
    private static final String CARD_TWO = "cardNameTwo";

    private TagFilesManager tagFilesManager = mock(TagFilesManager.class);

    private TagsManager tagsManager = new TagsManager(tagFilesManager);

}