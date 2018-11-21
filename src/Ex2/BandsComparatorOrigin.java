package Ex2;

import java.util.Comparator;

public class BandsComparatorOrigin implements Comparator<Band> {
	public int compare(Band b1, Band b2) {
	   if (b1.getOrigin().compareTo(b2.getOrigin())>0)
			return 1;
		else
			return 0;
	}
}
