package Framework;

import java.util.Scanner;

import Candidate.CandidateManager;

public class Main {

	// Options 
	// list = "ls";
	// save = "s";
	// run = "r";
	// load = "l";
	// quit = "q";

	public static void main(String[] args) {

		CandidateManager cm = new CandidateManager();
		Scanner sc = new Scanner(System.in);

		while (true) {
			String s = sc.next();
			switch (s) {
			case "q":
				System.exit(0);
				break;
			case "h":
				System.out.println("tst");
				break;
			case "ls":
				System.out.println("tst");
				break;
			default:
				System.out.println("tst");
				break;
			}

		}
	}
}
