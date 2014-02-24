package Framework;

import java.io.BufferedReader;
import java.io.FileReader;

import Candidate.Candidate;

// This class takes in a candidate and tests it against the data. It returns the number of data classified correctly.
public class Tester {

	// [index], [first is ID, rest is pixels as a vector]
	static int[][]		tstData;
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
		// Note that we don't need input nodes since this is exactly given in tstData.
		double[] hiddenNodes = new double[c.numHidNode];
		double[] outputNodes = new double[10];
		// Calculation the hidden nodes
		for (int i = 0; i < hiddenNodes.length; i++) {
			hiddenNodes[i] = 0;
			for (int j = 0; j < 28 * 28; j++)
				hiddenNodes[i] += c.w1[j][i] * tstData[index][j + 1] / 255.0;
			hiddenNodes[i] += c.b1[i];
			hiddenNodes[i] = sigmoid(hiddenNodes[i]);
		}
		double max = Double.MIN_VALUE;
		double maxInd = -1;
		// Calculation for output nodes
		for (int i = 0; i < 10; i++) {
			outputNodes[i] = 0;
			for (int j = 0; j < hiddenNodes.length; j++)
				outputNodes[i] += c.w2[j][i] * hiddenNodes[j];
			outputNodes[i] += c.b2[i];
			outputNodes[i] = sigmoid(outputNodes[i]);
			if (outputNodes[i] > max) {
				max = outputNodes[i];
				maxInd = i;
			}

		}
		if (maxInd == tstData[index][0])
			return true;
		return false;
	}

	public static int run(Candidate c) {
		int count = 0;
		// TODO: Only testing 100 for now. Change this when debugged.
		for (int i = 0; i < 1000; i++)
			if (tst(c, i))
				count++;
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
