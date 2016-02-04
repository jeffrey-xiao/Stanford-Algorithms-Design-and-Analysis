import java.util.*;
import java.io.*;
/*
 * The file "QuickSort.txt" contains all of the integers between 1 and 10,000 (inclusive, with no repeats) in unsorted order. 
 * The integer in the ith row of the file gives you the ith entry of an input array.
 * Your task is to compute the total number of comparisons used to sort the given input file by QuickSort. 
 * As you know, the number of comparisons depends on which elements are chosen as pivots, so we'll ask you to explore three different pivoting rules.
 * You should not count comparisons one-by-one. 
 * Rather, when there is a recursive call on a subarray of length m, you should simply add m-1 to your running total of comparisons. 
 * (This is because the pivot element is compared to each of the other m-1 elements in the subarray in this recursive call.)
 * 
 * WARNING: The Partition subroutine can be implemented in several different ways, and different implementations can give you differing numbers of comparisons. 
 * For this problem, you should implement the Partition subroutine exactly as it is described in the video lectures (otherwise you might get the wrong answer)
 * 
 * Pivot rule 1: Always use first element
 */
public class P2 {
	
	// declaring all variables necessary for input and output
	private static BufferedReader br;
	private static PrintWriter pr;
	private static StringTokenizer st;
	
	// variable to be incremented while sorting
	private static long totalComparisons;
	
	// variable that represents which partition to use
	private static int partitionState;
	
	// size of the input
	private static final int SIZE = 10000;
	public static void main (String[] args) throws IOException {
		br = new BufferedReader(new FileReader("QuickSort.txt"));
		pr = new PrintWriter(new OutputStreamWriter(System.out));
		
		int[] input = new int[SIZE];
		
		// reading input
		for (int i = 0; i < SIZE; i++)
			input[i] = readInt();
		
		// PIVOT 1
		int[] a = Arrays.copyOf(input, SIZE);
		partitionState = 1;
		totalComparisons = 0;
		sort(a, 0, SIZE - 1);
		pr.println(totalComparisons);
		
		// PIVOT 2
		a = Arrays.copyOf(input, SIZE);
		partitionState = 2;
		totalComparisons = 0;
		sort(a, 0, SIZE - 1);
		pr.println(totalComparisons);

		// PIVOT 3
		a = Arrays.copyOf(input, SIZE);
		partitionState = 3;
		totalComparisons = 0;
		sort(a, 0, SIZE - 1);
		pr.println(totalComparisons);
		
		pr.close();
	}
	// quick sort function
	private static void sort (int[] a, int lo, int hi) {
		if (hi - lo <= 0)
			return;
		
		// partitioning the array
		int mid = partition(a, lo, hi);
		
		// recursively sorting the two sides
		sort(a, lo, mid-1);
		sort(a, mid+1, hi);
		
		// incrementing the comparison count
		totalComparisons += hi - lo;
	}
	// partitions the array
	private static int partition (int[] a, int lo, int hi) {
		// pointer that divides the two parts of the partitioned array
		int i = lo + 1;
		
		// index of pivot
		int pi = lo;
		if (partitionState == 2) {
			pi = hi;
		} else if (partitionState == 3) {
			int mid = (hi + lo)/2;
			if ((a[lo] >= a[mid] && a[lo] <= a[hi]) || (a[lo] <= a[mid] && a[lo] >= a[hi]))
				pi = lo;
			else if ((a[mid] >= a[lo] && a[mid] <= a[hi]) || (a[mid] <= a[lo] && a[mid] >= a[hi]))
				pi = mid;
			else
				pi = hi;
		}
		// swap the pivot to the start
		swap(a, pi, lo);
		
		// partition it
		for (int j = lo + 1; j <= hi; j++)
			if (a[j] < a[lo])
				swap(a, i++, j);
		
		// swap the pivot so that it divides the two arrays
		swap(a, i-1, lo);
		
		return i-1;
	}
	// auxiliary function for swapping two elements
	private static void swap (int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
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
