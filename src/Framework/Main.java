package Framework;

import java.util.Scanner;

import Candidate.Candidate;
import Candidate.CandidateManager;
import Candidate.CandidateUtility;

public class Main {

	// ---------- COMMANDS ---------- //
	// final static String list = "ls"; // ls [num ranks]
	// final static String load = "l"; //
	// final static String makeNew = "n"; // n [num hidden nodes]
	// final static String save = "s"; //
	// final static String setMut = "m";
	// final static String run = "r"; // r [num gens]
	// final static String quit = "q"; // q

	// ------------------------------ //

	public static void main(String[] args) throws Exception {
		Tester.init();
		CandidateManager cm = null;
		Scanner sc = new Scanner(System.in);

		while (true) {
			// TODO: is there a better way to do this?
			String s = sc.nextLine();
			String[] splits = s.split("\\s+");

			long beg = System.currentTimeMillis();
			switch (splits[0]) {
			case "n":
				cm = new CandidateManager(Integer.parseInt(splits[1]), Integer.parseInt(splits[2]));
				break;
			case "ls":
				if (cm == null)
					System.out.println("cm null.");
				else
					cm.prt(Integer.parseInt(splits[1]));
				break;
			case "r":
				if (cm == null)
					System.out.println("cm null.");
				else
					cm.runGen(Integer.parseInt(splits[1]));
				break;
			case "s":
				CandidateUtility.save(cm.cands.get(Integer.parseInt(splits[1])), "Test" + splits[1], cm.gen+"_"+splits[1]);
				break;
			case "m":
				if (splits[1] == "0") {
					Candidate.setRegMut(Double.parseDouble(splits[2]));
				} else {
					Candidate.setBiasMut(Double.parseDouble(splits[2]));
				}
				break;
			case "q":
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Argument.");
				break;
			}
			System.out.println(System.currentTimeMillis() - beg + " ms");

			// if (splits[0].equals(list) && splits.length == 2 &&
			// splits[1].matches("-?\\d+")) {
			// if (cm == null)
			// System.out.println("cm null.");
			// else
			// cm.prt(Integer.parseInt(splits[1]));
			// } else if (splits[0].equals(load)) {
			//
			// } else if (splits[0].equals(makeNew)) {
			// cm = new CandidateManager(Integer.parseInt(splits[1]),
			// Integer.parseInt(splits[2]));
			// } else if (splits[0].equals(save)) {
			//
			// } else if (splits[0].equals(setMut)) {
			//
			// } else if (splits[0].equals(run) && splits.length == 2 &&
			// splits[1].matches("-?\\d+")) {
			// if (cm == null)
			// System.out.println("cm null.");
			// else
			// cm.runGen(Integer.parseInt(splits[1]));
			// } else if (splits[0].equals(quit)) {
			// System.exit(0);
			// } else {
			// System.out.println("Invalid arguments.");
			// }
		}
	}
}
