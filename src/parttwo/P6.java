package parttwo;

import java.util.*;
import java.io.*;

/*
 * Given 2SAT clauses in "2sat1.txt", "2sat2.txt", "2sat3.txt",
 * "2sat4.txt", "2sat5.txt", and "2sat6.txt"
 * 
 * Determine if the clauses are satisfiable
 * 
 */

public class P6 {

	// declaring all variables necessary for input and output
	static BufferedReader br;
	static PrintWriter out;
	static StringTokenizer st;

	// number of clauses
	static int N;

	// adjacency list
	static ArrayList<ArrayList<Integer>> adj;

	// variables for Tarjan's SCC
	static int[] disc, lo, id;
	static Stack<Integer> s = new Stack<Integer>();
	static int cnt, idCnt = 1;

	public static void main (String[] args) throws IOException {
		br = new BufferedReader(new FileReader("2sat1.txt"));
		br = new BufferedReader(new FileReader("2sat2.txt"));
		br = new BufferedReader(new FileReader("2sat3.txt"));
		br = new BufferedReader(new FileReader("2sat4.txt"));
		br = new BufferedReader(new FileReader("2sat5.txt"));
		br = new BufferedReader(new FileReader("2sat6.txt"));
		out = new PrintWriter(new OutputStreamWriter(System.out));

		N = readInt();

		disc = new int[N << 1];
		lo = new int[N << 1];
		id = new int[N << 1];
		adj = new ArrayList<ArrayList<Integer>>(N << 1);

		for (int i = 0; i < 2 * N; i++)
			adj.add(new ArrayList<Integer>());

		for (int i = 0; i < N; i++) {
			int a = toNode(readInt());
			int b = toNode(readInt());

			adj.get(a ^ 1).add(b);
			adj.get(b ^ 1).add(a);
		}

		for (int i = 0; i < 2 * N; i++)
			if (disc[i] == 0)
				dfs(i);

		for (int i = 0; i < N; i++) {
			if (id[i << 1] == id[i << 1 | 1]) {
				out.println(0);
				out.close();
				return;
			}
		}
		out.println(1);
		out.close();
	}

	static void dfs (int u) {
		disc[u] = lo[u] = ++cnt;
		s.push(u);

		for (int v : adj.get(u)) {
			if (disc[v] == 0) {
				dfs(v);
				lo[u] = Math.min(lo[u], lo[v]);
			} else if (id[v] == 0) {
				lo[u] = Math.min(lo[u], disc[v]);
			}
		}

		if (disc[u] == lo[u]) {
			while (s.peek() != u) {
				id[s.peek()] = idCnt;
				s.pop();
			}
			id[s.peek()] = idCnt;
			idCnt++;
			s.pop();
		}
	}

	static int toNode (int val) {
		return val < 0 ? (- val - 1) * 2 + 1 : (val - 1) * 2;
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

	static double readDouble () throws IOException {
		return Double.parseDouble(next());
	}

	static char readCharacter () throws IOException {
		return next().charAt(0);
	}
}
