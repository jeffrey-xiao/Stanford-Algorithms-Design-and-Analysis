package parttwo;

import java.util.*;
import java.io.*;

/*
 * Given directed graphs in "g1.txt", "g2.txt", "g3.txt", and "large.txt"
 * 
 * Output the minimum distance between two nodes out of all the graphs
 * 
 */

public class P4 {
	
	// declaring all variables necessary for input and output
	static BufferedReader br;
	static PrintWriter out;
	static StringTokenizer st;
	
	// number of nodes and edges
	static int N, M;
	
	// adjacency list
	static ArrayList<ArrayList<Edge>> adj;
	
	// node weights
	static int[] h;
	
	// minimum distance between any two nodes
	static int[][] dist;
	
	// priority queue for Dijkstra's
	static PriorityQueue<Vertex> pq;
	
	public static void main (String[] args) throws IOException {
//		br = new BufferedReader(new FileReader("g1.txt"));
//		br = new BufferedReader(new FileReader("g2.txt"));
//		br = new BufferedReader(new FileReader("g3.txt"));
		br = new BufferedReader(new FileReader("large.txt"));
		out = new PrintWriter(new OutputStreamWriter(System.out));
		
		N = readInt();
		M = readInt();
		
		h = new int[N + 1];
		dist = new int[N + 1][N + 1];
		adj = new ArrayList<ArrayList<Edge>>(N + 1);

		Arrays.fill(h, 1 << 30);
		
		for (int i = 0; i <= N; i++)
			adj.add(new ArrayList<Edge>());
		
		for (int i = 1; i <= N; i++)
			adj.get(0).add(new Edge(i, 0));
		
		for (int i = 0; i < M; i++)
			adj.get(readInt()).add(new Edge(readInt(), readInt()));

		h[0] = 0;
		
		for (int i = 0; i < N; i++)
			for (int j = 0; j <= N; j++)
				for (Edge e : adj.get(j))
					if (h[e.dest] > e.cost + h[j])
						h[e.dest] = e.cost + h[j];

		for (int i = 0; i <= N; i++)
			for (Edge e : adj.get(i))
				if (h[e.dest] > e.cost + h[i]) {
					out.println("MIN COST CYCLE DETECTED");
					out.close();
					return;
				}
		
		for (int i = 1; i <= N; i++)
			dist[i] = getPath(i);
		
		int ans = 1 << 30;
		
		for (int i = 1; i <= N; i++)
			for (int j = i + 1; j <= N; j++)
				ans = Math.min(ans, dist[i][j] - h[i] + h[j]);
		
		out.println(ans);
		out.close();
	}

	static int[] getPath (int src) {
		int[] dist = new int[N + 1];
		
		for (int i = 0; i <= N; i++)
			dist[i] = 1 << 30;
		dist[src] = 0;
		
		pq = new PriorityQueue<Vertex>();
		pq.offer(new Vertex(src, 0));
		
		while (!pq.isEmpty()) {
			Vertex curr = pq.poll();
			
			for (Edge next : adj.get(curr.index)) {
				if (dist[next.dest] <= curr.cost + next.cost + h[curr.index] - h[next.dest])
					continue;
				
				dist[next.dest] = curr.cost + next.cost + h[curr.index] - h[next.dest];
				pq.offer(new Vertex(next.dest, dist[next.dest]));
			}
		}
		
		return dist;
	}

	static class Vertex implements Comparable<Vertex> {
		int index, cost;

		Vertex (int index, int cost) {
			this.index = index;
			this.cost = cost;
		}

		@Override
		public int compareTo (Vertex o) {
			return cost - o.cost;
		}
	}

	static class Edge {
		int dest, cost;

		Edge (int dest, int cost) {
			this.dest = dest;
			this.cost = cost;
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
	
	static double readDouble () throws IOException {
		return Double.parseDouble(next());
	}
	
	static char readCharacter () throws IOException {
		return next().charAt(0);
	}
}
