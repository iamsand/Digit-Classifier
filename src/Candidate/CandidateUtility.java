package Candidate;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;

// This class allows candidates to be saved.
public class CandidateUtility {
	public void save(Candidate c, String name, String fileName) throws Exception {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
		out.println(name);
		out.println("w1");
		for (int i = 0; i < c.w1.length; i++) {
			out.print(i + " ");
			for (int j = 0; j < c.w1[0].length; j++)
				out.print(c.w1[i][j] + " ");
			out.println();
		}
		out.println();

		out.println("w2");
		for (int i = 0; i < c.w2.length; i++) {
			out.print(i + " ");
			for (int j = 0; j < c.w2[0].length; j++)
				out.print(c.w2[i][j] + " ");
			out.println();
		}
		out.println();

		out.println("b1");
		for (int i = 0; i < c.b1.length; i++)
			out.print(c.b1[i] + " ");
		out.println();

		out.println("b2");
		for (int i = 0; i < c.b2.length; i++)
			out.print(c.b2[i] + " ");
		out.println();
		out.close();
	}
}
