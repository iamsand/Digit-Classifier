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
	
	public double[]			w1sum;
	public double[]			w2sum;
	public double				b1sum;
	public double				b2sum;



	private static double	REG_MUT_THRESH		= .05;
	private static double	BIAS_MUT_THRESH	= .05;
	private static Random	rand					= new Random();

	// This is making a new random Candidate.
	public Candidate(int numHidNode) {
		this.numHidNode = numHidNode;
		w2sum = new double[numHidNode];
		w1sum = new double[28*28];
		w1 = new double[28 * 28][numHidNode];
		for (int r = 0; r < 28 * 28; r++)
			for (int c = 0; c < numHidNode; c++) {
				w1[r][c] = rand.nextDouble();
				w1sum[r] += w1[r][c];
			}
		w2 = new double[numHidNode][10];
		for (int r = 0; r < numHidNode; r++)
			for (int c = 0; c < 10; c++) {
				
				w2[r][c] = rand.nextDouble();
				w2sum[r] += w2[r][c];
			}

		b1 = new double[numHidNode];
		for (int i = 0; i < numHidNode; i++) {
			b1[i] = rand.nextDouble();
			b1sum += b1[i];
		}
		b2 = new double[10];
		for (int i = 0; i < 10; i++) {
			b2[i] = rand.nextDouble();
			b2sum += b2[i];
		}
		normalize();
		calcFit();
	}

	// This allows us to load a Candidate from a file.
	public Candidate(String s) {
		// TODO
		normalize();
		calcFit();
	}

	// This is what breeds two Candidates.
	public Candidate(Candidate p1, Candidate p2) {
		this.numHidNode = p1.numHidNode;
		w2sum = new double[numHidNode];
		w1sum = new double[28*28];
		this.w1 = new double[28 * 28][numHidNode];
		for (int r = 0; r < 28 * 28; r++)
			for (int c = 0; c < numHidNode; c++) {
				w1[r][c] = (p1.w1[r][c] + p2.w1[r][c]) / 2;
				if (rand.nextDouble() < REG_MUT_THRESH) {
					w1[r][c] = Math.max(w1[r][c] + (1 - 2 * rand.nextDouble()), w1[r][c]);
				}
				w1sum[r] += w1[r][c];
			}
		this.w2 = new double[numHidNode][10];
		for (int r = 0; r < numHidNode; r++)
			for (int c = 0; c < 10; c++) {
				w2[r][c] = (p1.w2[r][c] + p2.w2[r][c]) / 2;
				if (rand.nextDouble() < REG_MUT_THRESH) {
					w2[r][c] += (1 - 2 * rand.nextDouble());
					w2[r][c] = Math.max(w2[r][c] + (1 - 2 * rand.nextDouble()), 0);
				}
				w2sum[r] += w2[r][c];

			}
		this.b1 = new double[numHidNode];
		for (int i = 0; i < numHidNode; i++) {
			b1[i] = (p1.b1[i] + p2.b1[i]) / 2;
			if (rand.nextDouble() < BIAS_MUT_THRESH) {
				b1[i] = Math.max(b1[i] + (1 - 2 * rand.nextDouble()), 0);
			}
			b1sum += b1[i];
		}
		this.b2 = new double[10];
		for (int i = 0; i < 10; i++) {
			b2[i] = (p1.b2[i] + p2.b2[i]) / 2;
			if (rand.nextDouble() < BIAS_MUT_THRESH) {
				b2[i] = Math.max(b2[i] + (1 - 2 * rand.nextDouble()), 0);

			}
			b2sum += b2[i];
		}
		normalize();
		calcFit();
	}

	// This makes the weights per node sum to 1.
	public void normalize() {
		for (int i = 0; i < 28 * 28; i++)
			for (int j = 0; j < numHidNode; j++)
				w1[i][j] /= w1sum[i];

		for (int i = 0; i < numHidNode; i++)
			for (int j = 0; j < 10; j++)
				w2[i][j] /= w2sum[i];

		for (int i = 0; i < numHidNode; i++)
			b1[i] /= b1sum;

		for (int i = 0; i < 10; i++)
			b2[i] /= b2sum;
	}

	public void calcFit() {
		fit = Tester.run(this);
	}

	@Override
	public int compareTo(Candidate c) {
		return c.fit - this.fit;
	}
}
