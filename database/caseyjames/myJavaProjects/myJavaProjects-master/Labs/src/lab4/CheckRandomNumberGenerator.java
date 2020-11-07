package lab4;

import java.util.Arrays;

/**
 * This class checks to see how well a random number generator works. It tests
 * several important properties, including the following.
 * 
 * the number of zeros after 10000 tries 
 * how many tries before all the numbers between 1-1000 are generated 
 * the number of unique numbers generated in 1000 tries 
 * the number of odd-even pairs 
 * the number of odd-odd pairs 
 * the number of even-odd pairs 
 * the number of even-even pairs 
 * the average number generated
 */
 public class CheckRandomNumberGenerator 
 {
  // for testing only generate numbers between 0 and max_size
  private final int max_size = 10000;

  // keeps track the oddness/evenness of consecutive numbers
  private int odd_odd_count_ = 0;

  private int odd_even_count_ = 0;

  private int even_even_count_ = 0;

  private int even_odd_count_ = 0;

  // average of all the random numbers
  private long average_of_all_ = 0;

  private int counter_ = 0;

  // histogram of the random numbers being generated
  private int[] histogram_ = new int[this.max_size];

  private int previous_ = -1;

  // number of boxes in the histogram that are still
  // empty after a thousand numbers are generated
  private int number_of_zeros_after_ten_thousand = 0;

  // number of boxes filled
  private int numbers_filled = 0;

  // number of tries taken to fill in box
  private int numbers_generated = 0;
} // end class
