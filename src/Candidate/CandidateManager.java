package Candidate;

import java.util.ArrayList;
import java.util.Collections;
import Framework.Tester;

public class CandidateManager {

	private ArrayList<Candidate> cands;

	public CandidateManager() {
		this(0);
	}

	public CandidateManager(int n) {
		Tester.init();
		cands = new ArrayList<Candidate>();
		while (cands.size() != n)
			cands.add(new Candidate());
	}

	// This replaces the current cands with a new one.
	public void breed() {

	}

	public void runGen() {
		breed();
		Collections.sort(cands);
	}

	public void runGen(int n) {
		for (int i = 0; i < n; i++)
			runGen();
	}

	public void prt(int n) {
		if (n > cands.size())
			System.out.println("n is greater than size! printing "
					+ cands.size());
		for (int i = 0; i < Math.min(n, cands.size()); i++)
			System.out.println(i + " " + cands.get(i).fit);

	}
}
