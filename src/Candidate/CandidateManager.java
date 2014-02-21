package Candidate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CandidateManager {

	public ArrayList<Candidate>	cands;
	public int					midNodes;
	public int					gen;

	public static Random		rand	= new Random();

	public CandidateManager(int midNodes) {
		this(midNodes, 0);
	}

	public CandidateManager(int midNodes, int n) {
		this.midNodes = midNodes;
		cands = new ArrayList<Candidate>();
		while (cands.size() != n)
			cands.add(new Candidate(midNodes));
		gen = 0;
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
		double probsum = 0;
		double[] prob = new double[cands.size()];
		for (int i = 0; i < cands.size(); i++)
			fitsum += cands.get(i).fit;
		for (int i = 0; i < cands.size(); i++) {
			prob[i] = probsum += cands.get(i).fit / fitsum;
			probsum += prob[i];
		}

		ArrayList<Candidate> newcands = new ArrayList<Candidate>();
		while (newcands.size() < cands.size()) {
			Candidate[] parents = new Candidate[2];
			for (int i = 0; i < 2; i++) {
				double r = rand.nextDouble();
				for (int j = 0; j < prob.length - 1; j++) 
					if (r >= prob[j] && r < prob[j + 1]) 
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
		for (int i = 0; i < n; i++)
			runGen();
	}

	public void prt(int n) {
		if (n > cands.size())
			System.out.println("Given n is greater than size! Printing " + cands.size());
		for (int i = 0; i < Math.min(n, cands.size()); i++)
			System.out.println(i + " " + cands.get(i).fit);
	}
}
