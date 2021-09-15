import java.util.HashMap;
import java.io.IOException;
import java.io.IOException;
import java.lang.Integer;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AdventCode15 {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		long t0, t1;
		ArrayList<Integer> nums = new ArrayList<>();
		for (String s : args[0].split(","))
			nums.add(Integer.valueOf(s));

		System.out.print("Enter Lim: ");
		final int lim = Integer.valueOf(in.readLine());
		t0 = System.currentTimeMillis();

		HashMap<Integer,Integer> indexMap = new HashMap<>();
		for (int i = 0; i < nums.size(); i++)
			indexMap.put(nums.get(i), i);

		int num = nums.get(nums.size() - 1);
		for (int i = nums.size() - 1, next; ; i++) {
			if (i + 1 == lim) break;
			//System.out.print("Turn " + (i + 2) + " i: " + i + " num: " + num + " ");
			if (indexMap.get(num) == null) {
				//System.out.print("No index found for " + num);
				next = 0;
			} else {
				next = i - indexMap.get(num);
				//System.out.print("Found " + num + " at " + indexMap.get(num));
			}
			//System.out.println(", Setting new index of " + num + " at " + i);
			indexMap.put(num, i);
			//System.out.println("next: " + next);
			num = next;
		}
		t1 = System.currentTimeMillis() - t0;
		System.out.println("At Lim: " + num);
		System.out.println("Computed in " + t1 + "ms");
	}
}