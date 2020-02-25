import java.util.ArrayList;
import java.util.Scanner;

public class Fibonacci {

	public static int fibRecursive(int nFib) {
		if (nFib == 0) {
			return 0;
		} else if (nFib == 1) {
			return 1;
		}
		return fibRecursive(nFib - 1) + fibRecursive(nFib - 2);
	}

	public static int fibIterative(int nFib) {
		int oldOldNumber;
		int oldNumber = 0;
		int currNumber = 1;

		if (nFib == 0) {
			return 0;
		} else {
			for (int i = 0; i < nFib; i++) {

				oldOldNumber = oldNumber;
				oldNumber = currNumber;
				currNumber = oldOldNumber + oldNumber;

			}
			return currNumber;
		}
	}

	public static double timedRecursive(int nFib) {
		double timeStart = System.nanoTime();
		System.out.println(fibRecursive(nFib));
		return (System.nanoTime() - timeStart);
	}

	public static double timedIterative(int nFib) {
		double timeStart = System.nanoTime();
		System.out.println(fibIterative(nFib));
		return (System.nanoTime() - timeStart);
	}

	public static void main(String[] args) {

		ArrayList<Double> iterativeTimes = new ArrayList<Double>();
		ArrayList<Double> recursiveTimes = new ArrayList<Double>();

		Scanner input = new Scanner(System.in);

		System.out.println("Iterative tests:");
		for (int i = 0; i < 30; i++) {
			iterativeTimes.add(timedIterative(i));
			System.out.println(iterativeTimes.get(i));
		}

		System.out.println("Recursive tests:");
		for (int i = 0; i < 31; i++) {
			recursiveTimes.add(timedRecursive(i));
			System.out.println(recursiveTimes.get(i));
		}

		input.close();

	}

}
