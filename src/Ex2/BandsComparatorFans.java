package Ex2;

import java.util.Comparator;

public class BandsComparatorFans implements Comparator<Band>{
	public int compare(Band b1, Band b2) {
		int band1 = b1.getNumOfFans();
		int band2 = b2.getNumOfFans();
		// descending order
		if (band1 < band2)
			return 1;
		else 
			return 0;

	}
}
