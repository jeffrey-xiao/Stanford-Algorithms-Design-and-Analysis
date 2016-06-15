package parttwo;

import java.util.*;
import java.io.*;

/*
 * Given an implicit undirected graph in "clustering_big.txt"
 * (each node is represented as a bit string and the cost between two nodes
 * is the Hamming distance between their bit strings)
 * 
 * Output the maximum k such that there is a k-clustering with spacing at least 3
 * 
 */

public class P2_2 {
	
	// declaring all variables necessary for input and output
	static BufferedReader br;
	static PrintWriter out;
	static StringTokenizer st;
	
	// number of nodes and the size of each node's bit string
	static int N, M;
	
	// occurrences for each bit string
	static int[] occ;

	// id and sz for DSU
	static int[] id, sz;

	public static void main (String[] args) throws IOException {
		br = new BufferedReader(new FileReader("clustering_big.txt"));
		out = new PrintWriter(new OutputStreamWriter(System.out));
		
		N = readInt();
		M = readInt();
		
		occ = new int[1 << M];
		id = new int[1 << M];
		sz = new int[1 << M];
		
		for (int i = 0; i < 1 << M; i++) {
			id[i] = i;
			sz[i] = 1;
		}
		
		for (int i = 0; i < N; i++) {
			int bitmask = 0;
			for (int j = 0; j < M; j++)
				bitmask = bitmask << 1 | (readCharacter() - '0');
			occ[bitmask]++;
		}
		
		int connectionsMade = 0;
		
		for (int i = 0; i < 1 << M; i++) {
			if (occ[i] == 0)
				continue;
			int total = occ[i] - 1;
			for (int j = 0; j < M; j++) {
				for (int k = j + 1; k < M; k++) {
					if (occ[i ^ (1 << j) ^ (1 << k)] > 0 && merge(i, i ^ (1 << j) ^ (1 << k)))
						total += 1;
				}
				if (occ[i ^ (1 << j)] > 0 && merge(i, i ^ (1 << j)))
					total += 1;
			}
			connectionsMade += total;
		}
		
		out.println(N - connectionsMade);
		out.close();
	}

	// DSU find with path compression
	static int find (int u) {
		return u == id[u] ? u : (id[u] = find(id[u]));
	}
	
	// DSU merge
	static boolean merge (int u, int v) {
		u = find(u);
		v = find(v);
		
		if (u == v)
			return false;
		
		if (sz[u] > sz[v]) {
			sz[u] += sz[v];
			id[v] = u;
		} else {
			sz[v] += sz[u];
			id[u] = v;
		}
		return true;
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
