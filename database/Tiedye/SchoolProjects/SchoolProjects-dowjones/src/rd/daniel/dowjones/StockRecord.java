/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rd.daniel.dowjones;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;

/**
 *
 * @author Daniel
 */
public class StockRecord implements Comparable<StockRecord>{

    int condensedDate;
    int open;
    int high;
    int low;
    int close;
    long volume;
    int adjClose;

    private static final NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance();
    
}
