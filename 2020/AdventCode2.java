import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class AdventCode2 {
	public static abstract class Pass {
		final char c;

		final String data;

		Pass(char c, String data) {
			this.c = c;
			this.data = data;
		}

		public abstract boolean isValid();
	}

	public static final class Pass1 extends Pass {
		final int min, max;

		Pass1(int min, int max, char c, String data) {
			super(c, data);
			this.min = min;
			this.max = max;
		}

		public boolean isValid() {
			long count = data.codePoints().filter(ch -> ch == c).count();
			//System.out.println(data + ": " + c + " count: " + count + ", " + min + "<=" + count + "<=" + max);
			return count >= min && count <= max;
		}
	}

	public static final class Pass2 extends Pass {
		final int i, j;

		Pass2(int i, int j, char c, String data) {
			super(c, data);
			this.i = i;
			this.j = j;
		}

		public boolean isValid() {
			//System.out.println(data + ": " + c + " i: " + i + "=" + data.charAt(i - 1) + " j: " + j + "=" + data.charAt(j - 1));
			return (data.charAt(i - 1) == c) ^ (data.charAt(j - 1) == c);
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Please specify the input file path");
			return;
		}

		ArrayList<Pass> list = new ArrayList<>();
		String[] split, nums;
		for (String s : readFile(args[0])) {
			split = s.split(" ");
			nums = split[0].split("-");
			list.add(
				/*/ new Pass1( /*/
				new Pass2( //*/
					Integer.valueOf(nums[0]),
					Integer.valueOf(nums[1]),
					split[1].charAt(0),
					split[2]
				)
			);
		}

		int valids = 0;
		for (Pass pass : list) if (pass.isValid()) valids++;
		System.out.println("Valid count: " + valids);
	}

	public static ArrayList<String> readFile(String path) {
		BufferedReader reader;
		ArrayList<String> res = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();
			while (line != null) {
				res.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
		}
		return res;
	}
}