package parttwo;

import java.util.*;
import java.io.*;

/*
 * Given a complete undirected graph in "clustering1.txt"
 * 
 * Output the maximum spacing of a k-clustering where k = 4
 * 
 */

public class P2_1 {
	
	// declaring all variables necessary for input and output
	static BufferedReader br;
	static PrintWriter out;
	static StringTokenizer st;
	
	static final int K = 4;
	
	// number of nodes and edges
	static int N;
	
	// array of edges
	static Edge[] e;
	
	// id and sz for DSU
	static int[] id, sz;

	public static void main (String[] args) throws IOException {
		br = new BufferedReader(new FileReader("clustering1.txt"));
		out = new PrintWriter(new OutputStreamWriter(System.out));
		
		N = readInt();

		e = new Edge[N * (N - 1) / 2];
		id = new int[N];
		sz = new int[N];
		
		for (int i = 0; i < N; i++) {
			id[i] = i;
			sz[i] = 1;
		}
		
		for (int i = 0; i < N * (N - 1) / 2; i++) {
			int u = readInt() - 1;
			int v = readInt() - 1;
			int cost = readInt();
			e[i] = new Edge(u, v, cost);
		}

		Arrays.sort(e);
		
		long ans = 1 << 30;
		int nodesLeft = N;
		
		for (int i = 0; i < N * (N - 1) / 2; i++) {
			if (nodesLeft == K) {
				if (find(e[i].u) != find(e[i].v))
					ans = Math.min(ans, e[i].cost);
				continue;
			}

			int idU = find(e[i].u);
			int idV = find(e[i].v);
			
			if (idU != idV) {
				merge(idU, idV);
				nodesLeft--;
			}
		}
		
		out.println(ans);
		out.close();
	}
	
	// DSU find with path compression
	static int find (int u) {
		return u == id[u] ? u : (id[u] = find(id[u]));
	}
	
	// DSU merge
	static void merge (int u, int v) {
		if (sz[u] > sz[v]) {
			sz[u] += sz[v];
			id[v] = u;
		} else {
			sz[v] += sz[u];
			id[u] = v;
		}
	}
	
	// edge class
	static class Edge implements Comparable<Edge> {
		int u, v, cost;
		
		Edge (int u, int v, int cost) {
			this.u = u;
			this.v = v;
			this.cost = cost;
		}

		@Override
		public int compareTo (Edge e) {
			return cost - e.cost;
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
