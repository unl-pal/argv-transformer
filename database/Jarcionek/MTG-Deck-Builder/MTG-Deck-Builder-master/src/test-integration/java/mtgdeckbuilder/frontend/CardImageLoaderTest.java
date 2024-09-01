package mtgdeckbuilder.frontend;

import org.junit.Test;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;

import static mtgdeckbuilder.Utils.assertImagesEqual;
import static mtgdeckbuilder.Utils.cardsDirectory;
import static mtgdeckbuilder.Utils.loadResourceImage;

public class CardImageLoaderTest {

    private CardImageLoader cardImageLoader = new CardImageLoader(cardsDirectory());
    
}