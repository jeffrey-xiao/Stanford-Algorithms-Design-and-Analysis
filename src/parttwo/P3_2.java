package parttwo;

import java.util.*;
import java.io.*;

/*
 * Given a series of items with (value, weight) in "knapsack_big.txt"
 * 
 * Output the maximum value that you can fit in a knapsack with a certain size.
 * Number of items and weight are too large to compute naively.
 * 
 */

public class P3_2 {
	
	// declaring all variables necessary for input and output
	static BufferedReader br;
	static PrintWriter out;
	static StringTokenizer st;
	
	// number of items and weight
	static int N, W;
	
	// value and weight of items
	static long[] value;
	static int[] weight;
	
	// Map lookup; key = weight; value = value
	static HashMap<Integer, Long> hm;

	public static void main (String[] args) throws IOException {
		br = new BufferedReader(new FileReader("knapsack_big.txt"));
		out = new PrintWriter(new OutputStreamWriter(System.out));
		
		W = readInt();
		N = readInt();
		
		value = new long[N + 1];
		weight = new int[N + 1];
		
		hm = new HashMap<Integer, Long>();
		hm.put(0, 0l);
		
		for (int i = 1; i <= N; i++) {
			value[i] = readInt();
			weight[i] = readInt();
		}
		
		long ans = 0;
		
		for (int i = 1; i <= N; i++) {
			int M = hm.size();
			int[] currWeight = new int[M];
			long[] currValue = new long[M];
			int index = 0;
			for (Map.Entry<Integer, Long> e : hm.entrySet()) {
				currWeight[index] = e.getKey();
				currValue[index] = e.getValue();
				index++;
			}
			for (int j = 0; j < M; j++) {
				if (currWeight[j] + weight[i] <= W) {
					Long existing = hm.get(currWeight[j] + weight[i]);
					if (existing == null)
						existing = 0l;
					hm.put(currWeight[j] + weight[i], Math.max(currValue[j] + value[i], existing));
					ans = Math.max(ans, currValue[j] + value[i]);
				}
			}
		}

		out.println(ans);
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
