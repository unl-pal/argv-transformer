package mtgdeckbuilder.backend;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TagFilesManagerTest {

    private static final File TAGS_DIRECTORY = new File(TagFilesManagerTest.class.getSimpleName() + "-dir");

    private final String tagOne = "tagNameOne";
    private final String tagTwo = "tagNameTwo";
    private final String tagThree = "tagNameThree";
    private final File tagFileOne = new File(TAGS_DIRECTORY, tagOne + ".txt");
    private final File tagFileTwo = new File(TAGS_DIRECTORY, tagTwo + ".txt");
    private final File tagFileThree = new File(TAGS_DIRECTORY, tagThree + ".txt");
    private final String cardNameOne = "cardNameOne";
    private final String cardNameTwo = "cardNameTwo";
    private final String cardNameThree = "cardNameThree";
    private final List<String> cardsOne = newArrayList(cardNameOne);
    private final List<String> cardsTwo = newArrayList(cardNameTwo, cardNameThree);

    private TagFilesManager tagFilesManager = new TagFilesManager(TAGS_DIRECTORY);

}