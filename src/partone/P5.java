package partone;

import java.util.*;
import java.io.*;

/*
 * Given adjacency list in file "dijkstraData.txt"
 * Compute shortest distances to {7,37,59,82,99,115,133,165,188,197}
 */

public class P5 {
	
	// declaring all variables necessary for input and output
	private static BufferedReader br;
	private static PrintWriter pr;
	// size of the input
	private static final int SIZE = 200;
	
	//adjacency list
	private static ArrayList<ArrayList<Edge>> adj = new ArrayList<ArrayList<Edge>>();
	
	// array that contains the distances we want
	private static int[] target = {7,37,59,82,99,115,133,165,188,197};
	
	public static void main (String[] args) throws IOException {
		br = new BufferedReader(new FileReader("dijkstraData.txt"));
		pr = new PrintWriter(new OutputStreamWriter(System.out));
		
		// initializing the adjacency list
		for (int i = 0; i < SIZE; i++)
			adj.add(new ArrayList<Edge>());
		// reading in input
		for (int i = 0; i < SIZE; i++) {
			StringTokenizer curr = new StringTokenizer(br.readLine());
			curr.nextToken();
			while (curr.hasMoreTokens()) {
				String[] next = curr.nextToken().split(",");
				adj.get(i).add(new Edge(Integer.parseInt(next[0])-1, Integer.parseInt(next[1])));
			}
		}
		// initializing the array to maintain the minimum distance from start
		int[] min = new int[SIZE];
		for (int i = 0; i < SIZE; i++)
			min[i] = 1 << 30;
		PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();
		pq.offer(new Vertex(0, 0));
		min[0] = 0;
		// running Dijkstra's 
		while (!pq.isEmpty()) {
			Vertex curr = pq.poll();
			for (Edge next : adj.get(curr.index)) {
				if (min[next.dest] <= min[curr.index] + next.cost)
					continue;
				min[next.dest] = min[curr.index] + next.cost;
				pq.offer(new Vertex(next.dest, min[next.dest]));
			}
		}
		// printing results
		for (int i = 0; i < target.length; i++)
			pr.println(min[target[i]-1]);
		
		pr.close();
	}
	
	// object that represents Vertex
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
	
	// object that represents Edge
	static class Edge {
		int dest, cost;
		Edge (int dest, int cost) {
			this.dest = dest;
			this.cost = cost;
		}
	}
}
