package Candidate;

import java.util.Random;

import Framework.Tester;

public class Candidate implements Comparable<Candidate> {

	public double[][] w1; // 28*28 -> 100
	public double[][] w2; // 100 -> 10
	public int fit;

	private static double MUTATION_THRESHHOLD = 0;

	private static Random rand = new Random();;

	public void setMut(double d) {
		MUTATION_THRESHHOLD = d;
	}

	public Candidate() {
		w1 = new double[28 * 28][100];
		for (int r = 0; r < 28 * 28; r++)
			for (int c = 0; c < 100; c++)
				w1[r][c] = rand.nextDouble();
		w2 = new double[100][10];
		for (int r = 0; r < 100; r++)
			for (int c = 0; c < 10; c++)
				w2[r][c] = rand.nextDouble();
		calcfit();
	}

	public Candidate(String s) {

	}

	public Candidate(Candidate p1, Candidate p2) {
		w1 = new double[28 * 28][100];
		w2 = new double[100][10];
		double sum1 = 0;
		double sum2 = 0;
		
		for (int r = 0; r < 28 * 28; r++)
			for (int c = 0; c < 100; c++) {
				w1[r][c] = (p1.w1[r][c] + p2.w1[r][c]) / 2;
				if (rand.nextDouble() < MUTATION_THRESHHOLD)
					w1[r][c] += (1 - 2 * rand.nextDouble());
				sum1 += w1[r][c];
			}
		for (int r = 0; r < 100; r++)
			for (int c = 0; c < 10; c++) {
				w2[r][c] = (p1.w2[r][c] + p2.w2[r][c]) / 2;
				if (rand.nextDouble() < MUTATION_THRESHHOLD)
					w2[r][c] += (1 - 2 * rand.nextDouble());
				sum2 += w2[r][c];
			}
		normalize(sum1, sum2);
		calcfit();
	}

	// fix this to accommodate the mut.
	public void normalize(double s1, double s2) {
		for (int r = 0; r < 28 * 28; r++)
			for (int c = 0; c < 100; c++) {
				w1[r][c] /= s1;
			}
		for (int r = 0; r < 100; r++)
			for (int c = 0; c < 10; c++) {
				w2[r][c] /= s2;
			}
	}

	public void calcfit() {
		fit = Tester.run(this);
	}

	@Override
	public int compareTo(Candidate c) {
		return this.fit - c.fit;
	}
}
