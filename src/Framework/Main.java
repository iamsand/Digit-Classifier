package Framework;

import java.util.Scanner;

import Candidate.CandidateManager;

public class Main {

	// ---------- COMMANDS ---------- //
	final static String	list		= "ls";	// ls [num ranks]
	final static String	load		= "l";	//
	final static String	makeNew	= "n";	// n [num hidden nodes]
	final static String	save		= "s";	//
	final static String	setMut	= "m";
	final static String	run		= "r";	// r [num gens]
	final static String	quit		= "q";	// q

	// ------------------------------ //

	public static void main(String[] args) throws Exception {
		Tester.init();
		CandidateManager cm = null;
		Scanner sc = new Scanner(System.in);

		while (true) {
			// TODO: is there a better way to do this?
			String s = sc.next();
			String[] splits = s.split("\\s+");
			if (splits[0] == list && splits.length == 2 && splits[1].matches("-?\\d+")) {
				if (cm == null)
					System.out.println("cm null.");
				else
					cm.prt(Integer.parseInt(splits[1]));
			} else if (splits[0] == load) {

			} else if (splits[0] == makeNew && splits.length == 2 && splits[1].matches("-?\\d+")) {

			} else if (splits[0] == save) {

			} else if (splits[0] == setMut) {

			} else if (splits[0] == run && splits.length == 2 && splits[1].matches("-?\\d+")) {
				if (cm == null)
					System.out.println("cm null.");
				else
					cm.runGen(Integer.parseInt(splits[1]));
			} else if (splits[0] == quit) {
				System.exit(0);
			} else {
				System.out.println("Invalid arguments.");
			}
		}
	}
}
