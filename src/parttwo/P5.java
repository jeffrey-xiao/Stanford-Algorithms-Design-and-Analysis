package parttwo;

import java.util.*;
import java.io.*;

/*
 * Given series of cities (x, y) in "tsp.txt"
 * 
 * Output the minimum cost of a traveling salesman tour
 * 
 */

public class P5 {
	
	// declaring all variables necessary for input and output
	static BufferedReader br;
	static PrintWriter out;
	static StringTokenizer st;
	
	// number of nodes and edges
	static int N;
	
	// position of cities
	static double[] x, y;
	
	// distance between two cities
	static double[][] dist;
	
	public static void main (String[] args) throws IOException {
		br = new BufferedReader(new FileReader("tsp.txt"));
		out = new PrintWriter(new OutputStreamWriter(System.out));
		
		N = readInt();
		
		x = new double[N];
		y = new double[N];
		
		dist = new double[N][N];
		
		for (int i = 0; i < N; i++) {
			x[i] = readDouble();
			y[i] = readDouble();
			for (int j = 0; j < i; j++) {
				dist[i][j] = dist[j][i] = (float) Math.sqrt((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j]) * (y[i] - y[j]));
			}
		}		

		out.println(minDist());
		out.close();
	}

	static double minDist () {
		double[][] dp = new double[1 << (N - 1)][N - 1];

		for (int i = 0; i < 1 << (N - 1); i++)
			for (int j = 0; j < N - 1; j++)
				dp[i][j] = 1 << 29;

		for (int i = 0; i < N - 1; i++) {
			dp[1 << i][i] = dist[N - 1][i];
		}

		for (int i = 0; i < 1 << (N - 1); i++)
			for (int j = 0; j < N - 1; j++)
				if ((i & 1 << j) != 0)
					for (int k = 0; k < N - 1; k++)
						if ((i & 1 << k) == 0)
							dp[i ^ 1 << k][k] = Math.min(dp[i ^ 1 << k][k], dp[i][j] + dist[j][k]);

		double min = 1 << 30;
		for (int i = 0; i < N - 1; i++)
			min = Math.min(min, dp[(1 << (N - 1)) - 1][i] + dist[i][N - 1]);

		return min;
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
