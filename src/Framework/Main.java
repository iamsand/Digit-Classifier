package Framework;

import java.util.Scanner;

import Candidate.Candidate;
import Candidate.CandidateManager;
import Candidate.CandidateUtility;

public class Main {

	// -- HardCoded stuff -- //
	final static int	NUM_MID_NODES	= 75;
	final static int	NUM_CANDS		= 25;

	// ---------- COMMANDS ---------- //
	// final static String list = "ls"; // ls [num ranks]
	// final static String load = "l"; //
	// final static String save = "s"; //
	// final static String run = "r"; // r [num gens]
	// final static String quit = "q"; // q

	// ------------------------------ //

	public static void main(String[] args) throws Exception {
		long init = System.currentTimeMillis();
		Tester.init();
		CandidateManager cm = new CandidateManager(NUM_MID_NODES, NUM_CANDS);
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - init) + " ms");
		System.out.println("Ready.");
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			// TODO: is there a better way to do this?
			String s = sc.nextLine();
			String[] splits = s.split("\\s+");

			long beg = System.currentTimeMillis();
			switch (splits[0]) {
			case "ls":
				cm.prt(Integer.parseInt(splits[1]));
				break;
			case "r":
				if (cm == null)
					System.out.println("cm null.");
				else
					cm.runGen(Integer.parseInt(splits[1]));
				break;
			case "s":
				CandidateUtility.save(cm.cands.get(Integer.parseInt(splits[1])), "Test" + splits[1], cm.gen + "_" + splits[1]);
				break;
			case "q":
				sc.close();
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Argument.");
				break;
			}
			System.out.println("Elapsed time: " + (System.currentTimeMillis() - beg) + " ms");
		}
	}
}
