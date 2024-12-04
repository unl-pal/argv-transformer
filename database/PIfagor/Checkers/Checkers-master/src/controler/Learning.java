package controler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import constant.CT;

public class Learning {
	// part of learning
	public static void learn() throws FileNotFoundException, IOException {
		String fileNamString = CT.mainFileName;

		boolean readFile = !CT.FIRST_STAGE_LEARNING;
		boolean writeFile = true;

		// inInint(temp,inFile,fileNamString);

		long start = System.nanoTime();
		MatrixBoard temp = ReadTreeFromStorage(readFile, fileNamString);
		temp.initBoard(temp);
		long end = System.nanoTime();
		long time = (end - start) / 1000000;
		System.out.println("END OF READ: " + time);
		System.out.println(temp);

		for (int i = 0; i < CT.AMOUNT_GAMES; i++) {
			while (temp.endGame() == 0) {
				temp = temp.getNextTurn();
				// System.out.println(temp);
			}
			// System.out.println("------------");
			// System.out.println(temp);
			temp = temp.getRoot();

		}
		System.out.println("AFTER LERNING STATISTIC:");
		System.out.println(temp);

		temp.showNextMove();

		start = System.nanoTime();
		// writing to file
		if (writeFile) {
			temp.writeToFile(temp, new DataOutputStream(new FileOutputStream(
					fileNamString)));
		}

		end = System.nanoTime();
		time = (end - start) / 1000000;
		System.out.println("END OF TIME: " + time);
	}

}
