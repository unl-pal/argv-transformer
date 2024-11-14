package quicksort {


  object Sort {

    def QuickSort[T](list:List[T], sort:(T, T)=>Boolean):List[T] = {

      list match {

        case listh::listt => {
        
          val (low, high, pivot)=partition(list, sort)
          QuickSort(low, sort):::(pivot::QuickSort(high, sort))
        
        }
        case Nil => { list }

          

      }

    }

    def partition[T](list: List[T], sort:(T,T)=>Boolean) = {

      val pivot = list.head
      var low: List[T] = List()
      var high: List[T] = List()

      val checkSwap = ( i: T ) => {

        if(sort(i,pivot)) 
          low = i::low
        else
          high = i::high

      }

      list.tail.foreach(checkSwap)
      (low, high, pivot)

    }

  }

}
