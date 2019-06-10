package tests;

/**
 * Overloaded methods need to map to 
 * different symbol table elements.
 *
 */
public class Overload {


   public void queryInventoryAsync(boolean querySkuDetails) {

       (new Thread(new Runnable() {
           public void run() {

           }
       })).start();
   }

   public void queryInventoryAsync(int listener) {

   }

}
