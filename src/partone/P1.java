package partone;

import java.util.*;
import java.io.*;

/*
 * This file contains all of the 100,000 integers between 1 and 100,000 (inclusive) in some order, with no integer repeated.
 * 
 * Your task is to compute the number of inversions in the file "IntegerArray.txt", where the ith row of the file indicates the ith entry of an array.
 */

public class P1 {
	
	// declaring all variables necessary for input and output
	private static BufferedReader br;
	private static PrintWriter pr;
	private static StringTokenizer st;
	
	// variable to be incremented while doing the divide and conquer
	private static long totalInversions = 0;
	
	// size of the input
	private static final int SIZE = 100000;
	
	public static void main (String[] args) throws IOException {
		br = new BufferedReader(new FileReader("IntegerArray.txt"));
		pr = new PrintWriter(new OutputStreamWriter(System.out));
		
		// initializing the array containing the input and the auxiliary array
		int[] a = new int[SIZE];
		int[] aux = new int[SIZE];
		
		// reading in the input
		for (int i = 0; i < SIZE; i++)
			a[i] = readInt();
		
		// counting the inversions
		countInversions(a, aux, 0, SIZE-1);
		
		// since each inversion is counted twice, we divide by two to get our final answer
		pr.println(totalInversions/2);
		pr.close();
	}
	
	// divide and conquer method that is essentially a modified version of merge sort
	private static void countInversions (int[] a, int[] aux, int lo, int hi) {
		// our base case is when we have a segment of one integer
		if (hi - lo <= 0)
			return;
		int mid = lo + (hi - lo)/2;
		
		// dividing the input into two sections and recursively solving them
		countInversions(a, aux, lo, mid);
		countInversions(a, aux, mid+1, hi);
		
		// method which does the sorting and the counting
		merge(a, aux, lo, mid, hi);
	}
	
	private static void merge (int[] a, int[] aux, int lo, int mid, int hi) {
		// transfer the necessary segment from the input array to the auxiliary array
		for (int i = lo; i <= hi; i++)
			aux[i] = a[i];
		int i = lo;
		int j = mid + 1;
		for (int x = lo; x <= hi; x++) {
			// if the lower section has been completed, then we take from the upper section
			// there won't be any inversions for this case
			if (i == mid + 1) {
				a[x] = aux[j++];
			} 
			// if the upper section has been completed, then we take from the lower section
			// each time we take from the lower section when the upper section is empty, it will generate inversions equal to the size of the upper section
			else if (j == hi + 1) {
				a[x] = aux[i++];
				totalInversions += j - (mid + 1);
			} 
			// if the lower section has the smaller element, then we take from the lower section
			// each time we take from the lower section, it will generate inversions equal to the size of the upper section that have already been taken
			else if (aux[i] < aux[j]) {
				a[x] = aux[i++];
				totalInversions += j - (mid + 1);
			} 
			// if the upper section has the smaller element, then we take from the upper section
			// each time we take from the upper section, it will generate inversions equal to the size of the lower section that have not already been taken
			else {
				a[x] = aux[j++];
				totalInversions += mid + 1 - i;
			}
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
	
	private static int readInt () throws IOException {
		return Integer.parseInt(next());
	}
}
