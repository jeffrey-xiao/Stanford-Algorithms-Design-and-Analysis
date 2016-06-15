package parttwo;

import java.util.*;
import java.io.*;

/*
 * Given a series of items with (value, weight) in "knapsack1.txt"
 * 
 * Output the maximum value that you can fit in a knapsack with a certain size
 * 
 */

public class P3_1 {
	
	// declaring all variables necessary for input and output
	static BufferedReader br;
	static PrintWriter out;
	static StringTokenizer st;
	
	// number of items and weight
	static int N, W;
	
	// value and weight of items
	static int[] value, weight;
	
	// dp table
	static int[] dp;

	public static void main (String[] args) throws IOException {
		br = new BufferedReader(new FileReader("knapsack1.txt"));
		out = new PrintWriter(new OutputStreamWriter(System.out));
		
		W = readInt();
		N = readInt();
		
		value = new int[N + 1];
		weight = new int[N + 1];
		
		dp = new int[W + 1];
		
		for (int i = 1; i <= N; i++) {
			value[i] = readInt();
			weight[i] = readInt();
		}
		
		for (int i = 1; i <= N; i++)
			for (int j = W; j >= 1; j--)
				if (j - weight[i] >= 0)
					dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);

		out.println(dp[W]);
		out.close();
	}

	static String readLine () throws IOException {
		return br.readLine().trim();
	}
	
	static String next () throws IOException {
		while (st == null || !st.hasMoreTokens()) {
			st = new StringTokenizer(readLine());
		}
		return st.nextToken();
	}
	
	static int readInt () throws IOException {
		return Integer.parseInt(next());
	}
	
	static char readCharacter () throws IOException {
		return next().charAt(0);
	}
}
