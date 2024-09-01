package mtgdeckbuilder.frontend;

import org.junit.Ignore;
import org.junit.Test;

import javax.swing.JLabel;
import java.awt.Dimension;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static mtgdeckbuilder.util.FrontEndTestingUtils.displayAndWait;
import static mtgdeckbuilder.util.FrontEndTestingUtils.findComponentRecursively;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CardsDisplayPanelTest {

    private CardImageLoader cardImageLoader = mock(CardImageLoader.class);

    private CardsDisplayPanel cardsDisplayPanel = new CardsDisplayPanel(cardImageLoader);

}
