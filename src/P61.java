import java.util.*;
import java.io.*;
/*
 * Given 1 million integers in "sum.txt"
 * Find the possible target sums (-10000 <= T <= 10000) that can be achieved with two distinct integers
 */
public class P61 {

	// declaring all variables necessary for input and output
	private static BufferedReader br;
	private static PrintWriter pr;
	private static StringTokenizer st;


	// size of the input
	private static final int SIZE = 1000000;

	public static void main (String[] args) throws IOException {
		br = new BufferedReader(new FileReader("sum.txt"));
		pr = new PrintWriter(new OutputStreamWriter(System.out));

		// reading in input
		long[] in = new long[SIZE];
		for (int i = 0; i < SIZE; i++)
			in[i] = readLong();
		Arrays.sort(in);
		
		// valid array represents which targets are valid
		boolean[] valid = new boolean[20001];
		
		// doing an amortized linear time sweep to determine all valid targets
		// for integer in[i], we are trying to find the bound (j1, j2) where all integers
		// from j1 - j2 in array "in" will yield valid targets
		
		// initializing the bounds
		int j1 = 0;
		while (j1 < SIZE && in[0] + in[j1] < -10000)
			j1++;
		int j2 = SIZE-1;
		main : for (int i = 0; i < SIZE; i++) {
			// determining upper bound
			while (in[i] + in[j2] > 10000) {
				j2--;
				if (j2 <= i)
					break main;
			}
			
			// determining lower bound
			while (j1 - 1 >= 0 && in[i] + in[j1-1] >= -10000)
				j1--;
			
			// setting the valid ones to true
			for (int j = j1; j <= j2; j++)
				valid[(int)(in[j] + in[i] + 10000)] = true;
		}
		int total = 0;
		for (int i = 0; i <= 20000; i++)
			total += valid[i] ? 1 : 0;
		pr.println(total);
		pr.close();
	}

	private static String readLine () throws IOException {
		return br.readLine().trim();
	}
	private static String next () throws IOException {
		while (st == null || !st.hasMoreTokens()) {
			st = new StringTokenizer(readLine());
		}
		return st.nextToken();
	}
	private static long readLong () throws IOException {
		return Long.parseLong(next());
	}
}
