###What is this?

This is an implementation of Quick Sort, Merge Sort, and Bubble Sort in both Scala, Clojure, and Java. It is meant to compare the ease of programming, coding style, and efficiency of each language. It should be noted that I have had more experience with Java than with Scala or Clojure prior to performing this test, so the number of compiler errors should be taken with a grain of salt. Although the few errors with Clojure should maybe indicate an easier programming experience.

#####Number of compile/runtime errors during the writing of each algorithm for each language. 
*Note: *If there were multiple errors in any single compile/run, only one was fixed at a time, and then the code was recompiled. This gives a more accurate estimate rather than fixing multiple errors at once.

| Algorithm | Java | Scala|Clojure|
|-----------|------|------|-------|
|Bubble Sort|  25  |   40 | 16    |
|Quick Sort |   7  |   20 |  10   |
|Merge Sort |   5  |  27  |   4   |

To compile the java section

```
javac Main.java
```

To compile the scala section

```
scalac Main.scala quicksort/Sort.scala mergesort/Sort.scala bubblesort/Sort.scala

```

The main branch of the repo has a main function that runs a sample of each sort with a small list of ten items to demonstrate functionality.

To view the time comparison, switch to the TimeComparison branch, which is used as follows.

```
java Main <# Items per List>
```

```
scala Main <# Items per List>

```
or for a clojure time comparison, switch to the clojure-time branch, which is used as follows.

```
./clj main.clj

```
This performs a Quick, Merge, and Bubble sort on lists of Integers, String, and Floats. Each list has randomized entries and a random order.

The output is a single line of times in Milliseconds in the following order:

* Integer QuickSort time
* Float QuickSort time
* String QuickSort time
* Integer MergeSort time
* Float MergeSort time
* String MergeSort time
* Integer BubbleSort time
* Float BubbleSort time
* String BubbleSort time

Using a series of bash and matlab scripts I was able to visualize some of the data as seen below for scala and java.

#####Note: Clojure is viewed separately at the bottom due to drastic differences in implementation and resulting performance

![QuickSort](graphing/quicksort.png)
![MergeSort](graphing/mergesort.png)
![BubbleSort](graphing/bubblesort.png)

#####Data Discussion

First comparing the various sorting methods. 

We can see that MergeSort and QuickSort are very similar in performance, notice their y-axis. The Bubble sort is terrible in all cases, but especially it seems for Sorting java strings. This is odd because the way I implemented the random strings, java only selects from lower case letters, while the scala implementation selects from Upper, lower, and numeric characters. Having the larger character set I was almost certain scala would be slower. The method I used for both languages is near identical for bubble sort.

Next notice the similarities between MergeSort and QuickSort, not only are their axis at the same range, but the shape of each line pair across graphs seems to be a mirror image, if only a slight bit scaled. The implementation is also very language specific in each case. The java implementations stayed fairly close to the pseudo code for each algorithm, where as the scala implementations use much of scala's syntactic sugar. 

Despite this differences in implementation, the similarity in performance is clear, leading me to believe that scala's synactic sugar is little more than a programming novelty, and lends no benefit to the actual running of the program.

Comparing the 2 language's Merge and Quick Sorts, it can be seen that scala's implementation is slower than java's in all types, if only by a little bit.

Im granting java the winner in this poorly judged contest, however; a chance for redemption is due once my scala programming skills improve, as perhaps my implementation does not do the language justice right now.

#####Implementation Discussion

This entire project was a bit rushed but alot was learnt. Firstly Ive said it before, I am not a master of scala, or a journeyman, or an apprentice, or a padawan. Im terrible at it. Martin Odersky's Scala By Example has helped immensely.

>Scala 

Was difficult, although the implementations are fairly readily available online, it has taken me days to fully understand each one and tailor it to my needs. The syntactic sugar that scala 'offers', coupled with the functional OOP overlap make for a very steep learning curve and frustrating learning experience. That being said I can see how it could be considered a powerful language once it was fully understood.

One of the pitfalls I ran into was reoccurring stack overflow errors in the merge sort with lists above 4000 items. This was happening because I was not implementing tail-recursion. Once I switched to a tail recursive implementation it ran fine with upwards of 100000 items.

Something that I really like about scala is being able to pass an anonymous compare function to any of these sort implementations. It makes for some very unique and easy to implement sorting definitions that would be otherwise many lines of code in java.

Scala's code was much shorter overall, but in my opinion it has gone too far. It has sacrificed a quick programming experience for the Scala Master, at the cost of 0 readability for everyone else. And if code reuse is as important as I think it is, which it is, thats bad.

>Java

The java implementation of each algorithm was fairly straight forward. I basically stuck to a line by line copy of the pseudo code on wikipedia for each algorithm, just in Java form. That said the comparator generics were kind of a mashup. I had the generic type extend Comparable, and the class itself extend Comparator. This led to the easiest implementation of a custom comparator later and only ended up being 2 lines of code in the program as seen below.

```
public int compare(T a, T b) {

	return a.compareTo(b);

}
```

This allows a simple boolean to passed globally to change an order to ascending or descending. This is great although is one of java's many pitfalls in that I had to hack together 2 comparators to make it work.

The main function is a bit of a mess, which is my own fault, it was an afterthought because I originally implemented each Sort as its own executable class and then abstracted the execution after. I will 'read first code later' next time.

####Clojure

Results 

![](graphing/clojure-all.png)
![](graphing/clojure-2.png)
![](graphing/clojure-3.png)

#####Data Discussion

The performance of clojure varied greatly for different sorting algorithms. As you can see from the first graph, the bubble sort time was so overwhelmingly poor, that it made the other graphs not viewable at that scale. This could be attributed to the fact that it is a poor algorithm, as well as my implementation being poor.

After removing bubble sort from the graphs, we can see merge sort and quick sort in the second graph. Although the times are much more reasonable, the implementation of merge sort is still overwhelmingly slow compared to quick sort.

The final graph shows quick sort on its own. Not only were both merge sort and quick sort quicker than both the java and scala implementations, but quick sort seems to have a constant time complexity of less that 0.1 milliseconds. This is incredibly quick and a perfect example of the power of a functional programming language.

#####Implementation Discussion

Implementing the comparators in clojure was accomplished by defining a function called cust-comp that was used in each sorting algorithm, changing this function changes the order and style of sorting. Clojure's 'compare' must be used to maintain generic nature for any custom comparator that's implemented.

Generics in general were easy to achieve with clojure, given that language allows for heterogeneous collections. The only trick was mentioned above, namely the custom comparator requiring the use of 'compare' rather than >, <, or ==.

Compared to scala and java, the overall programming experience with clojure is more enjoyable. It is far easier to get much more accomplished in fewer lines of code, despite my minimal knowledge of the language. Although scala is a functional language, I believe clojure is closer to being a pure lisp, and therefore I find it much easier to think in a single paradigm and use it, rather than trying to combine it with an OOP approach.

The performance on large data structures was incredible for quicksort, and mediocre for the others. I have realized that there are multiple ways of accomplishing the same things in clojure, (3 types of recursion for instance), so I will chalk this up to my algorithms needing reimplementing.

One thing I will say against clojure is that I probably had more compiler errors than I needed, simply because I was not able to fix them when they came up. Some of the errors are so obscure and difficult to read that I was unable to decipher them. I'm sure this comes as experience with functional programming and clojure, but it makes the learning curve much steeper than it needs to be.

Overall it is a very workable language and quite enjoyable to learn.