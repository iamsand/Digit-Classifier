package Framework;

import java.io.BufferedReader;
import java.io.FileReader;

import Candidate.Candidate;

// This class takes in a candidate and tests it against the data. It returns the number of data classified correctly.
public class Tester {

	// [index], [first is ID, rest is pixels as a vector]
	static int[][]			tstData;
	final static String	fileLoc	= "train.csv";

	// This methods reads the train.csv so that we can access it as an int[][].
	public static void init() throws Exception {
		tstData = new int[42000][1 + 28 * 28];
		BufferedReader br = new BufferedReader(new FileReader("res/" + fileLoc));
		String s = br.readLine();
		String[] splts = null;
		for (int i = 0; i < 42000; i++) {
			s = br.readLine();
			splts = s.split(",");
			for (int j = 0; j < 1 + 28 * 28; j++)
				tstData[i][j] = Integer.parseInt(splts[j]);
		}
		br.close();
	}

	// This basically runs the neural network calculation.
	public static boolean tst(Candidate c, int index) {
		// Note that we don't need input nodes since this is exactly given in
		int[] hiddenNodes = new int[c.numHidNode];
		int[] outputNodes = new int[10];
		// TODO
		return false;
	}

	public static int run(Candidate c) {
		int count = 0;
		for (int i = 0; i < 42000; i++)
			if (tst(c, i))
				count++;
		return count;
	}

	// There are various ways to speed this up.
	// See http://stackoverflow.com/questions/2887815/speeding-up-math-calculations-in-java
	// TODO
	public static double sigmoid(double x) {
		if (x < -10)
			return 0;
		else if (x > 10)
			return 1;
		else
			return 1 / (1 + Math.exp(-x));
	}
}
