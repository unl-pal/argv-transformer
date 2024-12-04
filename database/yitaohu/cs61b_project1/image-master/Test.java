/* Test.java */

/**
 *  The Test class is a program that tests the functionality of the PixImage
 *  and RunLengthEncoding classes.
 *
 *  @author Jonathan Shewchuk and Joel Galenson
 */
package cs61b_project1;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Test {

  // This should be true when we generate 
  private static final boolean WRITE_MODE = false;
  
  // The maximum number of rows/columns/lines we should print.
  // Change this to Integer.MAX_VALUE to print everything.
  private static int MAX_PRINT_SIZE = 5;

  // Handle reading and writing the good versions.
  // We can't serialize PixImages and RLEs because it gives away
  // our implementation and the names conflict, so
  // we convert to/from strings/ints.

  private static ObjectInputStream is;
  private static ObjectOutputStream os;

  /**
   *  Default parameters.
   */

  private static final int iBlur = 11;
  private static final int jBlur = 15;
  private static final int iterBlur = 5;

  private static int clamp(int coord, int max) {
    int d = MAX_PRINT_SIZE / 2;
    coord -= MAX_PRINT_SIZE / 2;
    if (coord + MAX_PRINT_SIZE > max)
      coord = max - MAX_PRINT_SIZE;
    if (coord < 0)
      coord = 0;
    return coord;
  }

  private static void runTests() throws FileNotFoundException, IOException,
                                        ClassNotFoundException {
    BufferedImage goodImage;
    BufferedImage prevImage;
    PixImage studentImage;
    int blurInitScore = 1;
    int blurInnerScore = 1;
    int blurBoundaryScore = 1;
    int blurMultiScore = 1;
    int sobelInitScore = 1;
    int sobelInnerScore = 2;
    int sobelBoundaryScore = 1;

    System.out.println("Beginning Part I.");
    System.out.println("Performing " + iterBlur + " boxBlur(1) calls, then " +
                       "one boxBlur(3) calls on a " + iBlur + "x" + jBlur +
                       " image.");

    /**
     *  Create a random image to blur.
     */

    studentImage = randomImage(iBlur, jBlur);
    goodImage = readOrWriteNextImage(studentImage);

    /**
     *  Plot and compare our image with the student's.
     */

    if (!equal(goodImage, studentImage)) {
      // The student's image is incorrect after initialization.
      System.out.println("Your initial PixImage is incorrect.");
      printPixImages(studentImage, goodImage);
      // Student loses all four blur points.
      blurInitScore = 0;
      blurInnerScore = 0;
      blurBoundaryScore = 0;
      blurMultiScore = 0;
    }

    /**
     *  Perform multiple steps of blurring.
     *
     *  Note:  These loops ensure that we always call readOrWriteNext* the
     *  same number of times even if the tests fail.
     */

    for (int x = 1; x <= iterBlur; x++) {
      // Perform one blurring step in the student's image and my image.
      prevImage = goodImage;
      studentImage = studentImage.boxBlur(1);
      goodImage = readOrWriteNextImage(studentImage);
      //      studentImage.setPixel(0, 3, (short) 2, (short) 5, (short) 7);

      // Plot and compare them.
      if (blurInnerScore > 0 && !equal(goodImage, studentImage)) {
        // The student's image is incorrect.  Check if it's only a problem
        //   at the boundaries.
        boolean innerEq = innerEqual(goodImage, studentImage, x);
        // Print error message, but only if this is the first time.
        if (blurBoundaryScore > 0) {
          blurBoundaryScore = 0;
          blurMultiScore = 0;
          // Draw previous image; correct new image; student's new image.
          System.out.println("Your image is incorrect after " + x +
                             "step(s) of boxBlur(1).");
          printPixImages(studentImage, goodImage, prevImage);
          if (innerEq) {
            System.out.println(
                "(The problem seems to be only at the boundaries.)");
          } else {
            blurInnerScore = 0;
          }
        } else if (!innerEq) {
          // Control should only get here if bugs near the boundary were
          //   encountered on a previous iteration, but bugs in the
          //   interior first surfaced on this iteration.
          blurInnerScore = 0;
          System.out.println(
            "Your image's interior is incorrect after blurring step " + x +
            ".");
          printPixImages(studentImage, goodImage, prevImage);
        }
      }
    }

    // Perform multiple blurring steps in the student's image and my image.
    prevImage = goodImage;
    studentImage = studentImage.boxBlur(3);
    goodImage = readOrWriteNextImage(studentImage);

    // Plot and compare them.
    if (blurMultiScore > 0 && !equal(goodImage, studentImage)) {
      // The student's image is incorrect.
      blurMultiScore = 0;
      // Draw previous image; correct new image; student's new image.
      System.out.println("Your image is incorrect after boxBlur(3).");
    }

    if (blurMultiScore == 1) {
      System.out.println("  Test successful.");
    }


    System.out.println();
    System.out.println("Performing a sobelEdges call on feathers.tiff.");

    /**
     *  Create a random image to perform edge detection on.
     */

    try {
      System.out.println("  Reading feathers.tiff.");
      studentImage = ImageUtils.readTIFFPix("feathers.tiff");
    } catch (IllegalArgumentException e) {
      System.out.println("Cannot read feathers.tiff.  Exiting.");
      System.exit(1);
      // studentImage = new PixImage(1, 1);
    }
    goodImage = readOrWriteNextImage(studentImage);

    /**
     *  Plot and compare our image with the student's.
     */

    if (!equal(goodImage, studentImage)) {
      // The student's image is incorrect after initialization.
      System.out.println("Your initial PixImage is incorrect.");
      printPixImages(studentImage, goodImage);
      // Student loses all four Sobel points.
      sobelInitScore = 0;
      sobelInnerScore = 0;
      sobelBoundaryScore = 0;
    }

    /**
     *  Perform Sobel edge detection in my image and the student's image.
     */

    prevImage = goodImage;
    studentImage = studentImage.sobelEdges();
    goodImage = readOrWriteNextImage(studentImage);

    // Plot and compare them.
    if (sobelInnerScore > 0 && !equal(goodImage, studentImage)) {
      sobelBoundaryScore = 0;
      // Draw previous image; correct new image; student's new image.
      System.out.println("Your image is incorrect after sobelEdges.");
      printPixImages(studentImage, goodImage, prevImage);

      // Check if it's only a problem at the boundaries.
      if (innerEqual(goodImage, studentImage, 1)) {
        System.out.println(
          "(The problem seems to be only at the boundaries.)");
      } else {
        sobelInnerScore = 0;
      }
    }

    if (sobelBoundaryScore > 0) {
      System.out.println("  Test successful.");
    }


    int parti = blurInitScore + blurInnerScore + blurBoundaryScore +
      blurMultiScore + sobelInitScore + sobelInnerScore + sobelBoundaryScore;
    System.out.println();
    System.out.println("Total Part I score:  " + parti + " out of 8.");
    System.out.println("Total Autogradable score so far:  " + parti +
                       " out of 8.");
    System.out.println();




    System.out.println("Beginning Part II.");
    System.out.println("Performing a 4x4 RunLengthEncoding-to-PixImage test.");
    int readBackScore = 2;
    int toPixImageScore = 3;

    System.out.println("  Calling the six-parameter constructor.");

    int[] rr = {0, 1, 2, 3, 4, 5};
    int[] rg = {20, 18, 16, 14, 12, 10};
    int[] rb = {42, 42, 42, 137, 137, 137};
    int[] rl = {3, 2, 5, 1, 1, 4};
    RunLengthEncoding rle1 = new RunLengthEncoding(4, 4, rr, rg, rb, rl);
    ArrayList<int[]> jrle1 = readOrWriteNextRLE(rle1);

    System.out.println("  Reading back the encoding through nextRun.");
    RunIterator rle1it = rle1.iterator();
    for (int i = 0; i < rl.length; i++) {
      if (!rle1it.hasNext()) {
        System.out.println("    Run # " + i +
                           " missing.  (Runs are indexed from zero.)");
        System.out.println("    (In other words, your nextRun() is " +
                           "returning null when it shouldn't.)");
        printRLE(jrle1);
        readBackScore = 0;
        break;
      }

      int[] ts = rle1it.next();
      if ((ts[0] != rl[i]) || (ts[1] != rr[i]) || (ts[2] != rg[i])
          || (ts[3] != rb[i])) {
        System.out.println("    Run # " + i + " should be " + rl[i] + ", " +
                           rr[i] + ", " + rg[i] + ", " + rb[i] +
                           ".  (Runs are indexed from zero.)");
        System.out.println("    Instead, it's " + ts[0] + ", " + ts[1] + ", "
                           + ts[2] + ", " + ts[3]);
        printRLE(jrle1);
        readBackScore = 0;
        break;
      }
    }

    if ((readBackScore == 1) && (rle1it.hasNext())) {
      System.out.println("    Your hasNext() is failing to return false when" +
                         " the runs run out.");
      printRLE(jrle1);
      readBackScore = 0;
    }

    System.out.println("  Calling toPixImage.");
    PixImage o1 = rle1.toPixImage();
    BufferedImage jo1 = readOrWriteNextImage(o1);
    if (!equal(jo1, o1)) {
      printPixImages(o1, jo1);
      toPixImageScore = 0;
    }

    int partii = readBackScore + toPixImageScore;
    if (partii == 5) {
      System.out.println("  Test successful.");
    }

    System.out.println();
    System.out.println("Total Part II score:  " + partii + " out of 5.");
    System.out.println("Total Autogradable score so far:  " +
                       (parti + partii) + " out of 13.");
    System.out.println();




    System.out.println("Beginning Part III.");
    System.out.println("Run-length encoding a PixImage.");
    int toRLEScore = 3;
    int backToPixImageScore = 1;

    try {
      System.out.println("  Reading highcontrast.tiff.");
      studentImage = ImageUtils.readTIFFPix("highcontrast.tiff");
    } catch (IllegalArgumentException e) {
      System.out.println("Cannot read highcontrast.tiff.  Exiting.");
      System.exit(1);
      // studentImage = new PixImage(1, 1);
    }
    goodImage = readOrWriteNextImage(studentImage);

    System.out.println("  Calling the one-parameter RunLengthEncoding " +
                       "constructor.");
    RunLengthEncoding rle2 = new RunLengthEncoding(studentImage);
    ArrayList<int[]> jrle2 = readOrWriteNextRLE(rle2);

    int i = 0;
    RunIterator rle2it = rle2.iterator();
    for (int[] jts2: jrle2) {
      if (!rle2it.hasNext()) {
        System.out.println("    Run # " + i +
                           " missing.  (Runs are indexed from zero.)");
        System.out.println("    (In other words, your nextRun() is " +
                           "returning null when it shouldn't.)");
        toRLEScore = 0;
        break;
      }

      int[] ts2 = rle2it.next();
      if ((jts2[0] != ts2[0]) || (jts2[1] != ts2[1]) || (jts2[2] != ts2[2])
          || (jts2[3] != ts2[3])) {
        System.out.println("    Run # " + i + " should be " + jts2[0] +
            ", " + jts2[1] + ", " + jts2[2] + ", " + jts2[3] + "."
            + "  (Runs indexed from zero.)");
        System.out.println("    Instead, it's " + ts2[0] + ", " + ts2[1] + ", "
            + ts2[2] + ", " + ts2[3]);
        toRLEScore = 0;
        break;
      }
      i++;
    }

    if ((toRLEScore == 1) && (rle2it.hasNext())) {
      System.out.println("    Your nextRun is failing to return null when" +
                         " the runs run out.");
      System.out.println("    Here is the correct image.");
      paint(goodImage, null);
      toRLEScore = 0;
    }

    System.out.println("  Converting back to a PixImage.");
    PixImage o2j = rle2.toPixImage();
    if (!equal(goodImage, o2j)) {
      printPixImages(o2j, goodImage);
      backToPixImageScore = 0;
    }

    int partiii = toRLEScore + backToPixImageScore;
    if (partiii == 4) {
      System.out.println("  Test successful.");
    }

    System.out.println();
    System.out.println("Total Part III score:  " + partiii + " out of 4.");
    System.out.println("Total Autogradable score so far:  " +
                       (parti + partii + partiii) + " out of 17.");
    System.out.println();




    System.out.println("Beginning Part IV.");
    System.out.println("Setting pixels in your 4x4 run-length encoding" +
                       " (from Part II).");
    int addScore1 = 1;
    int addScore2 = 1;

    System.out.println("  Setting (3, 2) to [red=5, green=10, blue=136].");
    rle1.setPixel(3, 2, (short) 5, (short) 10, (short) 136);
    jrle1 = readOrWriteNextRLE(rle1);
    i = 0;
    rle1it = rle1.iterator();
    for (int[] jts1: jrle1) {
      if (!rle1it.hasNext()) {
        System.out.println("    Run # " + i +
                           " missing.  (Runs are indexed from zero.)");
        System.out.println("    (In other words, your nextRun() is " +
                           "returning null when it shouldn't.)");
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore1 = 0;
        break;
      }

      int[] ts1 = rle1it.next();
      if ((jts1[0] != ts1[0]) || (jts1[1] != ts1[1]) || (jts1[2] != ts1[2])
          || (jts1[3] != ts1[3])) {
        System.out.println("    Run # " + i + " should be " + jts1[0] +
            ", " + jts1[1] + ", " + jts1[2] + ", " + jts1[3] + "."
            + "  (Runs indexed from zero.)");
        System.out.println("    Instead, it's " + ts1[0] + ", " + ts1[1] + ", "
            + ts1[2] + ", " + ts1[3]);
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore1 = 0;
        break;
      }
      i++;
    }

    System.out.println("  Setting (0, 3) to [red=5, green=10, blue=136].");
    rle1.setPixel(0, 3, (short) 5, (short) 10, (short) 136);
    jrle1 = readOrWriteNextRLE(rle1);
    i = 0;
    rle1it = rle1.iterator();
    for (int[] jts1: jrle1) {
      if (!rle1it.hasNext()) {
        System.out.println("    Run # " + i +
                           " missing.  (Runs are indexed from zero.)");
        System.out.println("    (In other words, your nextRun() is " +
                           "returning null when it shouldn't.)");
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore1 = 0;
        break;
      }

      int[] ts1 = rle1it.next();
      if ((jts1[0] != ts1[0]) || (jts1[1] != ts1[1]) || (jts1[2] != ts1[2])
          || (jts1[3] != ts1[3])) {
        System.out.println("    Run # " + i + " should be " + jts1[0] +
            ", " + jts1[1] + ", " + jts1[2] + ", " + jts1[3] + "."
            + "  (Runs indexed from zero.)");
        System.out.println("    Instead, it's " + ts1[0] + ", " + ts1[1] + ", "
            + ts1[2] + ", " + ts1[3]);
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore1 = 0;
        break;
      }
      i++;
    }

    System.out.println("  Setting (1, 0) to [red=1, green=18, blue=42].");
    rle1.setPixel(1, 0, (short) 1, (short) 18, (short) 42);
    jrle1 = readOrWriteNextRLE(rle1);
    i = 0;
    rle1it = rle1.iterator();
    for (int[] jts1: jrle1) {
      if (!rle1it.hasNext()) {
        System.out.println("    Run # " + i +
                           " missing.  (Runs are indexed from zero.)");
        System.out.println("    (In other words, your nextRun() is " +
                           "returning null when it shouldn't.)");
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore2 = 0;
        break;
      }

      int[] ts1 = rle1it.next();
      if ((jts1[0] != ts1[0]) || (jts1[1] != ts1[1]) || (jts1[2] != ts1[2])
          || (jts1[3] != ts1[3])) {
        System.out.println("    Run # " + i + " should be " + jts1[0] +
            ", " + jts1[1] + ", " + jts1[2] + ", " + jts1[3] + "."
            + "  (Runs indexed from zero.)");
        System.out.println("    Instead, it's " + ts1[0] + ", " + ts1[1] + ", "
            + ts1[2] + ", " + ts1[3]);
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore2 = 0;
        break;
      }
      i++;
    }

    System.out.println("  Setting (2, 0) to [red=1, green=18, blue=42].");
    rle1.setPixel(2, 0, (short) 1, (short) 18, (short) 42);
    jrle1 = readOrWriteNextRLE(rle1);
    i = 0;
    rle1it = rle1.iterator();
    for (int[] jts1: jrle1) {
      if (!rle1it.hasNext()) {
        System.out.println("    Run # " + i +
                           " missing.  (Runs are indexed from zero.)");
        System.out.println("    (In other words, your nextRun() is " +
                           "returning null when it shouldn't.)");
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore2 = 0;
        break;
      }

      int[] ts1 = rle1it.next();
      if ((jts1[0] != ts1[0]) || (jts1[1] != ts1[1]) || (jts1[2] != ts1[2])
          || (jts1[3] != ts1[3])) {
        System.out.println("    Run # " + i + " should be " + jts1[0] +
            ", " + jts1[1] + ", " + jts1[2] + ", " + jts1[3] + "."
            + "  (Runs indexed from zero.)");
        System.out.println("    Instead, it's " + ts1[0] + ", " + ts1[1] + ", "
            + ts1[2] + ", " + ts1[3]);
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore2 = 0;
        break;
      }
      i++;
    }

    System.out.println("  Setting (0, 0) to [red=1, green=18, blue=42].");
    rle1.setPixel(0, 0, (short) 1, (short) 18, (short) 42);
    jrle1 = readOrWriteNextRLE(rle1);
    i = 0;
    rle1it = rle1.iterator();
    for (int[] jts1: jrle1) {
      if (!rle1it.hasNext()) {
        System.out.println("    Run # " + i +
                           " missing.  (Runs are indexed from zero.)");
        System.out.println("    (In other words, your nextRun() is " +
                           "returning null when it shouldn't.)");
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore2 = 0;
        break;
      }

      int[] ts1 = rle1it.next();
      if ((jts1[0] != ts1[0]) || (jts1[1] != ts1[1]) || (jts1[2] != ts1[2])
          || (jts1[3] != ts1[3])) {
        System.out.println("    Run # " + i + " should be " + jts1[0] +
            ", " + jts1[1] + ", " + jts1[2] + ", " + jts1[3] + "."
            + "  (Runs indexed from zero.)");
        System.out.println("    Instead, it's " + ts1[0] + ", " + ts1[1] + ", "
            + ts1[2] + ", " + ts1[3]);
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore2 = 0;
        break;
      }
      i++;
    }

    int partiv = addScore1 + addScore2;
    if (partiv == 2) {
      System.out.println("  Test successful.");
    }

    System.out.println();
    System.out.println("Total Part IV score:  " + partiv + " out of 2.");
    System.out.println("Total Autogradable score:  " +
                       (parti + partii + partiii + partiv) + " out of 19.");

    System.out.println();
    System.out.println("(Note:  1 hand-graded point will be for your check()" +
                       " method.)");
  }
}
