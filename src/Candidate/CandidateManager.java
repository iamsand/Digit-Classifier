package Candidate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CandidateManager {

	public ArrayList<Candidate>	cands;
	public int					midNodes;
	public int					gen;

	public final static int		INCUMBENT	= 3;
	public static Random		rand		= new Random();

	public CandidateManager(int midNodes) {
		this(midNodes, 0);
	}

	public CandidateManager(int midNodes, int numCand) {
		gen = 0;
		this.midNodes = midNodes;
		cands = new ArrayList<Candidate>();
		while (cands.size() != numCand) {
			System.out.println("building... " + cands.size());
			cands.add(new Candidate(midNodes));
		}
		Collections.sort(cands);
	}

	public void add(Candidate c) {
		cands.add(c);
	}

	public void remove(int n) {
		if (n > cands.size()) {
			System.out.println("Given n is greater than size!");
			return;
		}
		for (int i = 0; i < n; i++)
			cands.remove(cands.size() - 1);
	}

	// This selects parents by roulette. I think it is quite expensive.
	public void breed() {
		double fitsum = 0;
		double[] prob = new double[cands.size() + 1];
		for (int i = 0; i < cands.size(); i++)
			fitsum += cands.get(i).fit;
		prob[0] = cands.get(0).fit / fitsum;
		for (int i = 1; i < cands.size(); i++)
			prob[i] = prob[i - 1] + cands.get(i).fit / fitsum;
		prob[prob.length - 1] = 1.01; // sentinel

		ArrayList<Candidate> newcands = new ArrayList<Candidate>();
		// Here we add incumbents to guarantee the population doesn't get worse.
		for (int i = 0; i < INCUMBENT; i++)
			newcands.add(cands.get(i));

		while (newcands.size() < cands.size()) {
			System.out.println("building... " + newcands.size());
			Candidate[] parents = new Candidate[2];
			for (int i = 0; i < 2; i++) {
				double r = rand.nextDouble();
				if (r < prob[0]) {
					parents[i] = cands.get(0);
					continue;
				}
				for (int j = 1; j < prob.length - 1; j++)
					if (r >= prob[j - 1] && r < prob[j])
						parents[i] = cands.get(j);
			}
			newcands.add(new Candidate(parents[0], parents[1]));
		}
		cands = newcands;
	}

	public void runGen() {
		breed();
		Collections.sort(cands);
		gen++;
	}

	public void runGen(int n) {
		for (int i = 0; i < n; i++) {
			System.out.println("gen " + gen);
			runGen();
		}
	}

	public void prt(int n) {
		if (n > cands.size())
			System.out.println("Given n is greater than size! Printing " + cands.size());
		for (int i = 0; i < Math.min(n, cands.size()); i++)
			System.out.println(i + " " + cands.get(i).fit);
	}
}
