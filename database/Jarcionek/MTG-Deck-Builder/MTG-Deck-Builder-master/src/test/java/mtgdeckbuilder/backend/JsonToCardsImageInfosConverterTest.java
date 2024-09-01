package mtgdeckbuilder.backend;

import mtgdeckbuilder.data.CardImageInfo;
import org.junit.Test;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.CoreMatchers.is;

public class JsonToCardsImageInfosConverterTest {

    private JsonToCardsImageInfosConverter jsonToCardsImageInfosConverter = new JsonToCardsImageInfosConverter();

}
