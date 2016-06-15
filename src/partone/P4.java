package partone;

import java.util.*;
import java.io.*;

/*
 * Given an adjacency list in the file "SCC.txt"
 * Compute the SCCs and output the largest 5
 */

public class P4 {

	// declaring all variables necessary for input and output
	private static BufferedReader br;
	private static PrintWriter pr;
	private static StringTokenizer st;


	// size of the input
	private static final int SIZE = 875714;

	// counter for tarjans
	private static int cnt = 0;
	
	// disc for tarjans
	private static int[] disc = new int[SIZE];
	
	// low for tarjans
	private static int[] lo = new int[SIZE];
	
	// stack for tarjans and boolean to check in stack
	private static Stack<Integer> curr = new Stack<Integer>();
	private static boolean[] instack = new boolean[SIZE];
	
	// ids and number for the SCCs
	private static int[] id = new int[SIZE];
	private static int num = 0;
	
	// adjacency list
	private static ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
	
	public static void main (String[] args) throws IOException {
		br = new BufferedReader(new FileReader("SCC.txt"));
		pr = new PrintWriter(new OutputStreamWriter(System.out));

		// initializing
		for (int i = 0; i < SIZE; i++) {
			adj.add(new ArrayList<Integer>());
			disc[i] = -1;
		}
		
		// reading in input
		String in;
		while ((in = br.readLine()) != null) {
			StringTokenizer curr = new StringTokenizer(in);
			int x = Integer.parseInt(curr.nextToken()) - 1;
			int y = Integer.parseInt(curr.nextToken()) - 1;
			adj.get(x).add(y);
		}
		
		// computing all the SCCs
		for (int i = 0; i < SIZE; i++)
			if (disc[i] == -1)
				dfs(i);
		
		// disc will now be the sizes of the SCCs
		disc = new int[SIZE];
		
		for (int i = 0; i < SIZE; i++)
			disc[id[i]]++;
		
		PriorityQueue<Integer> largestFive = new PriorityQueue<Integer>(new Comparator<Integer>(){
			@Override
			public int compare (Integer o1, Integer o2) {
				return o1 - o2;
			}
		});
		
		for (int i = 0; i < num; i++) {
			largestFive.offer(disc[i]);
			if (largestFive.size() > 5)
				largestFive.poll();
		}
		
		for (int i = 0; i < 5; i++) {
			if (largestFive.size() < 5 - i)
				pr.println(0);
			else
				pr.println(largestFive.poll());
		}
		pr.close();
	}
	
	private static void dfs (int u) {
		disc[u] = lo[u] = cnt++;
		curr.push(u);
		instack[u] = true;
		for (Integer v : adj.get(u)) {
			if (disc[v] == -1) {
				dfs(v);
				lo[u] = Math.min(lo[u], lo[v]);
			} else if (instack[v]) {
				lo[u] = Math.min(lo[u], disc[v]);
			}
		}
		// setting the ids for each node
		if (disc[u] == lo[u]) {
			while (curr.peek() != u) {
				instack[curr.peek()] = false;
				id[curr.pop()] = num;
			}
			instack[u] = false;
			id[curr.pop()] = num++;
		}
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
	@SuppressWarnings ("unused")
	private static int readInt () throws IOException {
		return Integer.parseInt(next());
	}
}
