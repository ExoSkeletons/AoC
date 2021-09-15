import java.lang.IllegalStateException;
import java.util.ArrayList;

public class AdventCode8 {
	public static final String ACC = "acc", JMP = "jmp", NOP = "nop";

	public static void main(String[] args) {
		int acc = 0;

		for (int i = 0; i < args.length; i+=2) {
			if (args[i].equals(JMP) || args[i].equals(NOP))
				try {
					System.out.print("trying "+i/2+"["+i+"] "+args[i]+" "+args[i+1]+"->");
					if (args[i].equals(JMP)) args[i] = NOP;
					else if (args[i].equals(NOP)) args[i] = JMP;
					System.out.println(args[i]);

					acc = run(args);
					System.out.println("terminated successfully, acc = "+acc);
			
					return;
				} catch(IllegalStateException e) {
					//System.out.println(e.getMessege());
					System.out.print("failed, reverting "+args[i]+" "+args[i+1]+"->");
					if (args[i].equals(JMP)) args[i] = NOP;
					else if (args[i].equals(NOP)) args[i] = JMP;
					System.out.println(args[i]+" "+args[i+1]);
				}
		}
	}

	public static int run(String[] args) throws IllegalStateException {
		int acc = 0;
		int i = 0;
		ArrayList<Integer> executed = new ArrayList<>();
		String op;
		int arg;

		//System.out.println("args len = "+args.length);

		loop:
		do {
			//System.out.println("");
			//System.out.println("index = "+i/2+" ["+i+"], acc = "+acc);

			if (executed.contains(i)){
				String err = "!repeat index "+i/2;
				System.out.println(err);
				throw new IllegalStateException(err);
			}
			executed.add(i);

			op = args[i];
			arg = Integer.valueOf(args[i + 1]);

			//System.out.println("op = \""+op+"\", arg = "+arg);

			if (op.equals(JMP)){
				//System.out.println("> jumping "+i/2+(arg<0?"":"+")+arg +"="+((i+arg*2))/2);
				i += arg * 2;
			} else {
				if (op.equals(ACC)){
					//System.out.println("> acc += "+arg+"");
					acc += arg;
				} else {
					//System.out.println(">nop");
				}
				i+= 2;
			}
		} while (i < args.length);

		System.out.println("final acc = "+acc);
		return acc;
	}
}