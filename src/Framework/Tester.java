package Framework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import Candidate.Candidate;

// This class takes in a candidate and tests it against the data. It returns the number of data classified correctly.
public class Tester {

	// [index], [first is ID, rest is pixels as a vector]
	public static int[][]		tstData;
	public final static String	fileLoc	= "train.csv";

	// This is the number of datas to test for each candidate.
	public final static int NUM_TEST = 1000;
	
	public static Random rand = new Random();
	
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
		// Note that we don't need input nodes since this is exactly given in tstData.
		double[] hiddenNodes = new double[c.numHidNode];
		double[] outputNodes = new double[10];
		// Calculation for the hidden nodes
		for (int i = 0; i < hiddenNodes.length; i++) {
			hiddenNodes[i] = 0;
			for (int j = 0; j < 28 * 28; j++)
				hiddenNodes[i] += c.w1[j][i] * tstData[index][j + 1] / 255.0;
			hiddenNodes[i] += c.b1[i];
			// hiddenNodes[i] = sigmoid(hiddenNodes[i]); // TODO: ???
			// System.out.print(i + " " + hiddenNodes[i] + " "); // DEBUG
		}
		double max = Double.MIN_VALUE;
		double maxInd = -1;
		// Calculation for output nodes
		for (int i = 0; i < 10; i++) {
			outputNodes[i] = 0;
			for (int j = 0; j < hiddenNodes.length; j++)
				outputNodes[i] += c.w2[j][i] * hiddenNodes[j];
			outputNodes[i] += c.b2[i];
			// System.out.print(i + " " + outputNodes[i] + " "); // DEBUG
			if (outputNodes[i] > max) {
				max = outputNodes[i];
				maxInd = i;
			}
		}
		// System.out.println(); // DEBUG
		return maxInd == tstData[index][0];
	}

	public static int run(Candidate c) {
		// System.out.println("Running test for " + c); // DEBUG
		int count = 0;
		// TODO: Not testing whole data set. Change this when debugged.
		for (int i = 0; i < NUM_TEST; i++) {
			// System.out.println("Testing sample " + i); // DEBUG
			if (tst(c, rand.nextInt(42000)))
				count++;
		}
		return count;
	}

	// TODO: There are various ways to speed this up.
	// See
	// http://stackoverflow.com/questions/2887815/speeding-up-math-calculations-in-java
	public static double sigmoid(double x) {
		if (x < -10)
			return 0;
		else if (x > 10)
			return 1;
		else
			return 1 / (1 + Math.exp(-x));
	}
}
