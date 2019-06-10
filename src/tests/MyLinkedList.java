package tests;


import java.util.NoSuchElementException;


public class MyLinkedList<E> implements List<E> {
  

    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++)
            result[i] = this.get(i);

        return result;
    }


}
