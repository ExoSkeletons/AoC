import java.io.IOException;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;

public class AdventCode10 {
	public static void main(String[] args) {
		ArrayList<Integer> adaptersJ = new ArrayList<>();
		int deviceJ = 0;
		for (int i = 0, j; i < args.length; i++) {
			j = Integer.valueOf(args[i]);
			adaptersJ.add(j);
			if (j > deviceJ) deviceJ = j;
		}
		adaptersJ.add(0);
		adaptersJ.add(deviceJ += 3);

		Collections.sort(adaptersJ);

		//for (Integer i : adaptersJ)
		//	System.out.print("" + i + " ");
		//System.out.println();

		int[] diffsSum = {0, 0, 0};
		for (int i = 0; i < adaptersJ.size() - 1; i++)
			diffsSum[(adaptersJ.get(i + 1) - adaptersJ.get(i)) - 1]++;

		//for (int i  = 0; i < diffsSum.length; i++)
		//	System.out.print("" + (i + 1) + ": " + diffsSum[i] + ", ");
		//System.out.println();

		System.out.println("" + diffsSum[0] + " * " + diffsSum[2] + " = " + diffsSum[0] * diffsSum[2]);
	}
}