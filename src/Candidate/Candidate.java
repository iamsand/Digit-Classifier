package Candidate;

import java.util.Random;

import Framework.Tester;

public class Candidate implements Comparable<Candidate> {

	// 28*28 -> midNodes
	public double[][]			w1;
	// midNodes -> 10
	public double[][]			w2;
	public int					fit;

	private static double	MUTATION_THRESHHOLD	= 0;
	private static Random	rand						= new Random();

	public void setMut(double d) {
		MUTATION_THRESHHOLD = d;
	}

	public Candidate(int midNodes) {
		w1 = new double[28 * 28][midNodes];
		for (int r = 0; r < 28 * 28; r++)
			for (int c = 0; c < midNodes; c++)
				w1[r][c] = rand.nextDouble();
		w2 = new double[midNodes][10];
		for (int r = 0; r < midNodes; r++)
			for (int c = 0; c < 10; c++)
				w2[r][c] = rand.nextDouble();
		calcFit();
	}

	public Candidate(String s) {
		calcFit();
	}

	public Candidate(Candidate p1, Candidate p2) {
		int midNodes = p1.w1[0].length;
		w1 = new double[28 * 28][midNodes];
		w2 = new double[midNodes][10];

		for (int r = 0; r < 28 * 28; r++)
			for (int c = 0; c < midNodes; c++) {
				w1[r][c] = (p1.w1[r][c] + p2.w1[r][c]) / 2;
				if (rand.nextDouble() < MUTATION_THRESHHOLD)
					w1[r][c] += (1 - 2 * rand.nextDouble());

			}
		for (int r = 0; r < midNodes; r++)
			for (int c = 0; c < 10; c++) {
				w2[r][c] = (p1.w2[r][c] + p2.w2[r][c]) / 2;
				if (rand.nextDouble() < MUTATION_THRESHHOLD)
					w2[r][c] += (1 - 2 * rand.nextDouble());

			}
		calcFit();
	}

	public void calcFit() {
		fit = Tester.run(this);
	}

	@Override
	public int compareTo(Candidate c) {
		return this.fit - c.fit;
	}
}
