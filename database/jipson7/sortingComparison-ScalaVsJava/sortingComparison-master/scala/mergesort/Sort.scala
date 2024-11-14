package mergesort {


  object Sort {

    def MergeSort[T](xs: List[T], sort: (T, T) => Boolean): List[T] = { 
      def merge(left: List[T], right: List[T], sort: (T, T) => Boolean): Stream[T] = (left, right) match {
        case (x :: xs, y :: ys) if sort(x, y) => Stream.cons(x, merge(xs, right, sort))
        case (x :: xs, y :: ys) => Stream.cons(y, merge(left, ys, sort))
        case _ => if (left.isEmpty) right.toStream else left.toStream
      }
      val n = xs.length / 2 
      if (n == 0) xs 
      else { 
        val (ys, zs) = xs splitAt n 
        merge(MergeSort(ys, sort), MergeSort(zs, sort), sort).toList
      } 
    }

  }

}
