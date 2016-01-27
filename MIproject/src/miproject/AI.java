package miproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AI {

	public static double SlopeOne(ArrayList<Boolean> x, ArrayList<Boolean> y) {

		double a = 0, b = 0, c = 0;
		boolean m, n;
		for (int i = 0; i < x.size(); i++) {

			m = x.get(i);
			n = y.get(i);

			if (m && n)
				a++;
			if (n)
				b++;
			if (m)
				c++;

		}
		return a / (Math.sqrt(b) * Math.sqrt(c));
	}

	public static ArrayList<Integer> bestOnes(int indexA,
			ArrayList<ArrayList<Boolean>> boolMatrix, int limit) {
		ArrayList<Integer> best = new ArrayList<Integer>();
		HashMap<Double,Integer> slopeOnes = new HashMap<Double, Integer>();
		for (int i = 0; i < boolMatrix.size(); i++) {
			if (i != indexA) {
				slopeOnes.put(
						SlopeOne(boolMatrix.get(indexA), boolMatrix.get(i)),i);
			}
			
		}
		List<Double> values = new ArrayList<Double>(slopeOnes.keySet());
		Collections.sort(values,Collections.reverseOrder());
		for(int i=0; i<limit; i++){
			best.add(slopeOnes.get(values.get(i)));
			System.out.println(values.get(i));
		}
		return best;
	}
}
