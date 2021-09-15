import java.io.IOException;

public class AdventCode9 {
	public static void main(String[] args) {
		long[] nums = new long[args.length];
		for (int i = 0; i < nums.length; i++)
			nums[i] = Long.valueOf(args[i]);

		int erri = validateEnc(nums, 25);
		if (erri > 0) {
			System.out.println("Press enter to continue.");
			try { System.in.read(); } catch (IOException ignored) {}

			long weakness = findWeak(nums, erri);
			if (weakness > 0)
				System.out.println("Sequence weakness: " + weakness);
		}
	}

	public static int validateEnc(long[] nums, int back) {
		int i = back;
		boolean valid = false;

		for (; i < nums.length - 1; i++) {
			valid = false;
			//System.out.println("" + nums[i] + " ?= ");
			validate:
			for (int bi1 = i - back; bi1 < i; bi1++) {
				//System.out.print(""+ nums[bi1] + " + ");
				for (int bi2 = i - back; bi2 < i; bi2++) {
					if (bi1 != bi2) {
						//System.out.print("" + nums[bi2]);
						if (nums[i] == nums[bi1] + nums[bi2]) {
							valid = true;
							break validate;
						}
						//System.out.print(",");
					}
				}
				//System.out.println("");
			}
			if (!valid) {
				System.out.println("\nError at [" + i + "]: " + nums[i]);
				return i;
			}
			//System.out.println("");
		}

		System.out.println("Sequence valid");
		return -1;
	}

	public static long findWeak(long[] nums, int erri) {
		long sum, lowest, highest;

		for (int i = 0; i < nums.length - 1; i++) {
			sum = lowest = highest = nums[i];
			for (int j = i + 1; j < nums.length; j++) {
				sum += nums[j];
				
				if (sum > nums[erri]) break;

				if (nums[j] < lowest) lowest = nums[j];
				if (nums[j] > highest) highest = nums[j];

				if (sum == nums[erri]) {
					System.out.println("[" + i + "] -> [" + j + "] = " + nums[erri] + "[" + erri + "]");
					System.out.println("lowest: " + lowest + " + highest: " + highest + " = " + (lowest + highest));
					return lowest + highest;
				}
			}
		}

		return -1;
	}
}