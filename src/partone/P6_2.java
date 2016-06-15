package partone;

import java.util.*;
import java.io.*;
/*
 * Given 10000 integers in "Median.txt"
 * Find the medians of the subsequences of the first kth elements (1 <= k <= 10000)
 * Sum the medians then mod 10000
 */
public class P6_2 {

	// declaring all variables necessary for input and output
	private static BufferedReader br;
	private static PrintWriter pr;
	private static StringTokenizer st;

	// size of the input
	private static final int SIZE = 10000;

	// MOD
	private static final int MOD = 10000;
	
	public static void main (String[] args) throws IOException {
		br = new BufferedReader(new FileReader("Median.txt"));
		pr = new PrintWriter(new OutputStreamWriter(System.out));

		// reading in input
		int[] in = new int[SIZE];
		for (int i = 0; i < SIZE; i++)
			in[i] = readInt();
		
		// the running median will be implemented using two priority queues
		// one max and one min
		PriorityQueue<Integer> min = new PriorityQueue<Integer>(new Comparator<Integer>(){
			@Override
			public int compare (Integer o1, Integer o2) {
				return o1 - o2;
			}
		});
		
		PriorityQueue<Integer> max = new PriorityQueue<Integer>(new Comparator<Integer>(){
			@Override
			public int compare (Integer o1, Integer o2) {
				return o2 - o1;
			}
		});
		
		if (in[0] > in[1]) {
			max.offer(in[1]);
			min.offer(in[0]);
		} else {
			max.offer(in[0]);
			min.offer(in[1]);
		}
		
		int sum = (in[0] + Math.min(in[0], in[1]))%MOD;
		for (int i = 2; i < SIZE; i++) {
			if (in[i] < max.peek())
				max.offer(in[i]);
			else
				min.offer(in[i]);
			
			if (min.size() >= max.size() + 2)
				max.offer(min.poll());
			else if (max.size() >= min.size() + 2)
				min.offer(max.poll());
			
			if (min.size() == max.size())
				sum = (sum + max.peek())%MOD;
			else
				if (max.size() > min.size())
					sum = (sum + max.peek())%MOD;
				else
					sum = (sum + min.peek())%MOD;
		}
		
		pr.println(sum);
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
	
	private static int readInt () throws IOException {
		return Integer.parseInt(next());
	}
}
