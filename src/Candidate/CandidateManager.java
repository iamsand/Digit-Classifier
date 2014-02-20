package Candidate;

import java.util.ArrayList;
import java.util.Collections;

public class CandidateManager {

	public ArrayList<Candidate>	cands;
	public int							midNodes;
	public int							gen;

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

	// This replaces the current cands with a new one.
	// Breed them with roulette.
	public void breed() {
		// TODO
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
