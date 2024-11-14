package Engine;

/*
Author: Christine.

This class is the one where we will put mapping of most instructions to binary
till now it contains:-
1- Instructions.
2- Constuctor.
3- get type of instruction R for R-type.
						   I for I-type.
						   J for J-type.
4- startThis will result in the end with list of binary instruction (Not completed yet).
5- readInstructions read from file get list of word instructions.
6- checkOrgisfirst checks if first instruction is org.
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Initializer {
	HashMap<String,String> Rinstructions, Iinstructions, Jinstructions, Registers;
	HashMap<String, Integer> labelValues;
	ArrayList<String> wordInstructions;
	ArrayList<String> binaryInstructions;
	int startingAddress;
}
