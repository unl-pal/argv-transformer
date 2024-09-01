package mtgdeckbuilder.backend;

import mtgdeckbuilder.data.CardImageInfo;
import mtgdeckbuilder.data.Url;

import java.io.File;
import java.util.Set;

public class CardImageDownloader {

    //TODO Jarek: cards have different sizes!
    private static final String LOW_RES_URL = "http://api.mtgdb.info/content/card_images/";
    private static final String HIGH_RES_URL = "http://api.mtgdb.info/content/hi_res_card_images/";
    private static final String LOW_RES_EXT = ".jpeg";
    private static final String HIGH_RES_EXT = ".jpg";

    private final File cardsDirectory;
    private final ImageDownloader imageDownloader;

}
