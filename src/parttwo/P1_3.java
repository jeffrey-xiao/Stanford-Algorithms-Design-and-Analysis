package parttwo;

import java.util.*;
import java.io.*;

/*
 * Given an undirected graph in "edges.txt"
 * 
 * Output the overall cost of the minimum spanning tree of the graph using Prim's algorithm
 * 
 */

public class P1_3 {
	
	// declaring all variables necessary for input and output
	static BufferedReader br;
	static PrintWriter out;
	static StringTokenizer st;
	
	// number of nodes and edges
	static int N, M;
	
	// array denoting whether a node has been covered by the current MST
	static boolean[] vis;
	
	// adjacency list
	static ArrayList<ArrayList<Edge>> adj;
	
	public static void main (String[] args) throws IOException {
		br = new BufferedReader(new FileReader("edges.txt"));
		out = new PrintWriter(new OutputStreamWriter(System.out));
		
		N = readInt();
		M = readInt();
		
		adj = new ArrayList<ArrayList<Edge>>(N);
		vis = new boolean[N];
		
		for (int i = 0; i < N; i++)
			adj.add(new ArrayList<Edge>());

		for (int i = 0; i < M; i++) {
			int u = readInt() - 1;
			int v = readInt() - 1;
			int cost = readInt();
			
			adj.get(u).add(new Edge(v, cost));
			adj.get(v).add(new Edge(u, cost));
		}
		
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
		long ans = 0;
		
		vis[0] = true;
		for (Edge e : adj.get(0))
			pq.add(e);
		
		while (!pq.isEmpty()) {
			Edge curr = pq.poll();
		
			if (vis[curr.dest])
				continue;
		
			vis[curr.dest] = true;
			ans += curr.cost;
			
			for (Edge next : adj.get(curr.dest))
				if (!vis[next.dest])
					pq.offer(next);
		}
		
		out.println(ans);
		out.close();
	}
	
	// edge class
	static class Edge implements Comparable<Edge> {
		int dest, cost;
		
		Edge (int dest, int cost) {
			this.dest = dest;
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
