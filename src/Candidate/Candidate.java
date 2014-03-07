package Candidate;

import java.util.Random;

import Framework.Tester;

// TODO: Currently all weights are in (-1,1). I'm not sure if this is correct. Need to verify.
public class Candidate implements Comparable<Candidate> {

	// Weights of edges from (28*28) -> (Hidden Nodes).
	public double[][]			w1;
	// Weights of edges from (Hidden Nodes) -> (10).
	public double[][]			w2;
	// Weights of edges from (bias) -> (Hidden Nodes).
	public double[]			b1;
	// Weights of edges from (bias) -> (10).
	public double[]			b2;
	public int					numHidNode;
	public int					fit;
	public double				prob;

	private static double	REG_MUT_THRESH		= .05;
	private static double	BIAS_MUT_THRESH	= .05;
	private static Random	rand					= new Random();

	public static void setRegMut(double d) {
		if (d < 0 || d > 1) {
			System.out.println("Invalid Range.");
			return;
		}
		REG_MUT_THRESH = d;
	}

	public static void setBiasMut(double d) {
		if (d < 0 || d > 1) {
			System.out.println("Invalid Range.");
			return;
		}
		BIAS_MUT_THRESH = d;
	}

	// This is making a new random Candidate.
	public Candidate(int numHidNode) {
		this.numHidNode = numHidNode;
		w1 = new double[28 * 28][numHidNode];
		for (int r = 0; r < 28 * 28; r++)
			for (int c = 0; c < numHidNode; c++)
				w1[r][c] = rand.nextDouble();
		w2 = new double[numHidNode][10];
		for (int r = 0; r < numHidNode; r++)
			for (int c = 0; c < 10; c++)
				w2[r][c] = rand.nextDouble();
		b1 = new double[numHidNode];
		for (int i = 0; i < numHidNode; i++)
			b1[i] = rand.nextDouble();
		b2 = new double[10];
		for (int i = 0; i < 10; i++)
			b2[i] = rand.nextDouble();
		calcFit();
	}

	// This allows us to load a Candidate from a file.
	public Candidate(String s) {
		// TODO
		calcFit();
	}

	// This is what breeds two Candidates.
	public Candidate(Candidate p1, Candidate p2) {
		this.numHidNode = p1.numHidNode;
		this.w1 = new double[28 * 28][numHidNode];
		for (int r = 0; r < 28 * 28; r++)
			for (int c = 0; c < numHidNode; c++) {
				w1[r][c] = (p1.w1[r][c] + p2.w1[r][c]) / 2;
				if (rand.nextDouble() < REG_MUT_THRESH)
					w1[r][c] += (1 - 2 * rand.nextDouble());
			}
		this.w2 = new double[numHidNode][10];
		for (int r = 0; r < numHidNode; r++)
			for (int c = 0; c < 10; c++) {
				w2[r][c] = (p1.w2[r][c] + p2.w2[r][c]) / 2;
				if (rand.nextDouble() < REG_MUT_THRESH)
					w2[r][c] += (1 - 2 * rand.nextDouble());

			}
		this.b1 = new double[numHidNode];
		for (int i = 0; i < numHidNode; i++) {
			b1[i] = (p1.b1[i] + p2.b1[i]) / 2;
			if (rand.nextDouble() < BIAS_MUT_THRESH)
				b1[i] += (1 - 2 * rand.nextDouble());
		}
		this.b2 = new double[10];
		for (int i = 0; i < 10; i++) {
			b2[i] = (p1.b2[i] + p2.b2[i]) / 2;
			if (rand.nextDouble() < BIAS_MUT_THRESH)
				b2[i] += (1 - 2 * rand.nextDouble());
		}
		// TODO: maybe something needs to be put in about normalizing since
		// currently weights may be < -1 or >1
		calcFit();
	}

	public void calcFit() {
		fit = Tester.run(this);
	}

	@Override
	public int compareTo(Candidate c) {
		return c.fit - this.fit;
	}
}
