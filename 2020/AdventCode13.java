import java.util.Arrays;
import java.util.Set;
import java.util.TreeMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Iterator;
import java.util.ArrayList;

public  class AdventCode13 {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int startTime;
		int[] busses, times;
		int earliestI = 0;

		System.out.print("Enter earliest time: ");
		startTime = Integer.valueOf(in.readLine());

		System.out.print("Enter busses: ");
		String[] notes = in.readLine().split(",");
    	busses = new int[notes.length];
		for (int i = 0; i < notes.length; i++)
			try {
       			busses[i] = Integer.parseInt(notes[i]);
    		} catch (NumberFormatException e) {
    			busses[i] = 0;
    		}

    	System.out.print("Busses: ");
    	for (int bus : busses) if (bus > 0) System.out.print("" + bus + ",");
    	System.out.println();

    	times = new int[busses.length];
    	for (int i = 0, bus; i < busses.length; i++)
    		if (busses[i] > 0) {
    			bus = busses[i];
    			times[i] = ((int) ((float) startTime / bus) + 1) * bus;
    			if (times[i] < times[earliestI]) earliestI = i;
    		}

    	int wait = times[earliestI] - startTime;

    	System.out.println("Earliest time: " + times[earliestI] + ", on bus: " + busses[earliestI]);
    	System.out.println("Minutes to wait: " + wait);
    	System.out.println("" + busses[earliestI] + "*" + wait + "=" + busses[earliestI] * wait);

    	System.out.println();

    	


		int[] orderdBussesTmp = busses.clone(), orderdBusses, orderdBussesIndexes;
    	Arrays.sort(orderdBussesTmp);
    	
    	int non0I = 0;
    	for (; non0I < orderdBussesTmp.length && orderdBussesTmp[non0I] == 0; non0I++);
    	orderdBusses = new int[orderdBussesTmp.length - non0I];
    	System.arraycopy(orderdBussesTmp, non0I, orderdBusses, 0, orderdBusses.length);
    	
    	int lastBus = 0, lastTime = 0;
    	orderdBussesIndexes = new int[orderdBusses.length];
    	for (int i = 0; i < orderdBusses.length; i++) {
    		for (int index = i; index < busses.length; index++)
    			if (orderdBusses[i] == busses[index]) {
    				orderdBussesIndexes[i] = index;
    				break;
    			}
    		lastBus = busses[i];
    	}
    	
    	System.out.print("Busses:  ");
    	for (int bus : busses) System.out.print("" + bus + ",");
    	System.out.println();
    	System.out.print("Indexes: ");
    	for (int i = 0; i < busses.length; i++) System.out.print("" + i + ",");
    	System.out.println();
    	System.out.print("Orderd:  ");
    	for (int bus : orderdBusses) System.out.print("" + bus + ",");
    	System.out.println();
    	System.out.print("Indexes: ");
    	for (int i = 0; i < orderdBussesIndexes.length; i++) System.out.print("" + orderdBussesIndexes[i] + ",");
    	System.out.println();
    	
    	System.out.print("Press enter to begin the slightly improved brute force");
    	in.readLine();
    	long t0 = System.currentTimeMillis(), t;
		int factor = 0;
		long remain;
		int len = orderdBusses.length - 1;
    	force:
    	while(true) {
    		factor++;
    		lastTime = lastBus * factor;
    		//System.out.print("\n[" + factor + "] ");
    		for (int i = len, sub, bus; i >= 0; i--)
    			if ((bus = orderdBusses[i]) > 0) {
    				sub = len - orderdBussesIndexes[i];
    				remain = (lastTime - sub) % bus;
    				//System.out.print("" + lastBus + "*" + factor + "-" + sub + "%" + bus + "=" + remain + " ");
    				if (remain != 0)
    					continue force;
    			}
    		break;
    	}
    	t = System.currentTimeMillis() - t0;
    	System.out.println();
    	System.out.println("Timestamp: " + (lastTime - len));
    	System.out.println("Compute time " + t + "ms");
	}
}