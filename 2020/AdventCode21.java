import java.util.Comparator;
import java.util.HashMap;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class AdventCode21 {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Please specify the input file path");
			return;
		}

		long t0, t1, t2;

		HashMap<ArrayList<String>, ArrayList<String>> foods = new HashMap<>(); // Ingredients -> known alregens
		HashMap<String, ArrayList<String>> ingMap = new HashMap<>(); // Ingredient -> Possible alergens
		HashMap<String, ArrayList<String>> alergMap = new HashMap<>(); // Alegren -> Possible ingredient
		HashMap<String, Integer> appearedMap = new HashMap<>();
		ArrayList<String> cleanIngs = new ArrayList<>();

		t0 = System.currentTimeMillis();

		{ //* Parse */
			ArrayList<String> lines = readFile(args[0]);
			ArrayList<String> ings;
			ArrayList<String> alrgs;
			ArrayList<String> possibleIngs, possibleAlrgs;
			String[] split;
			String last;
			String possibleIng;
			for (String line : lines) {
				split = line.split(" ");

				ings = new ArrayList<>();
				for (int i = 0; i < split.length - 1 && !split[i].startsWith("("); i++)
					ings.add(split[i]);
				alrgs = new ArrayList<>();
				for (int i = split.length - 1; i > 0 && !split[i].startsWith("("); i--)
					alrgs.add(split[i].replace(",", "").replace(")", ""));

				for (String ing : ings) {
					if (appearedMap.get(ing) == null)
						appearedMap.put(ing, 0);
					appearedMap.put(ing, appearedMap.get(ing) + 1);
					if (ingMap.get(ing) == null)
						ingMap.put(ing, new ArrayList<>());
					possibleAlrgs = ingMap.get(ing);	
					for (String alrg : alrgs)
						if (!possibleAlrgs.contains(alrg))
							possibleAlrgs.add(alrg);
				}
				for (String alrg : alrgs) {
					//System.out.print(alrg + " might be in " + ings + ", ");
					if (alergMap.get(alrg) == null) {
						//System.out.print("putting ");
						alergMap.put(alrg, new ArrayList<>());
					}
					possibleIngs = alergMap.get(alrg);
					//System.out.println("adding");
					for (String ing : ings)
						if (!possibleIngs.contains(ing))
							possibleIngs.add(ing);
				}

				foods.put(ings, alrgs);
				//System.out.println();
			}
		}
		t1 = System.currentTimeMillis();
		{ //* Eliminate */
			for (String ing : ingMap.keySet())
				for (ArrayList<String> ings : foods.keySet())
					if (!ings.contains(ing)) {
						//System.out.print("no " + ing + " in " + ings + ", " + ing + " can't have ");
						for (String alrg : foods.get(ings)) {
							//System.out.print(alrg + ",");
							alergMap.get(alrg).remove(ing);
							ingMap.get(ing).remove(alrg);
						}
						//System.out.println();
					}
			//System.out.println();
		}

		t2 = System.currentTimeMillis();

		System.out.println("Foods:");
		for (ArrayList<String> ings : foods.keySet())
			System.out.println(ings + " | " + foods.get(ings));
		System.out.println();
		System.out.println("Alergens:");
		System.out.println(alergMap);
		System.out.println();
		System.out.println("Ingredients:");
		System.out.println(ingMap);
		System.out.println();

		int totalAppeared = 0;
		for (String ing : ingMap.keySet())
			if (ingMap.get(ing).size() == 0) {
				cleanIngs.add(ing);
				totalAppeared += appearedMap.get(ing);
			}
		System.out.println("Clean:");
		System.out.println(cleanIngs);
		System.out.println("Appeared " + totalAppeared + " times");
		System.out.println();

		System.out.println();
		System.out.println("Parsed in " + (t1 - t0) + "ms, Computed in " + (t2 - t1) + "ms, Total " + (t2 - t0) + "ms");
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