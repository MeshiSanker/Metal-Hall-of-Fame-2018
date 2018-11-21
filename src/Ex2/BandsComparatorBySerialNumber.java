package Ex2;

import java.util.Comparator;

public class BandsComparatorBySerialNumber implements Comparator<Band>{
	public int compare(Band b1, Band b2) {
		int band1 = b1.getSerialNumber();
		int band2 = b2.getSerialNumber();
		// descending order
		if (band1 < band2)
			return 1;
		else 
			return 0;

	}
}

