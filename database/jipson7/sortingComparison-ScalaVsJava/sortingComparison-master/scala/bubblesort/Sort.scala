package bubblesort {


  object Sort {

    def BubbleSort[T](list:Array[T], sort:(T, T)=>Boolean):Array[T] = {

      val length = list.size - 1 

      for (i <- 1 to length) {

        for (j <- length to i by -1) {

          if (sort(list(j), list(j - 1))) {

            var holder = list(j)
            list(j) = list(j - 1)
            list(j - 1) = holder


          }

        }

      }

      list

    }

  }

}
