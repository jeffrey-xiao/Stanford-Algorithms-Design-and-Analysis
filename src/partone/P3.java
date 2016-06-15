package partone;

import java.util.*;
import java.io.*;

/*
 * Given an adjacency list in the file "kargerMinCut.txt"
 * Run the randomized contraction algorithm many times and print the min
 */

public class P3 {

	// declaring all variables necessary for input and output
	private static BufferedReader br;
	private static PrintWriter pr;

	// size of the input
	private static final int SIZE = 200;
	private static final int ITERATIONS = 10000;

	// DSU ids
	private static int[] id = new int[SIZE];
	
	// DSU sizes
	private static int[] size = new int[SIZE];

	public static void main (String[] args) throws IOException {
		br = new BufferedReader(new FileReader("kargerMinCut.txt"));
		pr = new PrintWriter(new OutputStreamWriter(System.out));
		// list that represents all the edges1
		ArrayList<Edge> edges = new ArrayList<Edge>();

		//reading in input and populating edge list
		for (int i = 0; i < SIZE; i++) {
			StringTokenizer curr = new StringTokenizer(readLine());
			curr.nextToken();
			while (curr.hasMoreTokens()) {
				int conn = Integer.parseInt(curr.nextToken())-1;
				if (i < conn)
					edges.add(new Edge(i, conn));
			}
		}
		int globalMin = 1 << 30;
		for (int i = 0; i < ITERATIONS; i++) {
			// initializing DSU
			for (int j = 0; j < SIZE; j++) {
				id[j] = j;
				size[j] = 1;
			}
			int currSize = edges.size();
			int nodesLeft = SIZE;
			// run the algorithm until there are two nodes left
			while (nodesLeft > 2) {
				// select an edge at random
				int ran = (int)(Math.random() * currSize);
				int rx = find(edges.get(ran).x);
				int ry = find(edges.get(ran).y);
				
				// if edge doesn't belong to two nodes in the same component, then contract it
				if (rx != ry) {
					merge(rx, ry);
					nodesLeft--;
				}
				// "removing" an edge
				Edge temp = edges.get(--currSize);
				edges.set(currSize, edges.get(ran));
				edges.set(ran, temp);
			}
			int cnt = 0;
			for (Edge e : edges) {
				if (find(e.x) != find(e.y))
					cnt++;
			}
			globalMin = Math.min(globalMin, cnt);
		}
		pr.println(globalMin);
		pr.close();
	}
	
	// DSU find
	static int find (int x) {
		return x == id[x] ? x : (id[x] = find(id[x]));
	}
	
	// DSU merge
	static void merge (int x, int y) {
		if (size[x] > size[y]) {
			size[x] += size[y];
			id[y] = x;
		} else {
			size[y] += size[x];
			id[x] = y;
		}
	}
	
	// object that represents an edge
	static class Edge {
		int x, y;
		Edge (int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	private static String readLine () throws IOException {
		return br.readLine().trim();
	}
}
