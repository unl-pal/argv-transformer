/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbclient;

import ejbatm.BankRemote;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import javax.ejb.EJB;

/**
 *
 * @author Ken
 */
public class Client {
    @EJB
    private static BankRemote bank;

    /**
     * The class simulate the card reader device in ATM machine
     */
    private class CardReader {
    }
}
