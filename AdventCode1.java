
public class AdventCode1 {
	public static void main(String[] args) {
		int[] nums = new int[args.length];
		for (int i = 0, j; i < args.length; i++)
			nums[i] = Integer.valueOf(args[i]);

		int n1 = 0, n2 = n1, n3 = n2, mul = 0;
		
		loop:
		for (int i = 0; i < nums.length; i++) {
			n1 = nums[i];
			for (int j = i + 1; j < nums.length; j++) {
				n2 = nums[j];
				if (n1 + n2 == 2020) {
					mul = n1 * n2;
					break loop;
				}
			}
		}
		System.out.println("" + n1 + " + " + n2 + " = " + (n1 + n2));
		System.out.println("" + n1 + " * " + n2 + " = " + mul);
		
		loop:
		for (int i = 0; i < nums.length; i++) {
			n1 = nums[i];
			for (int k = i + 1; k < nums.length; k++) {
				n2 = nums[k];
				for (int j = k + 1; j < nums.length; j++) {
					n3 = nums[j];
					if (n1 + n2 + n3 == 2020) {
						mul = n1 * n2 * n3;
						break loop;
					}
				}
			}
		}
		System.out.println("" + n1 + " + " + n2 + " + " + n3 + " = " + (n1 + n2 + n3));
		System.out.println("" + n1 + " * " + n2 + " * " + n3 + " = " + mul);
	}
}