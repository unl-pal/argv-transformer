import util.Random
import quicksort.Sort
import bubblesort.Sort
import mergesort.Sort

object Main {

  def main(args : Array[String]) {

    runQuickSort()

    runMergeSort()

    runBubbleSort()

  }


  def runMergeSort() {

    println("\n\nMERGE SORT\n\n")

    println("Integer Sorting\n/////////////");

    val sort1 = (a: Int, b: Int) => {a >= b}

    var unsortedInt = List.fill(10)(Random.nextInt(100))

    println(unsortedInt.mkString(" "))

    println((mergesort.Sort.MergeSort(unsortedInt, sort1)).mkString(" "))
    println()

    ///////////////////////////////////

    println("String Sorting\n/////////////")

    val sort2 = (a: String, b: String) => {a <= b}

    var unsortedString = List.fill(10)(Random.alphanumeric.take(5).mkString)

    println(unsortedString.mkString(" "))

    println((mergesort.Sort.MergeSort(unsortedString, sort2)).mkString(" "))
    println()
    ////////////////////////////////////

    println("Float Testing\n/////////////")

    var sort3 = (a: Float, b: Float) => (a >= b)

    var unsortedFloat = List.fill(10)(Random.nextFloat())

    println(unsortedFloat.mkString(" "))

    println((mergesort.Sort.MergeSort(unsortedFloat, sort3)).mkString(" "))
    println()

  }

  def runBubbleSort() {

    println("\n\nBUBBLE SORT\n\n")

    println("Integer Sorting\n/////////////");

    val sort1 = (a: Int, b: Int) => {a >= b}

    var unsortedInt = Array.fill(10)(Random.nextInt(100))

    println(unsortedInt.mkString(" "))

    println((bubblesort.Sort.BubbleSort(unsortedInt, sort1)).mkString(" "))
    println()

    ///////////////////////////////////

    println("String Sorting\n/////////////")

    val sort2 = (a: String, b: String) => {a <= b}

    var unsortedString = Array.fill(10)(Random.alphanumeric.take(5).mkString)

    println(unsortedString.mkString(" "))

    println((bubblesort.Sort.BubbleSort(unsortedString, sort2)).mkString(" "))
    println()
    ////////////////////////////////////

    println("Float Testing\n/////////////")

    var sort3 = (a: Float, b: Float) => (a >= b)

    var unsortedFloat = Array.fill(10)(Random.nextFloat())

    println(unsortedFloat.mkString(" "))

    println((bubblesort.Sort.BubbleSort(unsortedFloat, sort3)).mkString(" "))
    println()

  }

  def runQuickSort() {

    println("\n\nQUICK SORT\n\n")

    println("Integer Sorting\n/////////////");

    val sort1 = (a: Int, b: Int) => {a >= b}

    var unsortedInt = List.fill(10)(Random.nextInt(100))

    println(unsortedInt.mkString(" "))

    println((quicksort.Sort.QuickSort(unsortedInt, sort1)).mkString(" "))
    println()

    ///////////////////////////////////

    println("String Sorting\n/////////////")

    val sort2 = (a: String, b: String) => {a <= b}

    var unsortedString = List.fill(10)(Random.alphanumeric.take(5).mkString)

    println(unsortedString.mkString(" "))

    println((quicksort.Sort.QuickSort(unsortedString, sort2)).mkString(" "))
    println()
    ////////////////////////////////////

    println("Float Testing\n/////////////")

    var sort3 = (a: Float, b: Float) => (a >= b)

    var unsortedFloat = List.fill(10)(Random.nextFloat())

    println(unsortedFloat.mkString(" "))

    println((quicksort.Sort.QuickSort(unsortedFloat, sort3)).mkString(" "))
    println()

  }

}

