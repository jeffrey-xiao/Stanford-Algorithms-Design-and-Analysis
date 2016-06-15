package parttwo;

import java.util.*;
import java.io.*;

/*
 * Given a series of jobs (weight, length) in "jobs.txt"
 * 
 * Output the sum of weighted completion times using greedy algorithm of comparing (weight - length)
 * in decreasing order. Ties are broken by picking the higher weight first.
 */

public class P1_1 {
	
	// declaring all variables necessary for input and output
	static BufferedReader br;
	static PrintWriter out;
	static StringTokenizer st;
	
	// number of jobs
	static int N;
	
	// Job array
	static Job[] j;
	
	public static void main (String[] args) throws IOException {
		br = new BufferedReader(new FileReader("jobs.txt"));
		out = new PrintWriter(new OutputStreamWriter(System.out));
		
		N = readInt();
		j = new Job[N];
		
		for (int i = 0; i < N; i++)
			j[i] = new Job(readInt(), readInt());
		
		long currTime = 0;
		long ans = 0;
		
		Arrays.sort(j);
		
		for (int i = 0; i < N; i++) {
			currTime += j[i].length;
			ans += currTime * j[i].weight;
		}
		
		out.println(ans);
		out.close();
	}
	
	// job class
	static class Job implements Comparable<Job> {
		int weight, length;
		
		Job (int weight, int length) {
			this.weight = weight;
			this.length = length;
		}

		@Override
		public int compareTo (Job j) {
			int val1 = weight - length;
			int val2 = j.weight - j.length;
			
			if (val1 == val2)
				return j.weight - weight;
			return val2 - val1;
		}
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
}
