// CSE 143, Homework 4: Tag
//
// Instructor-provided support class.  You should not modify this code!

/**
 * Each TagNode object represents a single node in a linked list
 * for a game of Tag.
 */
public class TagNode {
    public String name;        // this person's name
    public String tagger;      // name of who tagged this person (null if alive)
    public TagNode next;  // next node in the list (null if none)
}

